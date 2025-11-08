// src/app/app.routes.ts (ESTRUCTURA CORRECTA Y PLANA)

import { Routes } from '@angular/router';

export const routes: Routes = [

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
  },
  { path: '**', redirectTo: '' }
];


