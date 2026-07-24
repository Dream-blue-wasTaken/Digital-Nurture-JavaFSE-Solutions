import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of, throwError } from 'rxjs';
import { catchError, map, retry, tap } from 'rxjs/operators';
import { Course } from '../models/course.model';

@Injectable({
  providedIn: 'root'
})
export class CourseService {
  private apiUrl = 'http://localhost:3000/courses';

  private initialCourses: Course[] = [
    { id: 1, name: 'Data Structures & Algorithms', code: 'CS101', credits: 4, gradeStatus: 'passed' },
    { id: 2, name: 'Database Management Systems', code: 'CS102', credits: 3, gradeStatus: 'passed' },
    { id: 3, name: 'Web Development with Angular', code: 'CS103', credits: 4, gradeStatus: 'pending' },
    { id: 4, name: 'Operating Systems', code: 'CS104', credits: 3, gradeStatus: 'failed' },
    { id: 5, name: 'Software Engineering', code: 'CS105', credits: 3, gradeStatus: 'pending' }
  ];

  constructor(private http: HttpClient) {}

  getCoursesSync(): Course[] {
    return [...this.initialCourses];
  }

  getCourses(): Observable<Course[]> {
    return this.http.get<Course[]>(this.apiUrl).pipe(
      retry(2),
      tap(courses => console.log('Courses loaded:', courses.length)),
      map(courses => courses.filter(c => c.credits > 0)),
      catchError(err => {
        console.error('API Error, falling back to local dataset:', err);
        return of(this.initialCourses);
      })
    );
  }

  getCourseById(id: number): Observable<Course | undefined> {
    return this.http.get<Course>(`${this.apiUrl}/${id}`).pipe(
      catchError(() => {
        const found = this.initialCourses.find(c => c.id === id);
        return of(found);
      })
    );
  }

  addCourse(course: Course): void {
    this.initialCourses.push(course);
  }

  createCourse(course: Omit<Course, 'id'>): Observable<Course> {
    return this.http.post<Course>(this.apiUrl, course).pipe(
      tap(newCourse => this.initialCourses.push(newCourse)),
      catchError(err => {
        console.error(err);
        const created: Course = { ...course, id: Date.now() };
        this.initialCourses.push(created);
        return of(created);
      })
    );
  }

  updateCourse(id: number, course: Partial<Course>): Observable<Course> {
    return this.http.put<Course>(`${this.apiUrl}/${id}`, course).pipe(
      catchError(err => throwError(() => new Error('Failed to update course.')))
    );
  }

  deleteCourse(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`).pipe(
      tap(() => {
        this.initialCourses = this.initialCourses.filter(c => c.id !== id);
      }),
      catchError(err => throwError(() => new Error('Failed to delete course.')))
    );
  }
}
