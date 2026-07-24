import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  // Hardcoded for auth guard testing
  private loggedInStatus = true;

  isLoggedIn(): boolean {
    return this.loggedInStatus;
  }

  setLoggedIn(status: boolean): void {
    this.loggedInStatus = status;
  }
}
