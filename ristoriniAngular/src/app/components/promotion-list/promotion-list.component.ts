import { Component, Input } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { RouterLink } from '@angular/router';
import { PromocionDTO } from '../../models/promocion.dto';
import { PromotionsService } from '../../service/promotions.service';

@Component({
  standalone: true,
  selector: 'app-promotion-list',
  imports: [CommonModule, DatePipe, RouterLink],
  templateUrl: './promotion-list.component.html',
  styleUrls: ['./promotion-list.component.css']
})
export class PromotionListComponent {
  @Input() promotions: PromocionDTO[] = [];
  constructor(private promosSvc: PromotionsService) {}
  onPromotionClick(p: PromocionDTO) { this.promosSvc.logClick(p.codContenido).subscribe(); }
}



