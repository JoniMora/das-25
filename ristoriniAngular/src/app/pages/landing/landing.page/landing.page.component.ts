import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { Promotion } from '../../../models/promotion.model';
import { PromotionsService } from '../../../service/promotions.service';
import { PromotionListComponent } from '../../../components/promotion-list/promotion-list.component';

@Component({
  standalone: true,
  selector: 'app-landing-page',
  imports: [CommonModule, FormsModule, PromotionListComponent],
  templateUrl: './landing.page.component.html',
  styleUrls: ['./landing.page.component.css']
})
export class LandingPageComponent implements OnInit {
  loading = true;
  query = '';
  promotions: Promotion[] = [];

  constructor(private promos: PromotionsService) {}

  ngOnInit() { this.load(); }

  load() {
    this.loading = true;
    this.promos.list({ active: true, query: this.query }).subscribe({
      next: (res) => { this.promotions = res; this.loading = false; },
      error: () => { this.promotions = []; this.loading = false; }
    });
  }
}




