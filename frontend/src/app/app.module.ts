import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ProductComponent } from './product/product.component';
import { CarComponent } from './car/car.component';
import { MechanicComponent } from './mechanic/mechanic.component';
import { CarOwnerComponent } from './car-owner/car-owner.component';
import { TaskComponent } from './task/task.component';
import { OrderComponent } from './order/order.component'
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import { ProductUpdateComponent } from './product-update/product-update.component';
import { TaskUpdateComponent } from './task-update/task-update.component';
import {HttpClient, HttpClientModule} from "@angular/common/http";
import { CarUpdateComponent } from './car-update/car-update.component';
import { CarOwnerUpdateComponent } from './car-owner-update/car-owner-update.component';
import { MechanicUpdateComponent } from './mechanic-update/mechanic-update.component';
import { OrderUpdateComponent } from './order-update/order-update.component';
import { OwnerOrdersComponent } from './owner-orders/owner-orders.component';
import { MechanicFinishedOrdersComponent } from './mechanic-finished-orders/mechanic-finished-orders.component';
import { MechanicSalaryComponent } from './mechanic-salary/mechanic-salary.component';
import { OrderAddProductComponent } from './order-add-product/order-add-product.component';
import { OrderUpdateStatusComponent } from './order-update-status/order-update-status.component';
import { OrderTotalPriceComponent } from './order-total-price/order-total-price.component';
import { TaskUpdateStatusComponent } from './task-update-status/task-update-status.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations'
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {DateAdapter, MAT_DATE_FORMATS} from "@angular/material/core";
import {AppDateAdapter, APP_DATE_FORMATS} from "./adapter/date.adapter";

@NgModule({
  declarations: [
    AppComponent,
    ProductComponent,
    CarComponent,
    MechanicComponent,
    CarOwnerComponent,
    TaskComponent,
    OrderComponent,
    ProductUpdateComponent,
    TaskUpdateComponent,
    CarUpdateComponent,
    CarOwnerUpdateComponent,
    MechanicUpdateComponent,
    OrderUpdateComponent,
    OwnerOrdersComponent,
    MechanicFinishedOrdersComponent,
    MechanicSalaryComponent,
    OrderAddProductComponent,
    OrderUpdateStatusComponent,
    OrderTotalPriceComponent,
    TaskUpdateStatusComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule,
    BrowserAnimationsModule,
    MatFormFieldModule,
    MatInputModule,
    MatDatepickerModule
  ],
  providers: [
    {provide: DateAdapter, useClass: AppDateAdapter},
    {provide: MAT_DATE_FORMATS, useValue: APP_DATE_FORMATS}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
