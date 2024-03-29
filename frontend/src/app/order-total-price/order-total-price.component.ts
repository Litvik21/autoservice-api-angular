import { Component, OnInit } from '@angular/core';
import { Order } from '../model/order';
import { ActivatedRoute } from '@angular/router';
import { OrderService } from '../service/order.service';
import { CarService } from '../service/car.service';
import { Location } from '@angular/common';

@Component({
  selector: 'app-order-total-price',
  templateUrl: './order-total-price.component.html',
  styleUrls: ['./order-total-price.component.scss']
})
export class OrderTotalPriceComponent implements OnInit {
  order!: Order;
  totalPrice!: Number;

  constructor(
    private route: ActivatedRoute,
    private orderService: OrderService,
    private carService: CarService,
    private location: Location,
  ) {}

  ngOnInit(): void {
    this.getOrder();
  }

  getOrder(): void {
    const id = +this.route.snapshot.paramMap.get('id')!;
    this.orderService.getOrder(id)
      .subscribe(order => this.order = order);
  }

  goBack(): void {
    this.location.back();
  }

  getTotalPrice(): void {
    this.orderService.getTotalPrice(this.order.id!)
      .subscribe(totalPrice => this.totalPrice = totalPrice);
  }
}
