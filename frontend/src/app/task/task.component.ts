import {Component, OnInit} from '@angular/core';
import {Task} from "../model/task";
import {TaskService} from "../service/task.service";
import {Mechanic} from "../model/mechanic";
import {OrderService} from "../service/order.service";
import {MechanicService} from "../service/mechanic.service";
import {Order} from "../model/order";
import {PaymentStatus, PaymentStatusMapping} from "../model/paymentStatus";
import {TypeOfTask, TypeOfTaskMapping} from "../model/typeOfTask";
import {FormBuilder, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-task',
  templateUrl: './task.component.html',
  styleUrls: ['./task.component.scss']
})
export class TaskComponent implements OnInit {
  orderForm!: FormGroup;
  mechanicForm!: FormGroup;
  paymentStatusForm!: FormGroup;
  typeOfTaskForm!: FormGroup;

  tasks: Task[] = [];
  orders: Order[] = [];
  mechanics: Mechanic[] = [];
  newMechanic!: Mechanic;
  newOrder!: Order;
  paymentStatus!: PaymentStatus;
  statuses = Object.values(PaymentStatus);
  PaymentStatusMapping = PaymentStatusMapping;
  type!: TypeOfTask;
  types = Object.values(TypeOfTask);
  TypeOfTaskMapping = TypeOfTaskMapping;

  price = 0;

  constructor(private taskService: TaskService,
              private orderService: OrderService,
              private mechanicService: MechanicService,
              private fb: FormBuilder) { }

  ngOnInit() {
    this.getOrders();
    this.getMechanics();
    this.getTasks();
    this.paymentStatusForm = this.fb.group({
      paymentStatus: [null]
    })
    this.typeOfTaskForm = this.fb.group({
      typeOfTask: [null]
    })
    this.mechanicForm = this.fb.group({
      mechanic: [null]
    })
    this.orderForm = this.fb.group({
      order: [null]
    })
  }

  getOrders(): void {
    this.orderService.getOrders()
      .subscribe(orders => this.orders = orders);
  }

  getMechanics(): void {
    this.mechanicService.getMechanics()
      .subscribe(mechanics => this.mechanics = mechanics);
  }

  getTasks(): void {
    this.taskService.getTasks()
      .subscribe(tasks => this.tasks = tasks);
  }

  submitPaymentStatus() {
    this.paymentStatus = this.paymentStatusForm.value;
  }

  submitTypeOfTask() {
    this.type = this.typeOfTaskForm.value;
  }

  submitMechanic() {
    this.newMechanic = this.mechanics.find(m => m.id == this.mechanicForm.value)!
  }

  submitOrder() {
    this.newOrder = this.orders.find(o => o.id == this.orderForm.value)!
  }

  add(): void {
    let id = Math.max.apply(Math, this.tasks.map(function (o) {return o.id!;} ));

    this.taskService.addTask({id: id + 1, typeOfTask: this.type, price: this.price,
      order: this.newOrder, mechanic: this.newMechanic, paymentStatus: this.paymentStatus} as Task)
      .subscribe(task => {this.tasks.push(task)});

    this.price = 0;
  }
}
