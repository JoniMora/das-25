import { Component, Input } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { RouterLink } from '@angular/router';

import { Promotion } from '../../models/promotion.model';
import { PromotionsService } from '../../service/promotions.service';

@Component({
  standalone: true,
  selector: 'app-promotion-list',
  imports: [CommonModule, DatePipe, RouterLink],
  templateUrl: './promotion-list.component.html',
  styleUrls: ['./promotion-list.component.css']
})
export class PromotionListComponent {
  @Input() promotions: Promotion[] = [];
  constructor(private promosSvc: PromotionsService) {}
  onPromotionClick(p: Promotion) { this.promosSvc.logClick(p.id).subscribe(); }
}



