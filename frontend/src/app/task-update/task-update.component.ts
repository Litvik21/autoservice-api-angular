import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { TaskService } from '../service/task.service';
import { FormBuilder, FormGroup } from '@angular/forms';
import { PaymentStatus, PaymentStatusMapping } from '../model/paymentStatus';
import { TypeOfTask, TypeOfTaskMapping } from '../model/typeOfTask';
import { OrderService } from '../service/order.service';
import { MechanicService } from '../service/mechanic.service';
import { Mechanic } from '../model/mechanic';
import { Order } from '../model/order';

@Component({
  selector: 'app-task-update',
  templateUrl: './task-update.component.html',
  styleUrls: ['./task-update.component.scss']
})
export class TaskUpdateComponent implements OnInit {
  orderForm!: FormGroup;
  mechanicForm!: FormGroup;
  paymentStatusForm!: FormGroup;
  typeOfTaskForm!: FormGroup;
  task: any;
  orders: Order[] = [];
  mechanics: Mechanic[] = [];
  newMechanic!: Mechanic;
  newOrder!: Order;
  paymentStatus!: PaymentStatus;
  statuses = Object.values(PaymentStatus);
  paymentStatusMapping = PaymentStatusMapping;
  type!: TypeOfTask;
  types = Object.values(TypeOfTask);
  typeOfTaskMapping = TypeOfTaskMapping;
  price = 0;

  constructor(
    private orderService: OrderService,
    private mechanicService: MechanicService,
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private taskService: TaskService,
    private location: Location
  ) {
  }

  ngOnInit(): void {
    this.getOrders();
    this.getTask();
    this.getMechanics();
  }

  getTask(): void {
    const id = +this.route.snapshot.paramMap.get('id')!;
    this.taskService.getTask(id)
      .subscribe(task => {
        this.task = task;
        this.price = task.price!;
        this.typeOfTaskForm = this.fb.group({
          type: [this.task.type]
        });
        this.paymentStatusForm = this.fb.group({
          status: [this.task.paymentStatus]
        });
        this.mechanicForm = this.fb.group({
          mechanic: [this.task.mechanicId]
        });
        this.orderForm = this.fb.group({
          order: [this.task.orderId]
        });
      });
  }

  getOrders(): void {
    this.orderService.getOrders()
      .subscribe(orders => this.orders = orders);
  }

  getMechanics(): void {
    this.mechanicService.getMechanics()
      .subscribe(mechanics => this.mechanics = mechanics);
  }

  submitPaymentStatus() {
    this.paymentStatus = this.paymentStatusForm.get('status')!.value;
  }

  submitTypeOfTask() {
    this.type = this.typeOfTaskForm.get('type')!.value;
  }

  submitMechanic() {
    if (this.mechanicForm.valid) {
      const mechanicId = this.mechanicForm.get('mechanic')!.value;
      if (mechanicId !== null) {
        this.newMechanic = this.mechanics.find(m => m.id === mechanicId)!;
      }
    }
  }

  submitOrder() {
    if (this.orderForm.valid) {
      const orderId = this.orderForm.get('order')!.value;
      if (orderId !== null) {
        this.newOrder = this.orders.find(o => o.id === orderId)!;
      }
    }
  }

  goBack(): void {
    this.location.back();
  }

  save(): void {
    this.submitTypeOfTask();
    this.submitOrder();
    this.submitMechanic();
    this.submitPaymentStatus();

    this.task = {
      id: this.task.id,
      type: this.type ?? this.task.type,
      orderId: this.newOrder?.id ?? this.task.orderId,
      mechanicId: this.newMechanic?.id ?? this.task.mechanicId,
      price: this.price != 0 ? this.price : this.task.price,
      paymentStatus: this.paymentStatus ?? this.task.paymentStatus
    };
    this.taskService.updateTask(this.task)
      .subscribe(() => this.goBack());
  }
}
