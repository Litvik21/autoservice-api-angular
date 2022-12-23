import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Car } from '../model/car';
import { environment } from '../../environments/environment';

@Injectable({providedIn: 'root'})
export class CarService {
  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };
  private carsUrl = environment.urlPath + '/cars';

  constructor(
    private http: HttpClient) {
  }

  getCars(): Observable<Car[]> {
    return this.http.get<Car[]>(this.carsUrl)
      .pipe(
        catchError(this.handleError<Car[]>('getCars', []))
      );
  }

  getCar(id: number): Observable<Car> {
    const url = `${this.carsUrl}/${id}`;
    return this.http.get<Car>(url).pipe(
      catchError(this.handleError<Car>(`getCar id=${id}`))
    );
  }

  updateCar(car: Car): Observable<any> {
    const url = `${this.carsUrl}/${car.id}`
    return this.http.put(url, car, this.httpOptions).pipe(
      catchError(this.handleError<any>('updateCar'))
    );
  }

  addCar(car: Car): Observable<Car> {
    return this.http.post<Car>(this.carsUrl, car, this.httpOptions).pipe(
      catchError(this.handleError<Car>('addCar'))
    );
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      console.error(error);

      return of(result as T);
    };
  }
}
