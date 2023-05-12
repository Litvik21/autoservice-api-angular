import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { Order } from '../model/order';
import { OrderService } from '../service/order.service';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Car } from '../model/car';
import { Task } from '../model/task';
import { Product } from '../model/product';
import { Status, StatusMapping } from '../model/status';
import { CarService } from '../service/car.service';
import { ProductService } from '../service/product.service';
import { TaskService } from '../service/task.service';

@Component({
  selector: 'app-order-update',
  templateUrl: './order-update.component.html',
  styleUrls: ['./order-update.component.scss']
})
export class OrderUpdateComponent implements OnInit {
  productForm!: FormGroup;
  carForm!: FormGroup;
  taskForm!: FormGroup;
  statusForm!: FormGroup;
  order: any;
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
  selectedDate!: Date;

  constructor(private fb: FormBuilder,
              private orderService: OrderService,
              private route: ActivatedRoute,
              private location: Location,
              private carService: CarService,
              private productService: ProductService,
              private taskService: TaskService,) {
  }

  ngOnInit(): void {
    this.getOrder();
    this.getProducts();
    this.getCars();
    this.getTasks();
    this.getOrders();
    this.taskForm = this.fb.group({
      task: [null]
    });
    this.productForm = this.fb.group({
      product: [null]
    });
  }

  getOrders(): void {
    this.orderService.getOrders()
      .subscribe(orders => this.orders = orders);
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

  getOrder(): void {
    const id = +this.route.snapshot.paramMap.get('id')!;
    this.orderService.getOrder(id)
      .subscribe(order => {
        this.order = order;
        this.description = order.description;
        this.carForm = this.fb.group({
          car: [this.order.carId]
        });
        this.statusForm = this.fb.group({
          status: [this.order.status]
        });
      });
  }

  updateOrder(): void {
    this.submitCar();
    this.submitTask();
    this.submitProduct();
    this.submitStatus();

    this.order = {
      id: this.order.id,
      carId: this.newCar?.id ?? this.order.carId,
      description: this.description != '' ? this.description : this.order.description,
      productsIds: this.newProducts && this.newProducts.length > 0 ? this.newProducts.map(prod => prod.id) : this.order.productsIds,
      taskIds: this.newTasks && this.newTasks.length > 0 ? this.newTasks.map(task => task.id) : this.order.taskIds,
      status: this.status ?? this.order.status,
      dateFinished: this.selectedDate ? this.selectedDate : this.order.dateFinished
    };

    this.orderService.updateOrder(this.order)
      .subscribe(() => this.goBack());
  }

  goBack(): void {
    this.location.back();
  }
}
