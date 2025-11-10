import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common'; // Para *ngIf, *ngFor, async
import { RouterLink } from '@angular/router'; // Para [routerLink]
import { Observable } from 'rxjs';

// Importa los módulos de Material que decidiste usar
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

// 1. Importa el NUEVO servicio
import { RestaurantService } from '../../../service/restaurant.service';
import { PromocionDTO } from '../../../models/promocion.dto';
import { RestauranteResumenDTO } from '../../../models/restaurante-resumen.dto';

@Component({
  standalone: true,
  selector: 'app-landing-page',
  // 2. Asegúrate de importar TODOS los módulos que usas en el HTML
  imports: [
    CommonModule, 
    RouterLink,
    MatCardModule,
    MatButtonModule,
    MatProgressSpinnerModule
    // Si usas PromotionListComponent, añádelo aquí
    // PromotionListComponent 
  ],
  templateUrl: './landing.page.component.html',
  styleUrls: ['./landing.page.component.css']
})
export class LandingPageComponent implements OnInit {
  
  // 3. Solo necesitas estas dos propiedades (Observables)
  public promotions$!: Observable<PromocionDTO[]>;
  public restaurants$!: Observable<RestauranteResumenDTO[]>;

  // 4. Inyecta el RestaurantService (en lugar de PromotionsService)
  constructor(private restaurantService: RestaurantService) {}

  ngOnInit() {
    // 5. Asigna los observables. El HTML (con 'async') se encargará del resto.
    this.promotions$ = this.restaurantService.getPromotions();
    this.restaurants$ = this.restaurantService.getRestaurants();
  }
}