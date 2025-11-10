import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable, switchMap } from 'rxjs';
import { RestaurantService } from '../../service/restaurant.service';
import { RestauranteDetalleDTO } from '../../models/restaurante-detalle.dto';
import { RestaurantDetailComponent } from '../../components/restaurant-detail/restaurant-detail.component';

@Component({
  selector: 'app-restaurant-page',
  standalone: true,
  imports: [CommonModule, RestaurantDetailComponent],
  templateUrl: './restaurant-page.component.html',
  styleUrls: ['./restaurant-page.component.css']
})
export class RestaurantPageComponent implements OnInit {

  public restaurant$!: Observable<RestauranteDetalleDTO>;

  constructor(
    private route: ActivatedRoute,
    private restaurantService: RestaurantService
  ) {}

  ngOnInit(): void {
    this.restaurant$ = this.route.paramMap.pipe(
      switchMap(params => {
        const id = Number(params.get('id'));
        return this.restaurantService.getRestaurantDetail(id);
      })
    );
  }
}