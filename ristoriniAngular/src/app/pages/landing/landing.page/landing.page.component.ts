import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { Observable } from 'rxjs';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { RestaurantService } from '../../../service/restaurant.service';
import { PromocionDTO } from '../../../models/promocion.dto';
import { RestauranteResumenDTO } from '../../../models/restaurante-resumen.dto';

@Component({
  standalone: true,
  selector: 'app-landing-page',
  imports: [
    CommonModule, 
    RouterLink,
    MatCardModule,
    MatButtonModule,
    MatProgressSpinnerModule
  ],
  templateUrl: './landing.page.component.html',
  styleUrls: ['./landing.page.component.css']
})
export class LandingPageComponent implements OnInit {
  
  public promotions$!: Observable<PromocionDTO[]>;
  public restaurants$!: Observable<RestauranteResumenDTO[]>;

  constructor(private restaurantService: RestaurantService) {}

  ngOnInit() {
    this.promotions$ = this.restaurantService.getPromotions();
    this.restaurants$ = this.restaurantService.getRestaurants();
  }
}