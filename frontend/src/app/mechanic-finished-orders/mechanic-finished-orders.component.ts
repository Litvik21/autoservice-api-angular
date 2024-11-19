import { Component, OnInit } from '@angular/core';
import { Order } from '../model/order';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { MechanicService } from '../service/mechanic.service';

@Component({
  selector: 'app-mechanic-finished-orders',
  templateUrl: './mechanic-finished-orders.component.html',
  styleUrls: ['./mechanic-finished-orders.component.scss']
})
export class MechanicFinishedOrdersComponent implements OnInit {
  orders: Order[] = [];

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
    this.mechanicService.getFinishedOrders(id)
      .subscribe(orders => this.orders = orders);
  }

  goBack(): void {
    this.location.back();
  }
}
