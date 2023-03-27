import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { CarOwner } from '../model/carOwner';
import { Order } from '../model/order';
import { environment } from '../../environments/environment';

@Injectable({providedIn: 'root'})
export class CarOwnerService {
  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };
  private ownersUrl = environment.urlPath + '/car-owners';

  constructor(
    private http: HttpClient) {
  }

  getCarOwners(): Observable<CarOwner[]> {
    return this.http.get<CarOwner[]>(this.ownersUrl)
      .pipe(
        catchError(this.handleError<CarOwner[]>('getCarOwners', []))
      );
  }

  getCarOwner(id: number): Observable<CarOwner> {
    const url = `${this.ownersUrl}/get/${id}`;
    return this.http.get<CarOwner>(url).pipe();
  }

  getOrdersOfOwner(id: number): Observable<Order[]> {
    const url = `${this.ownersUrl}/orders/${id}`;
    return this.http.get<Order[]>(url).pipe();
  }

  updateCarOwner(owner: any): Observable<any> {
    const url = `${this.ownersUrl}/${owner.id}`;
    return this.http.put(url, owner, this.httpOptions).pipe(
      catchError(this.handleError<any>('updateCarOwner'))
    );
  }

  addCarOwner(owner: CarOwner): Observable<any> {
    const ownerToSent = {
      name: owner.name,
      carsId: owner.cars.map(car => car.id),
      ordersId: owner.orders.map(order => order.id)
    };
    return this.http.post<any>(this.ownersUrl, ownerToSent, this.httpOptions).pipe(
      catchError(this.handleError<any>('addCarOwner'))
    );
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      console.error(error);

      return of(result as T);
    };
  }
}
