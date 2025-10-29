import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Restaurant } from '../../models/restaurant.model';
import { RestaurantService } from '../../services/restaurant.service';

@Component({
  selector: 'app-restaurant-detail',
  templateUrl: './restaurant-detail.component.html',
  styleUrls: ['./restaurant-detail.component.css']
})
export class RestaurantDetailComponent implements OnInit {
  restaurant: Restaurant | undefined;
  loading = true;
  error = '';

  constructor(
    private route: ActivatedRoute,
    private restaurantService: RestaurantService
  ) {}

  ngOnInit(): void {
    this.loadRestaurant();
  }

  loadRestaurant(): void {
    const id = this.route.snapshot.paramMap.get('id');

    if (!id || isNaN(Number(id))) {
      this.error = 'ID de restaurante inválido';
      this.loading = false;
      return;
    }

    this.restaurantService.getRestaurantById(Number(id)).subscribe({
      next: (data) => {
        this.restaurant = data;
        this.loading = false;
      },
      error: (error) => {
        console.error('Error loading restaurant:', error);
        this.error = 'Error al cargar la información del restaurante';
        this.loading = false;
      }
    });
  }

  goBack(): void {
    // Método simple para volver atrás
    window.history.back();
  }

  onImageError(event: any): void {
    // Fallback para imágenes rotas
    event.target.src = 'https://via.placeholder.com/400x200?text=Imagen+no+disponible';
  }
}
