import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EnrollmentService } from '../../services/enrollment.service';
import { Course } from '../../models/course.model';
import { CreditLabelPipe } from '../../pipes/credit-label.pipe';
@Component({
  selector: 'app-student-profile',
  standalone: true,
  imports: [CommonModule, CreditLabelPipe],
  templateUrl: './student-profile.component.html',
  styleUrls: ['./student-profile.component.css']
})
export class StudentProfileComponent implements OnInit {
  student = {
    name: 'Alex Johnson',
    email: 'alex@example.com',
    id: 'STU-98241',
    major: 'Computer Science'
  };
  enrolledCourses: Course[] = [];
  constructor(private enrollmentService: EnrollmentService) {}
  ngOnInit(): void {
    this.enrolledCourses = this.enrollmentService.getEnrolledCourses();
  }
}