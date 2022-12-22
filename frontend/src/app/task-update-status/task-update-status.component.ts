import { Component, OnInit } from '@angular/core';
import {Task} from "../model/task";
import {ActivatedRoute} from "@angular/router";
import {TaskService} from "../service/task.service";
import {Location} from "@angular/common";
import {FormBuilder, FormGroup} from "@angular/forms";
import {PaymentStatus, PaymentStatusMapping} from "../model/paymentStatus";

@Component({
  selector: 'app-task-update-status',
  templateUrl: './task-update-status.component.html',
  styleUrls: ['./task-update-status.component.scss']
})
export class TaskUpdateStatusComponent implements OnInit {
  statusForm!: FormGroup;

  statuses = Object.values(PaymentStatus);
  PaymentStatusMapping = PaymentStatusMapping;
  task!: Task;
  status!: string;

  constructor(
    private route: ActivatedRoute,
    private taskService: TaskService,
    private location: Location,
    private fb: FormBuilder
  ) { }

  ngOnInit(): void {
    this.getTask();
    this.statusForm = this.fb.group({
      status: [null]
    })
  }

  getTask(): void {
    const id = +this.route.snapshot.paramMap.get('id')!;
    this.taskService.getTask(id)
      .subscribe(task => this.task = task);
  }

  submitStatus() {
    this.status = this.statusForm.value
  }

  goBack(): void {
    this.location.back();
  }

  save(): void {
    this.taskService.updateStatus(this.task.id!, this.status)
      .subscribe(() => this.goBack());
  }
}
