import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { CarOwnerService } from '../service/carOwner.service';
import { CarOwner } from '../model/carOwner';
import { Order } from '../model/order';
import { OrderService } from '../service/order.service';

@Component({
  selector: 'app-owner-orders',
  templateUrl: './owner-orders.component.html',
  styleUrls: ['./owner-orders.component.scss']
})
export class OwnerOrdersComponent implements OnInit {
  owner!: CarOwner;
  orders: Order[] = [];

  constructor(
    private route: ActivatedRoute,
    private ownerService: CarOwnerService,
    private orderService: OrderService,
    private location: Location
  ) {}

  ngOnInit(): void {
    this.getOwner();
  }

  getOwner(): void {
    const id = +this.route.snapshot.paramMap.get('id')!;
    this.ownerService.getCarOwner(id)
      .subscribe(owner => this.owner = owner);

    this.ownerService.getOrdersOfOwner(id)
      .subscribe(orders => {
        this.orders = orders;
      });
  }

  goBack(): void {
    this.location.back();
  }

}
