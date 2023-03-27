import { Component, OnInit } from '@angular/core';
import { Car } from '../model/car';
import { Task } from '../model/task';
import { Product } from '../model/product';
import { OrderService } from '../service/order.service';
import { CarService } from '../service/car.service';
import { ProductService } from '../service/product.service';
import { TaskService } from '../service/task.service';
import { Router } from '@angular/router';
import { Order } from '../model/order';

@Component({
  selector: 'app-order-info',
  templateUrl: './order-info.component.html',
  styleUrls: ['./order-info.component.scss']
})
export class OrderInfoComponent implements OnInit {
  orders: Order[] = [];
  cars: Car[] = [];
  tasks: Task[] = [];
  products: Product[] = [];
  number = '';

  constructor(private orderService: OrderService,
              private carService: CarService,
              private productService: ProductService,
              private taskService: TaskService,
              private router: Router) {
  }

  ngOnInit() {
    this.getOrders();
    this.getCars();
    this.getProducts();
    this.getTasks();
  }

  getTasks(): void {
    this.taskService.getTasks()
      .subscribe(tasks => this.tasks = tasks);
  }

  getProducts(): void {
    this.productService.getProducts()
      .subscribe(products => this.products = products);
  }

  getCars(): void {
    this.carService.getCars()
      .subscribe(cars => this.cars = cars);
  }

  getOrders(): void {
    this.orderService.getOrders()
      .subscribe(orders => {
        this.orders = orders
      });
  }

  update(orderId: any) {
    this.router.navigate(['/orders', orderId]);
  }

  updateStatus(orderId: any) {
    this.router.navigate(['/orders/update-status', orderId]);
  }

  addProduct(orderId: any) {
    this.router.navigate(['/orders/add-product', orderId]);
  }

  getPrice(orderId: any) {
    this.router.navigate(['/orders/price', orderId]);
  }
}
