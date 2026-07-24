# Angular Hands-on Exercises

## Prerequisites Installation
```bash
# Install Node.js and NPM
# Download from https://nodejs.org/en/download/

# Install Angular CLI globally
npm install -g @angular/cli

# Verify installation
ng version
```

## 1. Creating a New Angular Application

```bash
# Create a new Angular project
ng new my-angular-app

# Navigate to project folder
cd my-angular-app

# Serve the application
ng serve

# Open in browser at http://localhost:4200
```

## 2. Angular Components

### Generate Components
```bash
# Generate components using Angular CLI
ng generate component home
ng generate component about
ng generate component contact

# Or using shorthand
ng g c home
ng g c about
ng g c contact
```

### Component File Structure

**home.component.ts:**
```typescript
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  title: string = 'Welcome to the Home Page';
  welcomeMessage: string = 'This is the first Angular application!';

  constructor() { }

  ngOnInit(): void {
    console.log('Home component initialized');
  }
}
```

**home.component.html:**
```html
<div class="container">
  <h1>{{ title }}</h1>
  <p>{{ welcomeMessage }}</p>
  
  <div class="card">
    <h3>Quick Links</h3>
    <ul>
      <li><a routerLink="/about">About Us</a></li>
      <li><a routerLink="/contact">Contact</a></li>
    </ul>
  </div>
</div>
```

**home.component.css:**
```css
.container {
  padding: 20px;
  font-family: Arial, sans-serif;
}

.card {
  background-color: #f5f5f5;
  border: 1px solid #ddd;
  border-radius: 8px;
  padding: 15px;
  margin-top: 20px;
}
```

**about.component.ts:**
```typescript
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-about',
  templateUrl: './about.component.html',
  styleUrls: ['./about.component.css']
})
export class AboutComponent implements OnInit {

  companyName: string = 'Cognizant Technology Solutions';
  description: string = 'Leading IT services and consulting company';
  employees: number = 350000;

  constructor() { }

  ngOnInit(): void {
  }
}
```

**about.component.html:**
```html
<div class="container">
  <h1>About Us</h1>
  <div class="info-card">
    <h2>{{ companyName }}</h2>
    <p>{{ description }}</p>
    <p><strong>Employees:</strong> {{ employees }}+</p>
  </div>
</div>
```

## 3. Data Binding and Directives

```typescript
// In any component .ts file
import { Component } from '@angular/core';

@Component({
  selector: 'app-data-binding-demo',
  template: `
    <div>
      <h2>Data Binding Demo</h2>
      
      <!-- Interpolation -->
      <p>Name: {{ userName }}</p>
      
      <!-- Property Binding -->
      <img [src]="imageUrl" [alt]="imageAlt" width="200">
      
      <!-- Event Binding -->
      <button (click)="showAlert()">Click Me</button>
      
      <!-- Two-way Binding -->
      <input [(ngModel)]="userName" placeholder="Enter name">
      
      <!-- Structural Directives -->
      <div *ngIf="isLoggedIn">
        <p>Welcome back, {{ userName }}!</p>
      </div>
      
      <ul>
        <li *ngFor="let item of items">{{ item }}</li>
      </ul>
    </div>
  `
})
export class DataBindingDemoComponent {
  userName: string = 'John Doe';
  imageUrl: string = 'https://angular.io/assets/images/logos/angular/angular.svg';
  imageAlt: string = 'Angular Logo';
  isLoggedIn: boolean = true;
  items: string[] = ['Item 1', 'Item 2', 'Item 3'];

  showAlert() {
    alert('Button clicked!');
  }
}
```

## 4. Services and Dependency Injection

```typescript
// data.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DataService {

  private apiUrl = 'https://jsonplaceholder.typicode.com';

  constructor(private http: HttpClient) { }

  getUsers(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/users`);
  }

  getUserById(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/users/${id}`);
  }
}

// Using the service in a component
import { Component, OnInit } from '@angular/core';
import { DataService } from './data.service';

@Component({
  selector: 'app-user-list',
  template: `
    <div>
      <h2>User List</h2>
      <ul>
        <li *ngFor="let user of users">
          {{ user.name }} - {{ user.email }}
        </li>
      </ul>
    </div>
  `
})
export class UserListComponent implements OnInit {
  users: any[] = [];

  constructor(private dataService: DataService) { }

  ngOnInit(): void {
    this.dataService.getUsers().subscribe(
      data => this.users = data,
      error => console.error('Error fetching users:', error)
    );
  }
}
```

## 5. Routing Module

```typescript
// app-routing.module.ts
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { AboutComponent } from './about/about.component';
import { ContactComponent } from './contact/contact.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'home', component: HomeComponent },
  { path: 'about', component: AboutComponent },
  { path: 'contact', component: ContactComponent },
  { path: '**', redirectTo: 'home' }  // Wildcard route
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
```

```typescript
// app.module.ts
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { AboutComponent } from './about/about.component';
import { ContactComponent } from './contact/contact.component';
import { UserListComponent } from './user-list/user-list.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    AboutComponent,
    ContactComponent,
    UserListComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
```

## 6. Complete App Component Template

```html
<!-- app.component.html -->
<nav class="navbar">
  <div class="nav-brand">
    <h2>Angular Application</h2>
  </div>
  <div class="nav-links">
    <a routerLink="/home" routerLinkActive="active">Home</a>
    <a routerLink="/about" routerLinkActive="active">About</a>
    <a routerLink="/contact" routerLinkActive="active">Contact</a>
    <a routerLink="/users" routerLinkActive="active">Users</a>
  </div>
</nav>

<div class="main-content">
  <router-outlet></router-outlet>
</div>

<footer class="footer">
  <p>&copy; 2026 Angular Application. All rights reserved.</p>
</footer>
```

```css
/* app.component.css */
.navbar {
  background-color: #1976d2;
  color: white;
  padding: 1rem;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.nav-links a {
  color: white;
  text-decoration: none;
  margin: 0 15px;
  padding: 5px 10px;
  border-radius: 4px;
  transition: background-color 0.3s;
}

.nav-links a:hover {
  background-color: rgba(255,255,255,0.1);
}

.nav-links a.active {
  background-color: rgba(255,255,255,0.2);
  font-weight: bold;
}

.main-content {
  padding: 20px;
  min-height: calc(100vh - 150px);
}

.footer {
  background-color: #333;
  color: white;
  text-align: center;
  padding: 1rem;
  position: fixed;
  bottom: 0;
  width: 100%;
}
```

## Running the Application

```bash
# Development server
ng serve

# Build for production
ng build

# Production build with optimization
ng build --prod

# Run tests
ng test

# Run end-to-end tests
ng e2e
```
