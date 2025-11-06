import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Restaurant } from '../models/restaurant.model';
import { Promotion } from '../models/promotion.model';


@Injectable({ providedIn: 'root' })
export class RestaurantsService {
  private base = '/api/v1';
  constructor(private http: HttpClient) {}

  getById(id: string): Observable<Restaurant> {
    return this.http.get<Restaurant>(`${this.base}/restaurants/${id}`);
  }
}






