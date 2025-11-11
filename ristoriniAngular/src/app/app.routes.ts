import { Routes } from '@angular/router';
import { AppShellComponent } from './pages/shell/app-shell.component';

export const routes: Routes = [
  {
    path: '',
    component: AppShellComponent,

    children: [
      {
        path: '',
        pathMatch: 'full',
        loadComponent: () =>
          import('./pages/landing/landing.page/landing.page.component')
            .then(m => m.LandingPageComponent)
      },
      {
        path: 'restaurants/:id',
        loadComponent: () =>
          import('./pages/restaurant/restaurant-page.component')
            .then(m => m.RestaurantPageComponent)
      },
      {
        path: 'login',
        loadComponent: () =>
          import('./pages/login/login.page/login.page.component')
            .then(m => m.LoginPageComponent)
      }
    ]
  },
  { path: '**', redirectTo: '' }
];