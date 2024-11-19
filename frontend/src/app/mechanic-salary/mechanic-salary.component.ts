import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MechanicService } from '../service/mechanic.service';
import { Location } from '@angular/common';

@Component({
  selector: 'app-mechanic-salary',
  templateUrl: './mechanic-salary.component.html',
  styleUrls: ['./mechanic-salary.component.scss']
})
export class MechanicSalaryComponent implements OnInit {
  mechanic: any;
  salary: any;

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

    this.mechanicService.getSalary(id)
      .subscribe(salary => {
        console.log(salary);
        this.salary = salary});
  }

  goBack(): void {
    this.location.back();
  }
}
