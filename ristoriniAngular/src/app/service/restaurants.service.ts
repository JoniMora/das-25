import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RestauranteResumenDTO } from '../models/restaurante-resumen.dto';
import { PromocionDTO } from '../models/promocion.dto';


@Injectable({ providedIn: 'root' })
export class RestaurantsService {
  private base = '/api/v1';
  constructor(private http: HttpClient) {}

  getById(id: string): Observable<RestauranteResumenDTO> {
    return this.http.get<RestauranteResumenDTO>(`${this.base}/restaurants/${id}`);
  }
}






