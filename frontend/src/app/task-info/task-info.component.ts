import { Component, OnInit } from '@angular/core';
import { TaskService } from '../service/task.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-task-info',
  templateUrl: './task-info.component.html',
  styleUrls: ['./task-info.component.scss']
})
export class TaskInfoComponent implements OnInit {
  tasks: any;
  constructor(private taskService: TaskService,
              private router: Router) { }

  ngOnInit(): void {
    this.getTasks();
  }

  getTasks(): void {
    this.taskService.getTasks()
      .subscribe(tasks => {
        console.log(tasks)
        this.tasks = tasks
      });
  }

  update(taskId: any): void {
    this.router.navigate(['/tasks', taskId]);
  }

  updateStatus(taskId: any): void {
    this.router.navigate(['/tasks/update-status', taskId]);
  }

}
