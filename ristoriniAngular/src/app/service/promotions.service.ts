import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Promotion } from '../models/promocion.dto';

@Injectable({ providedIn: 'root' })
export class PromotionsService {
  private base = '/api/v1';
  constructor(private http: HttpClient) {}

  list(params?: { active?: boolean; query?: string; date?: string }): Observable<Promotion[]> {
    const httpParams = new HttpParams({ fromObject: { ...(params as any) } });
    return this.http.get<Promotion[]>(`${this.base}/promotions`, { params: httpParams });
  }

  registerClick(promotionId: string) {
    const url = `${this.base}/promotions/${promotionId}/clicks`;
    const body = { source: 'web', clickedAt: new Date().toISOString() };
    return this.http.post(url, body).pipe(catchError(() => of(true)));
  }

  logClick(promotionId: string) {
    const url = `${this.base}/promotions/${promotionId}/clicks`;
    const body = { source: 'web', clickedAt: new Date().toISOString() };
    if (navigator?.sendBeacon) {
      const blob = new Blob([JSON.stringify(body)], { type: 'application/json' });
      navigator.sendBeacon(url, blob);
      return of(true);
    }
    return this.registerClick(promotionId);
  }
}




