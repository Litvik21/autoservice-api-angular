import { Component, OnInit } from '@angular/core';
import { Mechanic } from '../model/mechanic';
import { MechanicService } from '../service/mechanic.service';
import { Order } from '../model/order';
import { OrderService } from '../service/order.service';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-mechanic',
  templateUrl: './mechanic.component.html',
  styleUrls: ['./mechanic.component.scss']
})
export class MechanicComponent implements OnInit {
  mechanics: Mechanic[] = [];
  mechanicName = "";
  lastName = "";

  constructor(private mechanicService: MechanicService,
              private router: Router) { }

  ngOnInit() {
    this.getMechanics();
  }

  getMechanics(): void {
    this.mechanicService.getMechanics()
      .subscribe(mechanics => this.mechanics = mechanics);
  }

  prev(): void {
    this.router.navigate(['tasks']).then(() => window.location.reload());
  }

  add(): void {
    let id = Math.max.apply(Math, this.mechanics.map(function (o) {return o.id;}));

    this.mechanicService.addMechanic({id: id + 1, name: this.mechanicName, lastName: this.lastName} as Mechanic)
      .subscribe(mechanic => {this.mechanics.push(mechanic)});

    this.mechanicName = '';
    this.router.navigate(['tasks']).then(() => window.location.reload());
  }
}
