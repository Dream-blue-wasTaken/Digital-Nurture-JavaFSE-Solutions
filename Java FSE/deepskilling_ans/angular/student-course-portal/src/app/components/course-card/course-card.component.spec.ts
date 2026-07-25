import { ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { SimpleChange } from '@angular/core';
import { vi } from 'vitest';
import { CourseCardComponent } from './course-card.component';
import { Course } from '../../models/course.model';
describe('CourseCardComponent', () => {
  let component: CourseCardComponent;
  let fixture: ComponentFixture<CourseCardComponent>;
  const mockCourse: Course = {
    id: 1,
    name: 'Data Structures',
    code: 'CS101',
    credits: 4,
    gradeStatus: 'passed'
  };
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CourseCardComponent]
    }).compileComponents();
    fixture = TestBed.createComponent(CourseCardComponent);
    component = fixture.componentInstance;
  });
  it('should create', () => {
    expect(component).toBeTruthy();
  });
  it('should render course details via @Input', () => {
    component.course = mockCourse;
    fixture.detectChanges();
    const headingEl = fixture.debugElement.query(By.css('h3')).nativeElement;
    expect(headingEl.textContent).toContain('Data Structures');
  });
  it('should emit enrollRequested event via @Output on button click', () => {
    component.course = mockCourse;
    fixture.detectChanges();
    const spy = vi.spyOn(component.enrollRequested, 'emit');
    const button = fixture.debugElement.query(By.css('.btn-primary')).nativeElement;
    button.click();
    fixture.detectChanges();
    expect(spy).toHaveBeenCalledWith(1);
  });
  it('should handle ngOnChanges and log input changes', () => {
    const spy = vi.spyOn(console, 'log');
    component.course = mockCourse;
    component.ngOnChanges({
      course: new SimpleChange(null, mockCourse, true)
    });
    expect(spy).toHaveBeenCalled();
  });
});