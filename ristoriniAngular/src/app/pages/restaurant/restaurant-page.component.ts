import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';

import { RestaurantsService } from '../../service/restaurants.service';
import { Restaurant } from '../../models/restaurant.model';
import { RestaurantDetailComponent } from '../../components/restaurant-detail/restaurant-detail.component';

@Component({
  standalone: true,
  selector: 'app-restaurant-page',
  imports: [CommonModule, RestaurantDetailComponent],
  templateUrl: './restaurant-page.component.html',
  styleUrls: ['./restaurant-page.component.css']
})
export class RestaurantPageComponent implements OnInit {
  restaurant?: Restaurant;

  constructor(
    private route: ActivatedRoute,
    private restaurantsService: RestaurantsService
  ) {}

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.restaurantsService.getById(id).subscribe({
        next: (restaurant: Restaurant) => {
          this.restaurant = restaurant;
        },
        error: (error) => {
          console.error('Error loading restaurant:', error);
        }
      });
    }
  }
}
