import { Component, OnInit } from '@angular/core';
import { Order } from '../model/order';
import { OrderService } from '../service/order.service';
import { Car } from '../model/car';
import { Task } from '../model/task';
import { Product } from '../model/product';
import { CarService } from '../service/car.service';
import { ProductService } from '../service/product.service';
import { TaskService } from '../service/task.service';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Status, StatusMapping } from '../model/status';

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.scss']
})
export class OrderComponent implements OnInit {
  productForm!: FormGroup;
  carForm!: FormGroup;
  taskForm!: FormGroup;
  statusForm!: FormGroup;

  orders: Order[] = [];
  cars: Car[] = [];
  tasks: Task[] = [];
  products: Product[] = [];
  newCar!: Car;
  newTasks: Task[] = [];
  newProducts: Product[] = [];
  status!: Status;
  statuses = Object.values(Status);
  statusMapping = StatusMapping;
  description = '';
  picker = new Date;


  constructor(private orderService: OrderService,
              private carService: CarService,
              private productService: ProductService,
              private taskService: TaskService,
              private fb: FormBuilder) {
  }

  ngOnInit() {
    this.getOrders();
    this.getCars();
    this.getProducts();
    this.getTasks();
    this.productForm = this.fb.group({
      product: [null]
    });
    this.taskForm = this.fb.group({
      task: [null]
    });
    this.carForm = this.fb.group({
      car: [null]
    });
    this.statusForm = this.fb.group({
      status: [null]
    });
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
    if (this.productForm.valid) {
      const productID = this.productForm.get('product')!.value;
      if (productID !== null) {
        this.newProducts.push(this.products.find(p => p.id === productID)!);
      }
    }
  }

  submitTask() {
    if (this.taskForm.valid) {
      const taskId = this.taskForm.get('task')!.value;
      if (taskId !== null) {
        this.newTasks.push(this.tasks.find(t => t.id === taskId)!);
      }
    }
  }

  submitCar() {
    if (this.carForm.valid) {
      const carId = this.carForm.get('car')!.value;
      if (carId !== null) {
        this.newCar = this.cars.find(c => c.id === carId)!;
      }
    }
  }

  submitStatus() {
    this.status = this.statusForm.get('status')!.value;
  }

  add(): void {
    let id = Math.max.apply(Math, this.orders.map(function (o) {
      return o.id!;
    }));

    this.orderService.addOrder({
      id: id + 1, car: this.newCar, dateFinished: this.picker,
      description: this.description, tasks: this.newTasks,
      products: this.newProducts, status: this.status
    } as Order)
      .subscribe(order => {
        this.orders.push(order);
      });

    this.description = '';
    this.productForm.reset();
    this.carForm.reset();
    this.taskForm.reset();
    this.statusForm.reset();
  }
}
