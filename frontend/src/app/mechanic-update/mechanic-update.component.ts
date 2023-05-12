import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { OrderService } from '../service/order.service';
import { MechanicService } from '../service/mechanic.service';
import { Order } from '../model/order';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-mechanic-update',
  templateUrl: './mechanic-update.component.html',
  styleUrls: ['./mechanic-update.component.scss']
})
export class MechanicUpdateComponent implements OnInit {
  orderForm!: FormGroup;
  newOrders: Order[] = [];
  mechanic: any;
  orders: Order[] = [];
  name = '';

  constructor(
    private route: ActivatedRoute,
    private mechanicService: MechanicService,
    private location: Location,
    private orderService: OrderService,
    private fb: FormBuilder
  ) {
  }

  ngOnInit(): void {
    this.getMechanic();
    this.getOrders();
    this.orderForm = this.fb.group({
      order: [null]
    });
  }

  getOrders(): void {
    this.orderService.getOrders()
      .subscribe(orders => this.orders = orders);
  }

  submitOrder() {
    if (this.orderForm.valid) {
      const orderId = this.orderForm.get('order')!.value;
      if (orderId !== null) {
        this.newOrders.push(this.orders.find(t => t.id === orderId)!);
      }
    }
  }

  getMechanic(): void {
    const id = +this.route.snapshot.paramMap.get('id')!;
    this.mechanicService.getMechanic(id)
      .subscribe(mechanic => {
        this.mechanic = mechanic;
        this.name = mechanic.name;
      });
  }

  goBack(): void {
    this.location.back();
  }

  save(): void {
    this.submitOrder();

    this.mechanic = {
      id: this.mechanic.id,
      name: this.name != '' ? this.name : this.mechanic.name,
      finishedOrdersId: this.newOrders && this.newOrders.length > 0 ?
        this.newOrders.map(order => order.id) : this.mechanic.finishedOrdersId
    };
    this.mechanicService.updateMechanic(this.mechanic)
      .subscribe(() => this.goBack());
  }
}
