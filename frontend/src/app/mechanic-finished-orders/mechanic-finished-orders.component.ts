import { Component, OnInit } from '@angular/core';
import {Order} from "../model/order";
import {ActivatedRoute} from "@angular/router";
import {Location} from "@angular/common";
import {Mechanic} from "../model/mechanic";
import {MechanicService} from "../service/mechanic.service";

@Component({
  selector: 'app-mechanic-finished-orders',
  templateUrl: './mechanic-finished-orders.component.html',
  styleUrls: ['./mechanic-finished-orders.component.scss']
})
export class MechanicFinishedOrdersComponent implements OnInit {

  mechanic!: Mechanic;
  orders: Order[] = []

  constructor(
    private route: ActivatedRoute,
    private mechanicService: MechanicService,
    private location: Location
  ) {}

  ngOnInit(): void {
    this.getMechanic();
  }

  getMechanic(): void {
    const id = +this.route.snapshot.paramMap.get('id')!;
    this.mechanicService.getMechanic(id)
      .subscribe(mechanic => this.mechanic = mechanic);
  }

  goBack(): void {
    this.location.back();
  }

  getOrders(): void {
    this.mechanicService.getFinishedOrders(this.mechanic.id)
      .subscribe(orders => this.orders = orders);
  }
}
