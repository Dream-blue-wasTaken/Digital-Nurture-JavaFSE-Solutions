import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { CoursesLayoutComponent } from './pages/courses-layout/courses-layout.component';
import { CourseListComponent } from './pages/course-list/course-list.component';
import { CourseDetailComponent } from './pages/course-detail/course-detail.component';
import { StudentProfileComponent } from './pages/student-profile/student-profile.component';
import { EnrollmentFormComponent } from './pages/enrollment-form/enrollment-form.component';
import { ReactiveEnrollmentFormComponent } from './pages/reactive-enrollment-form/reactive-enrollment-form.component';
import { NotFoundComponent } from './pages/not-found/not-found.component';
import { authGuard } from './guards/auth.guard';
import { unsavedChangesGuard } from './guards/unsaved-changes.guard';
export const routes: Routes = [
  { path: '', component: HomeComponent },
  {
    path: 'courses',
    component: CoursesLayoutComponent,
    children: [
      { path: '', component: CourseListComponent },
      { path: ':id', component: CourseDetailComponent }
    ]
  },
  {
    path: 'profile',
    component: StudentProfileComponent,
    canActivate: [authGuard]
  },
  { path: 'enroll', component: EnrollmentFormComponent },
  {
    path: 'enroll-reactive',
    component: ReactiveEnrollmentFormComponent,
    canDeactivate: [unsavedChangesGuard]
  },
  { path: '**', component: NotFoundComponent }
];