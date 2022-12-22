import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {Location} from "@angular/common";
import {CarOwnerService} from "../service/carOwner.service";
import {CarOwner} from "../model/carOwner";
import {Order} from "../model/order";

@Component({
  selector: 'app-owner-orders',
  templateUrl: './owner-orders.component.html',
  styleUrls: ['./owner-orders.component.scss']
})
export class OwnerOrdersComponent implements OnInit {

  owner!: CarOwner;
  orders: Order[] = []

  constructor(
    private route: ActivatedRoute,
    private ownerService: CarOwnerService,
    private location: Location
  ) {}

  ngOnInit(): void {
    this.getOwner();
  }

  getOwner(): void {
    const id = +this.route.snapshot.paramMap.get('id')!;
    this.ownerService.getCarOwner(id)
      .subscribe(owner => this.owner = owner);
  }

  goBack(): void {
    this.location.back();
  }

  getOrders(): void {
    this.ownerService.getOrdersOfOwner(this.owner.id)
      .subscribe(orders => this.orders = orders);
  }
}
