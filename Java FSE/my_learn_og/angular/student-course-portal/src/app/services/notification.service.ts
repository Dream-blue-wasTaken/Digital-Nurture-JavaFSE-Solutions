import { Injectable } from '@angular/core';

@Injectable()
export class NotificationService {
  private instanceId = Math.floor(Math.random() * 10000);

  notify(message: string): string {
    return `[NotificationService #${this.instanceId}]: ${message}`;
  }
}
