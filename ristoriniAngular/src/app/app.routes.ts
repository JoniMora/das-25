// src/app/app.routes.ts
import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    loadComponent: () =>
      import('./pages/landing/landing.page/landing.page.component')
        .then(c => c.LandingPageComponent)
  },
  {
    path: 'restaurants/:id',
    loadComponent: () =>
      import('./pages/restaurants/restaurant.page/restaurant.page.component')
        .then(c => c.RestaurantPageComponent)
  },
  { path: '**', redirectTo: '' }
];
