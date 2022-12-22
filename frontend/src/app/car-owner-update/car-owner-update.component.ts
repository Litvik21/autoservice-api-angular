import { Component, OnInit } from '@angular/core';
import {Car} from "../model/car";
import {CarOwner} from "../model/carOwner";
import {ActivatedRoute} from "@angular/router";
import {CarService} from "../service/car.service";
import {Location} from "@angular/common";
import {CarOwnerService} from "../service/carOwner.service";
import {Order} from "../model/order";
import {OrderService} from "../service/order.service";

@Component({
  selector: 'app-car-owner-update',
  templateUrl: './car-owner-update.component.html',
  styleUrls: ['./car-owner-update.component.scss']
})
export class CarOwnerUpdateComponent implements OnInit {

  owner!: CarOwner;
  cars: Car[] = [];
  orders: Order[] = []

  constructor(
    private route: ActivatedRoute,
    private carService: CarService,
    private location: Location,
    private ownerService: CarOwnerService,
    private orderService: OrderService
  ) {}

  ngOnInit(): void {
    this.getCars();
    this.getOwner();
    this.getOrders();
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
      .subscribe(owner => this.owner = owner);
  }

  goBack(): void {
    this.location.back();
  }

  save(): void {
    this.ownerService.updateCarOwner(this.owner)
      .subscribe(() => this.goBack());
  }
}
