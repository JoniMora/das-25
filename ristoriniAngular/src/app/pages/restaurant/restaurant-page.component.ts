import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable, switchMap } from 'rxjs';
import { RestaurantService } from '../../service/restaurant.service';
import { RestauranteDetalleDTO } from '../../models/restaurante-detalle.dto';
import { RestaurantDetailComponent } from '../../components/restaurant-detail/restaurant-detail.component';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

@Component({
  selector: 'app-restaurant-page',
  standalone: true,
  imports: [CommonModule, RestaurantDetailComponent, MatProgressSpinnerModule],
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
        if (!id) {
          throw new Error('ID de Restaurante no encontrado');
        }
        return this.restaurantService.getRestaurantDetail(id);
      })
    );
  }
}