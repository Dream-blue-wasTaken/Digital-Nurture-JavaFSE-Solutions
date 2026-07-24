import { Component } from '@angular/core';
import { NotificationService } from '../../services/notification.service';

@Component({
  selector: 'app-notification',
  standalone: true,
  providers: [NotificationService], // Component-level provider (Step 67)
  template: `
    <div class="notification-box">
      <p><strong>Scoped Notification:</strong> {{ message }}</p>
    </div>
  `,
  styles: [`
    .notification-box {
      background: #eff6ff;
      border: 1px solid #bfdbfe;
      padding: 0.75rem 1rem;
      border-radius: 6px;
      margin: 1rem 0;
      color: #1e40af;
    }
  `]
})
export class NotificationComponent {
  message: string;

  constructor(private notificationService: NotificationService) {
    this.message = this.notificationService.notify('Component-scoped NotificationService instance initialized.');
  }
}
