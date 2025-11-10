import { Component, Input } from '@angular/core'; // 1. Importar Input
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router'; // 2. RouterLink SÍ se usa en el HTML

// Importar los módulos de Material
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatListModule } from '@angular/material/list';
import { MatIconModule } from '@angular/material/icon';
import { MatTabsModule } from '@angular/material/tabs';

// Importar el DTO correcto
import { RestauranteDetalleDTO } from '../../models/restaurante-detalle.dto';

@Component({
  selector: 'app-restaurant-detail', // Este es tu 'app-restaurant-detail'
  standalone: true,
  imports: [
    CommonModule,
    RouterLink,
    MatCardModule,
    MatButtonModule,
    MatProgressSpinnerModule,
    MatListModule,
    MatIconModule,
    MatTabsModule
  ],
  templateUrl: './restaurant-detail.component.html',
  styleUrls: ['./restaurant-detail.component.css']
})

export class RestaurantDetailComponent {
  @Input() restaurant: RestauranteDetalleDTO | null = null;
}