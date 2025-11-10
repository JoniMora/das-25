import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { PromocionDTO } from '../models/promocion.dto';
import { RestauranteResumenDTO } from '../models/restaurante-resumen.dto';
import { RestauranteDetalleDTO } from '../models/restaurante-detalle.dto';

@Injectable({
  providedIn: 'root'
})
export class RestaurantService {

  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) { }

  getPromotions(): Observable<PromocionDTO[]> {
    return this.http.get<PromocionDTO[]>(`${this.apiUrl}/v1/promotions`);
  }

  getRestaurants(): Observable<RestauranteResumenDTO[]> {
    return this.http.get<RestauranteResumenDTO[]>(`${this.apiUrl}/v1/restaurants`);
  }

  getRestaurantDetail(id: number): Observable<RestauranteDetalleDTO> {
    return this.http.get<RestauranteDetalleDTO>(`${this.apiUrl}/v1/restaurants/${id}`);
  }
}