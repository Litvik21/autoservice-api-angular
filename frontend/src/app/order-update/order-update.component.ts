import { Component, OnInit } from '@angular/core';
import {Car} from "../model/car";
import {ActivatedRoute} from "@angular/router";
import {CarService} from "../service/car.service";
import {Location} from "@angular/common";
import {Order} from "../model/order";
import {OrderService} from "../service/order.service";
import {Product} from "../model/product";
import {TaskService} from "../service/task.service";
import {ProductService} from "../service/product.service";
import {Task} from "../model/task";

@Component({
  selector: 'app-order-update',
  templateUrl: './order-update.component.html',
  styleUrls: ['./order-update.component.scss']
})
export class OrderUpdateComponent implements OnInit {

  order!: Order;
  cars: Car[] = [];
  tasks: Task[] = [];
  products: Product[] = [];

  constructor(
    private route: ActivatedRoute,
    private orderService: OrderService,
    private carService: CarService,
    private location: Location,
    private taskService: TaskService,
    private productService: ProductService
  ) {}

  ngOnInit(): void {
    this.getOrder();
    this.getCars();
    this.getProducts();
    this.getTasks();
  }

  getCars(): void {
    this.carService.getCars()
      .subscribe(cars =>  this.cars = cars);
  }

  getProducts(): void {
    this.productService.getProducts()
      .subscribe(products => this.products = products);
  }

  getTasks(): void {
    this.taskService.getTasks()
      .subscribe(tasks => this.tasks = tasks);
  }

  getOrder(): void {
    const id = +this.route.snapshot.paramMap.get('id')!;
    this.orderService.getOrder(id)
      .subscribe(order => this.order = order);
  }

  goBack(): void {
    this.location.back();
  }

  save(): void {
    this.orderService.updateOrder(this.order)
      .subscribe(() => this.goBack());
  }
}
