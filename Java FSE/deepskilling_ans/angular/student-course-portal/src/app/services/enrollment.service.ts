import { Injectable } from '@angular/core';
import { CourseService } from './course.service';
import { Course } from '../models/course.model';
import { Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EnrollmentService {
  private enrolledCourseIds: number[] = [1, 2];

  constructor(private courseService: CourseService) {}

  enroll(courseId: number): void {
    if (!this.isEnrolled(courseId)) {
      this.enrolledCourseIds.push(courseId);
    }
  }

  unenroll(courseId: number): void {
    this.enrolledCourseIds = this.enrolledCourseIds.filter(id => id !== courseId);
  }

  isEnrolled(courseId: number): boolean {
    return this.enrolledCourseIds.includes(courseId);
  }

  getEnrolledCourseIds(): number[] {
    return [...this.enrolledCourseIds];
  }

  getEnrolledCourses(): Course[] {
    const allCourses = this.courseService.getCoursesSync();
    return allCourses.filter(c => this.enrolledCourseIds.includes(c.id));
  }

  getStudentsByCourse(courseId: number): Observable<any[]> {
    // Simulates dependent endpoint call for switchMap testing (Step 87)
    const mockStudents = [
      { id: 101, name: 'Student A', courseId: 1 },
      { id: 102, name: 'Student B', courseId: 1 },
      { id: 103, name: 'Student C', courseId: 2 }
    ].filter(s => s.courseId === courseId);
    return of(mockStudents);
  }
}
