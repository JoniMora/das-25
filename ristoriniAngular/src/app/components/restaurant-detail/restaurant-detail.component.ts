import { Component, Input } from '@angular/core';

import { Restaurant } from '../../models/restaurant.model';

@Component({
  standalone: true,
  selector: 'app-restaurant-detail',
  imports: [],
  templateUrl: './restaurant-detail.component.html',
  styleUrls: ['./restaurant-detail.component.css']
})
export class RestaurantDetailComponent {
  @Input({ required: true }) restaurant!: Restaurant;
}



