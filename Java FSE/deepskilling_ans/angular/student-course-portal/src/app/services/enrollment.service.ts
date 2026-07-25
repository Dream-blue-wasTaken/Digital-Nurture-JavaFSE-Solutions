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