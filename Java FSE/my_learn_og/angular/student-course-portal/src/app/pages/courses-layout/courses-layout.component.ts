import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-courses-layout',
  standalone: true,
  imports: [RouterOutlet],
  template: `
    <div class="courses-layout">
      <h2>Course Portal Directory</h2>
      <router-outlet></router-outlet>
    </div>
  `,
  styles: [`
    .courses-layout {
      max-width: 1000px;
      margin: 2rem auto;
      padding: 0 1rem;
    }
    h2 {
      color: #0f172a;
      border-bottom: 2px solid #e2e8f0;
      padding-bottom: 0.5rem;
    }
  `]
})
export class CoursesLayoutComponent {}
