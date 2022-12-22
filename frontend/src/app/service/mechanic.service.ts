import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import { Mechanic } from '../model/mechanic';
import {Order} from "../model/order";
import {environment} from "../../environments/environment";

@Injectable({ providedIn: 'root' })
export class MechanicService {
  private mechanicsUrl = environment.urlPath + '/mechanics';

  constructor(
    private http: HttpClient) { }

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  getMechanics(): Observable<Mechanic[]> {
    return this.http.get<Mechanic[]>(this.mechanicsUrl)
      .pipe(
        catchError(this.handleError<Mechanic[]>('getMechanics', []))
      );
  }

  getMechanic(id: number): Observable<Mechanic> {
    const url = `${this.mechanicsUrl}/${id}`;
    return this.http.get<Mechanic>(url).pipe(
      catchError(this.handleError<Mechanic>(`getMechanic id=${id}`))
    );
  }

  getFinishedOrders(id: number): Observable<Order[]> {
    const url = `${this.mechanicsUrl}/${id}/finished-orders`;
    return this.http.get<Order[]>(url).pipe(
      catchError(this.handleError<Order[]>(`getMechanic id=${id}`))
    );
  }

  getSalary(id: number): Observable<Number> {
    const url = `${this.mechanicsUrl}/${id}`;
    return this.http.get<Number>(url).pipe(
      catchError(this.handleError<Number>(`getMechanic id=${id}`))
    );
  }

  updateMechanic(mechanic: Mechanic): Observable<any> {
    const url = `${this.mechanicsUrl}/${mechanic.id}`
    return this.http.put(url, mechanic, this.httpOptions).pipe(
      catchError(this.handleError<any>('updateMechanic'))
    );
  }

  addMechanic(mechanic: Mechanic): Observable<Mechanic> {
    return this.http.post<Mechanic>(this.mechanicsUrl, mechanic, this.httpOptions).pipe(
      catchError(this.handleError<Mechanic>('addMechanic'))
    );
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      console.error(error);

      return of(result as T);
    };
  }
}
