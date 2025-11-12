import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { PromocionDTO } from '../models/promocion.dto';
import { environment } from '../../environments/environment';

@Injectable({ providedIn: 'root' })
export class PromotionsService {
  private apiUrl = environment.apiUrl + '/v1/promotions';
  constructor(private http: HttpClient) {}

  list(params?: { active?: boolean; query?: string; date?: string }): Observable<PromocionDTO[]> {
    const httpParams = new HttpParams({ fromObject: { ...(params as any) } });
    return this.http.get<PromocionDTO[]>(`${this.apiUrl}`, { params: httpParams });
  }

  registerClick(promotionId: string): Observable<any> {
    const url = `${this.apiUrl}/click`;
    const body = { codContenido: promotionId };
    // Llama al POST /v1/promotions/click
    return this.http.post(url, body).pipe(
      catchError(() => of(true))
    );
  }

  logClick(promotionId: string): Observable<any> {
    const url = `${this.apiUrl}/click`;
    const body = { codContenido: promotionId };

    if (navigator?.sendBeacon) {
      const blob = new Blob([JSON.stringify(body)], { type: 'application/json' });
      navigator.sendBeacon(url, blob);
      return of(true);
    }
    return this.registerClick(promotionId);
  }
}




