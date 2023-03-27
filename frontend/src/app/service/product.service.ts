import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Product } from '../model/product';
import { environment } from '../../environments/environment';

@Injectable({providedIn: 'root'})
export class ProductService {
  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };
  private productsUrl = environment.urlPath + '/products';

  constructor(
    private http: HttpClient) {
  }

  getProducts(): Observable<Product[]> {
    return this.http.get<Product[]>(this.productsUrl)
      .pipe(
        catchError(this.handleError<Product[]>('getProducts', []))
      );
  }

  getProduct(id: number): Observable<Product> {
    const url = `${this.productsUrl}/get/${id}`;
    return this.http.get<Product>(url).pipe(
      catchError(this.handleError<Product>(`getProduct id=${id}`))
    );
  }

  updateProduct(product: Product): Observable<any> {
    const url = `${this.productsUrl}/${product.id}`
    return this.http.put(url, product, this.httpOptions).pipe(
      catchError(this.handleError<any>('updateProduct'))
    );
  }

  addProduct(product: Product): Observable<Product> {
    return this.http.post<Product>(this.productsUrl, product, this.httpOptions).pipe(
      catchError(this.handleError<Product>('addProduct'))
    );
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      console.error(error);

      return of(result as T);
    };
  }
}
