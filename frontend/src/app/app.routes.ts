import { Routes } from '@angular/router';
import { MainLayout } from './layouts/main-layout/main-layout';
import { Lecturer } from './pages/lecturer/lecturer';
import { Student } from './pages/student/student';
import { Course } from './pages/course/course';
import { Schedule } from './pages/schedule/schedule';
import { Enrollment } from './pages/enrollment/enrollment';
import { Dashboard } from './pages/dashboard/dashboard';

export const routes: Routes = [
  {
    path: '',
    component: MainLayout,
    children: [
      {
        path: '',
        component: Dashboard,
        title: 'Dashboard | MyClass',
      },
      {
        path: 'student',
        component: Student,
        title: 'Student | MyClass',
      },
      {
        path: 'lecturer',
        component: Lecturer,
        title: 'Lecturer | MyClass',
      },
      {
        path: 'course',
        component: Course,
        title: 'Course | MyClass',
      },
      {
        path: 'schedule',
        component: Schedule,
        title: 'Schedule | MyClass',
      },
      {
        path: 'enrollment',
        component: Enrollment,
        title: 'Enrollment | MyClass',
      },
    ]
  }
];
