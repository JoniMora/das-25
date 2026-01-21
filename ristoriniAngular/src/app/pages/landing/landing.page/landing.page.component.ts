import { Component, OnInit, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { Observable } from 'rxjs';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { RestaurantService } from '../../../service/restaurant.service';
import { PromocionDTO } from '../../../models/promocion.dto';
import { RestauranteResumenDTO } from '../../../models/restaurante-resumen.dto';
import { PromotionsService } from '../../../service/promotions.service';

@Component({
  standalone: true,
  selector: 'app-landing-page',
  imports: [
    CommonModule,
    RouterLink,
    MatCardModule,
    MatButtonModule,
    MatProgressSpinnerModule,
  ],
  templateUrl: './landing.page.component.html',
  styleUrls: ['./landing.page.component.css'],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})

export class LandingPageComponent implements OnInit {
  public promotions$!: Observable<PromocionDTO[]>;
  public restaurants$!: Observable<RestauranteResumenDTO[]>;

  constructor(private restaurantService: RestaurantService,private promotionsService: PromotionsService,
    private router: Router) {}

  ngOnInit() {
    this.promotions$ = this.restaurantService.getPromotions();
    this.restaurants$ = this.restaurantService.getRestaurants();
  }
  onPromotionClick(promo: PromocionDTO): void {
    this.promotionsService.logClick(promo.codContenido).subscribe({
      next: () => {
        console.log(`Clic en ${promo.codContenido} registrado.`);
      },
      error: (err) => {
        console.error('Error al registrar el clic:', err);
      }
    });
    this.router.navigate(['/promotion', promo.codContenido]);
  }
}
