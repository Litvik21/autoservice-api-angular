import { Component, OnInit } from '@angular/core';
import {Order} from "../model/order";
import {OrderService} from "../service/order.service";
import {Car} from "../model/car";
import {Task} from "../model/task";
import {Product} from "../model/product";
import {CarService} from "../service/car.service";
import {ProductService} from "../service/product.service";
import {TaskService} from "../service/task.service";
import {FormBuilder, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.scss']
})
export class OrderComponent implements OnInit {
  productForm!: FormGroup;
  carForm!: FormGroup;
  taskForm!: FormGroup;

  orders: Order[] = [];
  cars: Car[] = [];
  tasks: Task[] = [];
  products: Product[] = [];
  newCar!: Car;
  newTasks: Task[] = [];
  newProducts: Product[] = [];

  description = "";
  dateFinished = new Date;
  picker = new Date;


  constructor(private orderService: OrderService,
              private carService: CarService,
              private productService: ProductService,
              private taskService: TaskService,
              private fb: FormBuilder) { }

  ngOnInit() {
    this.getOrders();
    this.getCars();
    this.getProducts();
    this.getTasks();
    this.productForm = this.fb.group({
      product: [null]
    })
    this.taskForm = this.fb.group({
      task: [null]
    })
    this.carForm = this.fb.group({
      car: [null]
    })
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
      .subscribe(orders => this.orders = orders);
  }

  submitProduct() {
    this.newProducts.push(this.products.find(p => p.id == this.productForm.value)!)
  }

  submitTask() {
    this.newTasks.push(this.tasks.find(t => t.id == this.taskForm.value)!)
  }

  submitCar() {
    this.newCar = this.cars.find(c => c.id == this.carForm.value)!
  }

  add(): void {
    let id = Math.max.apply(Math, this.orders.map(function (o) {return o.id!;} ));

    this.orderService.addOrder({id: id + 1, car: this.newCar, dateFinished: this.dateFinished,
      description: this.description, tasks: this.newTasks, products: this.newProducts} as Order)
      .subscribe(order => {this.orders.push(order)});

    this.description = "";
    this.dateFinished = new Date;
  }
}
