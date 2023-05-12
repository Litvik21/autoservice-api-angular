import { Component, OnInit } from '@angular/core';
import { Car } from '../model/car';
import { ActivatedRoute } from '@angular/router';
import { CarService } from '../service/car.service';
import { Location } from '@angular/common';
import { CarOwnerService } from '../service/carOwner.service';
import { Order } from '../model/order';
import { OrderService } from '../service/order.service';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-car-owner-update',
  templateUrl: './car-owner-update.component.html',
  styleUrls: ['./car-owner-update.component.scss']
})
export class CarOwnerUpdateComponent implements OnInit {
  orderForm!: FormGroup;
  carForm!: FormGroup;
  owner: any;
  cars: Car[] = [];
  orders: Order[] = [];
  newCars: Car[] = [];
  newOrders: Order[] = [];
  name = '';

  constructor(
    private route: ActivatedRoute,
    private carService: CarService,
    private location: Location,
    private ownerService: CarOwnerService,
    private orderService: OrderService,
    private fb: FormBuilder
  ) {
  }

  ngOnInit(): void {
    this.getCars();
    this.getOwner();
    this.getOrders();
    this.orderForm = this.fb.group({
      order: [null]
    });
    this.carForm = this.fb.group({
      car: [null]
    });
  }

  getOrders(): void {
    this.orderService.getOrders()
      .subscribe(orders => this.orders = orders)
  }

  getCars(): void {
    this.carService.getCars()
      .subscribe(cars =>  this.cars = cars);
  }

  getOwner(): void {
    const id = +this.route.snapshot.paramMap.get('id')!;
    this.ownerService.getCarOwner(id)
      .subscribe(owner => {
        this.owner = owner;
        this.name = owner.name;
      });
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

  goBack(): void {
    this.location.back();
  }

  save(): void {
    this.submitCar();
    this.submitOrder();

    this.owner = {
      id: this.owner.id,
      name: this.name != '' ? this.name : this.owner.name,
      carsId: this.newCars && this.newCars.length > 0 ? this.newCars.map(car => car.id) : this.owner.carsId,
      ordersId: this.newOrders && this.newOrders.length > 0 ? this.newOrders.map(order => order.id) : this.owner.ordersId
    };
    this.ownerService.updateCarOwner(this.owner)
      .subscribe(() => this.goBack());
  }
}
