import { Component, OnInit } from '@angular/core';
import {Order} from "../model/order";
import {ActivatedRoute} from "@angular/router";
import {OrderService} from "../service/order.service";
import {Location} from "@angular/common";
import {Status, StatusMapping} from "../model/status";
import {FormBuilder, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-order-update-status',
  templateUrl: './order-update-status.component.html',
  styleUrls: ['./order-update-status.component.scss']
})
export class OrderUpdateStatusComponent implements OnInit {
  statusForm!: FormGroup;

  order!: Order;
  status!: Status;
  statuses = Object.values(Status);
  StatusMapping = StatusMapping;

  constructor(
    private route: ActivatedRoute,
    private orderService: OrderService,
    private location: Location,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.getOrder();
    this.statusForm = this.fb.group({
      status: [null]
    })
  }

  getOrder(): void {
    const id = +this.route.snapshot.paramMap.get('id')!;
    this.orderService.getOrder(id)
      .subscribe(order => this.order = order);
  }

  submitStatus() {
    this.status = this.statusForm.value
  }

  goBack(): void {
    this.location.back();
  }

  save(): void {
    this.orderService.updateStatus(this.order.id!, this.status)
      .subscribe(() => this.goBack());
  }
}
