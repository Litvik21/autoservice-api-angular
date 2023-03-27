import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Order } from '../model/order';
import { Product } from '../model/product';
import { environment } from '../../environments/environment';
import { StatusMapping } from '../model/status';
import { CarService } from './car.service';
import { MechanicService } from './mechanic.service';
import { ProductService } from './product.service';
import { TaskService } from './task.service';

@Injectable({providedIn: 'root'})
export class OrderService {
  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };
  private ordersUrl = environment.urlPath + '/orders';

  constructor(
    private carService: CarService,
    private mechanicService: MechanicService,
    private productService: ProductService,
    private taskService: TaskService,
    private http: HttpClient) {
  }

  getOrders(): Observable<Order[]> {
    return this.http.get<Order[]>(this.ordersUrl).pipe(
      catchError(this.handleError<Order[]>('getOrders', []))
    );
  }

  getOrder(id: number): Observable<any> {
    const url = `${this.ordersUrl}/get/${id}`;
    return this.http.get<any>(url).pipe(
      catchError(this.handleError<Order>(`getOrder id=${id}`))
    );
  }

  updateOrder(order: any): Observable<any> {
    const url = `${this.ordersUrl}/${order.id}`;
    return this.http.put(url, order, this.httpOptions).pipe(
      catchError(this.handleError<any>('updateOrder'))
    );
  }

  updateStatus(id: number, status: String): Observable<any> {
    const url = `${this.ordersUrl}/update-status/${id}`;
    return this.http.put(url, status, this.httpOptions).pipe(
      catchError(this.handleError<any>('updateOrder'))
    );
  }

  getTotalPrice(id: number): Observable<Number> {
    const url = `${this.ordersUrl}/price/${id}`;
    return this.http.get<Number>(url).pipe(
      catchError(this.handleError<Number>(`getOrder id=${id}`))
    );
  }

  addOrder(order: Order): Observable<any> {
    if (order.status === undefined) {
      throw new Error('Task type is undefined');
    }
    const orderToSent = {
      carId: order.car.id,
      description: order.description,
      taskIds: order.tasks?.map(task => task.id),
      productsIds: order.products?.map(prod => prod.id),
      dateFinished: order.dateFinished,
      status: StatusMapping[order.status]
    };
    return this.http.post<any>(this.ordersUrl, orderToSent, this.httpOptions).pipe(
      catchError(this.handleError<any>('addOrder'))
    );
  }

  addProductToOrder(id: number, product: Product): Observable<Order> {
    return this.http.post<Order>(`${this.ordersUrl}/add-product/${id}`, product, this.httpOptions).pipe(
      catchError(this.handleError<Order>('addOrder'))
    );
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      console.error(error);

      return of(result as T);
    };
  }
}
