import { Component, OnInit } from '@angular/core';
import {Mechanic} from "../model/mechanic";
import {ActivatedRoute} from "@angular/router";
import {MechanicService} from "../service/mechanic.service";
import {Location} from "@angular/common";

@Component({
  selector: 'app-mechanic-salary',
  templateUrl: './mechanic-salary.component.html',
  styleUrls: ['./mechanic-salary.component.scss']
})
export class MechanicSalaryComponent implements OnInit {

  mechanic!: Mechanic;
  salary!: Number;

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
    this.mechanicService.getMechanic(id)
      .subscribe(mechanic => this.mechanic = mechanic);
  }

  goBack(): void {
    this.location.back();
  }

  getSalary(): void {
    this.mechanicService.getSalary(this.mechanic.id)
      .subscribe(salary => this.salary = salary);
  }
}
