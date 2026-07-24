import { TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { CourseService } from './course.service';
import { Course } from '../models/course.model';

describe('CourseService', () => {
  let service: CourseService;
  let httpMock: HttpTestingController;

  const mockCourses: Course[] = [
    { id: 1, name: 'Algorithms', code: 'CS101', credits: 4, gradeStatus: 'passed' },
    { id: 2, name: 'Databases', code: 'CS102', credits: 3, gradeStatus: 'passed' }
  ];

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        CourseService,
        provideHttpClient(),
        provideHttpClientTesting()
      ]
    });
    service = TestBed.inject(CourseService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should fetch courses via GET', () => {
    service.getCourses().subscribe(courses => {
      expect(courses.length).toBe(2);
      expect(courses).toEqual(mockCourses);
    });

    const req = httpMock.expectOne('http://localhost:3000/courses');
    expect(req.request.method).toBe('GET');
    req.flush(mockCourses);
  });

  it('should fallback to local data on HTTP error after retries', () => {
    service.getCourses().subscribe(courses => {
      expect(courses.length).toBeGreaterThan(0);
    });

    // Initial attempt + 2 retries = 3 requests
    const req1 = httpMock.expectOne('http://localhost:3000/courses');
    req1.flush('Error 1', { status: 500, statusText: 'Server Error' });

    const req2 = httpMock.expectOne('http://localhost:3000/courses');
    req2.flush('Error 2', { status: 500, statusText: 'Server Error' });

    const req3 = httpMock.expectOne('http://localhost:3000/courses');
    req3.flush('Error 3', { status: 500, statusText: 'Server Error' });
  });
});
