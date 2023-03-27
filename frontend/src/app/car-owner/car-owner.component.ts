import { Component, OnInit } from '@angular/core';
import { CarOwner } from '../model/carOwner';
import { CarOwnerService } from '../service/carOwner.service';
import { Car } from '../model/car';
import { Order } from '../model/order';
import { CarService } from '../service/car.service';
import { OrderService } from '../service/order.service';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-car-owner',
  templateUrl: './car-owner.component.html',
  styleUrls: ['./car-owner.component.scss']
})
export class CarOwnerComponent implements OnInit {
  orderForm!: FormGroup;
  carForm!: FormGroup;

  owners: CarOwner[] = [];
  cars: Car[] = [];
  orders: Order[] = [];
  newCars: Car[] = [];
  newOrders: Order[] = [];
  name = '';

  constructor(private ownerService: CarOwnerService,
              private carService: CarService,
              private orderService: OrderService,
              private fb: FormBuilder) {
  }

  ngOnInit() {
    this.getCarOwners();
    this.getCars();
    this.getOrders();
    this.orderForm = this.fb.group({
      order: [null]
    })
    this.carForm = this.fb.group({
      car: [null]
    })
  }

  getCars(): void {
    this.carService.getCars()
      .subscribe(cars => this.cars = cars);
  }

  getOrders(): void {
    this.orderService.getOrders()
      .subscribe(orders => this.orders = orders);
  }

  getCarOwners(): void {
    this.ownerService.getCarOwners()
      .subscribe(owners => this.owners = owners);
  }

  submitCar() {
    if (this.carForm.valid) {
      const carId = this.carForm.get('car')!.value;
      if (carId !== null) {
        this.newCars.push(this.cars.find(c => c.id === carId)!);
      }
    }
  }

  submitOrder() {
    if (this.orderForm.valid) {
      const orderId = this.orderForm.get('order')!.value;
      if (orderId !== null) {
        this.newOrders.push(this.orders.find(o => o.id === orderId)!);
      }
    }
  }

  add(): void {
    let id = Math.max.apply(Math, this.owners.map(function (o) {
      return o.id;
    }));
    this.ownerService.addCarOwner({id: id + 1, name: this.name, cars: this.newCars, orders: this.newOrders} as CarOwner)
      .subscribe(owner => {
        this.owners.push(owner);
      });

    this.name = '';
    this.orderForm.reset();
    this.carForm.reset();
  }
}
