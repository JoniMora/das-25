import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { Observable, switchMap } from 'rxjs';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatIconModule } from '@angular/material/icon';
import { PromocionDTO } from '../../../models/promocion.dto';
import { RestaurantService } from '../../../service/restaurant.service';

@Component({
  selector: 'app-promotion-page',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink,
    MatCardModule,
    MatButtonModule,
    MatProgressSpinnerModule,
    MatIconModule
  ],
  templateUrl: './promotion-page.component.html',
  styleUrls: ['./promotion-page.component.css']
})
export class PromotionPageComponent implements OnInit {

  public promotion$!: Observable<PromocionDTO>;

  constructor(
    private route: ActivatedRoute,
    private restaurantService: RestaurantService
  ) {}

  ngOnInit(): void {
    this.promotion$ = this.route.paramMap.pipe(
      switchMap(params => {
        const code = params.get('code')!;
        return this.restaurantService.getPromotionByCode(code);
      })
    );
  }
}