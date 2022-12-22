import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {Location} from "@angular/common";
import {Mechanic} from "../model/mechanic";
import {OrderService} from "../service/order.service";
import {MechanicService} from "../service/mechanic.service";
import {Order} from "../model/order";

@Component({
  selector: 'app-mechanic-update',
  templateUrl: './mechanic-update.component.html',
  styleUrls: ['./mechanic-update.component.scss']
})
export class MechanicUpdateComponent implements OnInit {

  mechanic!: Mechanic;
  orders: Order[] = [];

  constructor(
    private route: ActivatedRoute,
    private mechanicService: MechanicService,
    private location: Location,
    private orderService: OrderService
  ) {}

  ngOnInit(): void {
    this.getMechanic();
    this.getOrders();
  }

  getOrders(): void {
    this.orderService.getOrders()
      .subscribe(orders =>  this.orders = orders);
  }

  getMechanic(): void {
    const id = +this.route.snapshot.paramMap.get('id')!;
    this.mechanicService.getMechanic(id)
      .subscribe(mechanic => this.mechanic = mechanic);
  }

  goBack(): void {
    this.location.back();
  }

  save(): void {
    this.mechanicService.updateMechanic(this.mechanic)
      .subscribe(() => this.goBack());
  }
}
