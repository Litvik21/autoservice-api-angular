import { Component, OnInit } from '@angular/core';
import {Mechanic} from "../model/mechanic";
import {MechanicService} from "../service/mechanic.service";
import {Order} from "../model/order";
import {OrderService} from "../service/order.service";
import {FormBuilder, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-mechanic',
  templateUrl: './mechanic.component.html',
  styleUrls: ['./mechanic.component.scss']
})
export class MechanicComponent implements OnInit {
  orderForm!: FormGroup;

  mechanics: Mechanic[] = [];
  orders: Order[] = [];
  newOrders: Order[] = [];

  mechanicName = "";

  constructor(private mechanicService: MechanicService,
              private orderService: OrderService,
              private fb: FormBuilder) { }

  ngOnInit() {
    this.getMechanics();
    this.getOrders();
    this.orderForm = this.fb.group({
      order: [null]
    })
  }

  getMechanics(): void {
    this.mechanicService.getMechanics()
      .subscribe(mechanics => this.mechanics = mechanics);
  }

  getOrders(): void {
    this.orderService.getOrders()
      .subscribe(orders => this.orders = orders);
  }

  submitOrder() {
    this.newOrders.push(this.orders.find(o => o.id == this.orderForm.value)!)
  }

  add(): void {
    let id = Math.max.apply(Math, this.mechanics.map(function (o) {return o.id;}));

    this.mechanicService.addMechanic({id: id + 1, name: this.mechanicName, finishedOrders: this.newOrders} as Mechanic)
      .subscribe(mechanic => {this.mechanics.push(mechanic)});

    this.mechanicName = "";
  }
}
