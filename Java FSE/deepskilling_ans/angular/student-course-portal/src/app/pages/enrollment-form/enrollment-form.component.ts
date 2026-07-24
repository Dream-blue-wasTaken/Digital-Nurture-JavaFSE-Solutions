import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';

@Component({
  selector: 'app-enrollment-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './enrollment-form.component.html',
  styleUrls: ['./enrollment-form.component.css']
})
export class EnrollmentFormComponent {
  studentName: string = '';
  studentEmail: string = '';
  courseId: number | null = null;
  preferredSemester: string = 'Odd';
  agreeToTerms: boolean = false;

  submitted: boolean = false;

  onSubmit(form: NgForm): void {
    console.log('Template-Driven Form submitted:', form.value);
    console.log('Form validity:', form.valid);
    if (form.valid) {
      this.submitted = true;
    }
  }

  onReset(form: NgForm): void {
    form.resetForm({
      preferredSemester: 'Odd',
      agreeToTerms: false
    });
    this.submitted = false;
  }
}
