import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { TaskService } from '../service/task.service';
import { Location } from '@angular/common';
import { FormBuilder, FormGroup } from '@angular/forms';
import { PaymentStatus, PaymentStatusMapping } from '../model/paymentStatus';

@Component({
  selector: 'app-task-update-status',
  templateUrl: './task-update-status.component.html',
  styleUrls: ['./task-update-status.component.scss']
})
export class TaskUpdateStatusComponent implements OnInit {
  paymentStatusForm!: FormGroup;
  task: any;
  paymentStatus!: PaymentStatus;
  statuses = Object.values(PaymentStatus);
  paymentStatusMapping = PaymentStatusMapping;

  constructor(
    private route: ActivatedRoute,
    private taskService: TaskService,
    private location: Location,
    private fb: FormBuilder
  ) {
  }

  ngOnInit(): void {
    this.getTask();
  }

  getTask(): void {
    const id = +this.route.snapshot.paramMap.get('id')!;
    this.taskService.getTask(id)
      .subscribe(task => {
        this.task = task;
        this.paymentStatusForm = this.fb.group({
          status: [this.task.paymentStatus]
        });
      });
  }

  submitPaymentStatus() {
    this.paymentStatus = this.paymentStatusForm.get('status')!.value;
  }

  goBack(): void {
    this.location.back();
  }

  save(): void {
    this.submitPaymentStatus();

    this.taskService.updateStatus(this.task.id!, this.paymentStatus)
      .subscribe(() => this.goBack());
  }
}
