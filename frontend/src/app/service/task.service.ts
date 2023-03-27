import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Task } from '../model/task';
import { environment } from '../../environments/environment';
import { TypeOfTaskMapping } from '../model/typeOfTask';
import { PaymentStatusMapping } from '../model/paymentStatus';

@Injectable({providedIn: 'root'})
export class TaskService {
  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };
  private taskUrl = environment.urlPath + '/tasks';

  constructor(
    private http: HttpClient) {
  }

  getTasks(): Observable<Task[]> {
    return this.http.get<Task[]>(this.taskUrl)
      .pipe(
        catchError(this.handleError<Task[]>('getTasks', []))
      );
  }

  getTask(id: number): Observable<Task> {
    const url = `${this.taskUrl}/${id}`;
    return this.http.get<Task>(url).pipe(
      catchError(this.handleError<Task>(`getTask id=${id}`))
    );
  }

  updateTask(task: Task): Observable<any> {
    const url = `${this.taskUrl}/${task.id}`
    return this.http.put(url, task, this.httpOptions).pipe(
      catchError(this.handleError<any>('updateTask'))
    );
  }

  updateStatus(id: number, status: String): Observable<any> {
    const url = `${this.taskUrl}/update-status/${id}`;
    return this.http.put(url, status, this.httpOptions).pipe(
      catchError(this.handleError<any>('updateStatus'))
    );
  }

  addTask(task: Task): Observable<any> {
    if (task.typeOfTask === undefined || task.paymentStatus === undefined) {
      throw new Error('Task type is undefined');
    }
    const taskToSent = {
      type: TypeOfTaskMapping[task.typeOfTask],
      orderId: task.order?.id,
      mechanicId: task.mechanic?.id,
      price: task.price,
      paymentStatus: PaymentStatusMapping[task.paymentStatus]
    };
    console.log(taskToSent);
    return this.http.post<any>(this.taskUrl, taskToSent, this.httpOptions).pipe(
      catchError(this.handleError<any>('addTask'))
    );
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      console.error(error);
      return of(result as T);
    };
  }
}
