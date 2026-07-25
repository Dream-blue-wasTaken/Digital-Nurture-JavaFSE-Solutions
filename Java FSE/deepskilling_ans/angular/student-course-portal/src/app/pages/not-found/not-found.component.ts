import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
@Component({
  selector: 'app-not-found',
  standalone: true,
  imports: [RouterLink],
  template: `
    <div class="not-found-container">
      <h1>404 - Page Not Found</h1>
      <p>The page you are looking for does not exist.</p>
      <a routerLink="/" class="btn btn-primary">Return Home</a>
    </div>
  `,
  styles: [`
    .not-found-container {
      text-align: center;
      padding: 4rem 1rem;
    }
    h1 {
      font-size: 3rem;
      color: #ef4444;
    }
    .btn {
      display: inline-block;
      margin-top: 1rem;
      padding: 0.6rem 1.2rem;
      background: #2563eb;
      color: #fff;
      text-decoration: none;
      border-radius: 6px;
      font-weight: 600;
    }
  `]
})
export class NotFoundComponent {}