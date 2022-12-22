import { Component, OnInit } from '@angular/core';
import {CarOwner} from "../model/carOwner";
import {CarOwnerService} from "../service/carOwner.service";
import {Car} from "../model/car";
import {Order} from "../model/order";
import {CarService} from "../service/car.service";
import {OrderService} from "../service/order.service";
import {FormBuilder, FormGroup} from "@angular/forms";

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

  constructor(private ownerService: CarOwnerService,
              private carService: CarService,
              private orderService:OrderService,
              private fb:FormBuilder) { }

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
    this.newCars.push(this.cars.find(c => c.id == this.carForm.value)!)
  }

  submitOrder() {
    this.newOrders.push(this.orders.find(o => o.id == this.orderForm.value)!)
  }

  add(): void {
    let id = Math.max.apply(Math, this.owners.map(function (o) {return o.id;}))
    this.ownerService.addCarOwner({id: id + 1, cars: this.newCars, orders: this.newOrders} as CarOwner)
      .subscribe(owner => {this.owners.push(owner)})
  }
}
