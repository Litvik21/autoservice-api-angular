import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {ProductComponent} from "./product/product.component";
import {CarComponent} from "./car/car.component";
import {CarOwnerComponent} from "./car-owner/car-owner.component";
import {MechanicComponent} from "./mechanic/mechanic.component";
import {OrderComponent} from "./order/order.component";
import {TaskComponent} from "./task/task.component";
import {TaskUpdateComponent} from "./task-update/task-update.component";
import {ProductUpdateComponent} from "./product-update/product-update.component";
import {CarUpdateComponent} from "./car-update/car-update.component";
import {CarOwnerUpdateComponent} from "./car-owner-update/car-owner-update.component";
import {MechanicUpdateComponent} from "./mechanic-update/mechanic-update.component";
import {OrderUpdateComponent} from "./order-update/order-update.component";
import {OwnerOrdersComponent} from "./owner-orders/owner-orders.component";
import {MechanicFinishedOrdersComponent} from "./mechanic-finished-orders/mechanic-finished-orders.component";
import {MechanicSalaryComponent} from "./mechanic-salary/mechanic-salary.component";
import {OrderUpdateStatusComponent} from "./order-update-status/order-update-status.component";
import {OrderTotalPriceComponent} from "./order-total-price/order-total-price.component";
import {OrderAddProductComponent} from "./order-add-product/order-add-product.component";
import {TaskUpdateStatusComponent} from "./task-update-status/task-update-status.component";

const routes: Routes = [
  {path: 'cars', component: CarComponent},
  {path: 'cars/:id', component: CarUpdateComponent},

  {path: 'car-owners', component: CarOwnerComponent},
  {path: 'car-owners/:id', component: CarOwnerUpdateComponent},
  {path: 'car-owners/orders/:id', component: OwnerOrdersComponent},

  {path: 'mechanics', component: MechanicComponent},
  {path: 'mechanics/:id', component: MechanicUpdateComponent},
  {path: 'mechanics/:id/finished-orders', component: MechanicFinishedOrdersComponent},
  {path: 'mechanics/:id', component: MechanicSalaryComponent},

  {path: 'tasks', component: TaskComponent},
  {path: 'tasks/:id', component: TaskUpdateComponent},
  {path: 'tasks/update-status/:id', component: TaskUpdateStatusComponent},

  {path: 'orders', component: OrderComponent},
  {path: 'orders/:id', component: OrderUpdateComponent},
  {path: 'orders/add-product/:id', component: OrderAddProductComponent},
  {path: 'orders/update-status/:id', component: OrderUpdateStatusComponent},
  {path: 'orders/price/:id', component: OrderTotalPriceComponent},

  {path: 'products', component: ProductComponent},
  {path: 'products/:id', component: ProductUpdateComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
