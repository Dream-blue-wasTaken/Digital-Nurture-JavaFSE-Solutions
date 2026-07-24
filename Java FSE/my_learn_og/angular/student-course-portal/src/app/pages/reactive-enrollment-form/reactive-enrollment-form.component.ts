import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  AbstractControl,
  FormArray,
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  ValidationErrors,
  Validators
} from '@angular/forms';
import { ComponentWithDirtyCheck } from '../../guards/unsaved-changes.guard';

// Custom Sync Validator: Disallow course codes starting with 'XX' (Step 53)
export function noCourseCode(control: AbstractControl): ValidationErrors | null {
  const value = String(control.value || '').toUpperCase();
  if (value.startsWith('XX')) {
    return { noCourseCode: true };
  }
  return null;
}

// Custom Async Validator: Simulate email availability check after 800ms (Step 55)
export function simulateEmailCheck(control: AbstractControl): Promise<ValidationErrors | null> {
  return new Promise((resolve) => {
    setTimeout(() => {
      const email = String(control.value || '');
      if (email.includes('test@')) {
        resolve({ emailTaken: true });
      } else {
        resolve(null);
      }
    }, 800);
  });
}

@Component({
  selector: 'app-reactive-enrollment-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './reactive-enrollment-form.component.html',
  styleUrls: ['./reactive-enrollment-form.component.css']
})
export class ReactiveEnrollmentFormComponent implements OnInit, ComponentWithDirtyCheck {
  enrollForm!: FormGroup;
  submitted: boolean = false;

  constructor(private fb: FormBuilder) {}

  ngOnInit(): void {
    // Form initialization using FormBuilder (Step 49)
    this.enrollForm = this.fb.group({
      studentName: ['', [Validators.required, Validators.minLength(3)]],
      studentEmail: ['', [Validators.required, Validators.email], [simulateEmailCheck]],
      courseId: ['', [Validators.required, noCourseCode]],
      preferredSemester: ['Odd', Validators.required],
      agreeToTerms: [false, Validators.requiredTrue],
      additionalCourses: this.fb.array([]) // FormArray for dynamic controls (Step 56)
    });
  }

  // Typed getter for FormArray (Step 57)
  get additionalCourses(): FormArray {
    return this.enrollForm.get('additionalCourses') as FormArray;
  }

  addCourseControl(): void {
    this.additionalCourses.push(this.fb.control('', Validators.required));
  }

  removeCourseControl(index: number): void {
    this.additionalCourses.removeAt(index);
  }

  onSubmit(): void {
    console.log('Reactive Form Value:', this.enrollForm.value);
    console.log('Reactive Form Raw Value:', this.enrollForm.getRawValue());
    if (this.enrollForm.valid) {
      this.submitted = true;
    }
  }

  isDirty(): boolean {
    return this.enrollForm ? this.enrollForm.dirty && !this.submitted : false;
  }
}
