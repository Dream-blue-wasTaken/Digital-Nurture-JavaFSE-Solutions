import { Component, EventEmitter, Input, OnChanges, Output, SimpleChanges } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Course } from '../../models/course.model';
import { CreditLabelPipe } from '../../pipes/credit-label.pipe';
import { HighlightDirective } from '../../directives/highlight.directive';
@Component({
  selector: 'app-course-card',
  standalone: true,
  imports: [CommonModule, CreditLabelPipe, HighlightDirective],
  templateUrl: './course-card.component.html',
  styleUrls: ['./course-card.component.css']
})
export class CourseCardComponent implements OnChanges {
  @Input() course: Course = { id: 0, name: '', code: '', credits: 0, gradeStatus: 'pending' };
  @Input() isEnrolled: boolean = false;
  @Output() enrollRequested = new EventEmitter<number>();
  isExpanded: boolean = false;
  ngOnChanges(changes: SimpleChanges): void {
    if (changes['course']) {
      console.log('Course input changed:', {
        previousValue: changes['course'].previousValue,
        currentValue: changes['course'].currentValue
      });
    }
  }
  toggleDetails(): void {
    this.isExpanded = !this.isExpanded;
  }
  onEnrollClick(): void {
    if (this.course && this.course.id) {
      this.enrollRequested.emit(this.course.id);
    }
  }