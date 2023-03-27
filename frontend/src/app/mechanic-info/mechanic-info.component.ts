import { Component, OnInit } from '@angular/core';
import { Mechanic } from '../model/mechanic';
import { MechanicService } from '../service/mechanic.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-mechanic-info',
  templateUrl: './mechanic-info.component.html',
  styleUrls: ['./mechanic-info.component.scss']
})
export class MechanicInfoComponent implements OnInit {
  mechanics: Mechanic[] = []
  constructor(private mechanicService: MechanicService,
              private router: Router) { }

  ngOnInit(): void {
    this.getMechanics();
  }
  getMechanics(): void {
    this.mechanicService.getMechanics()
      .subscribe(mechanics => this.mechanics = mechanics);
  }
  update(mechanicId: any): void {
    this.router.navigate(['/mechanics', mechanicId]);
  }

  getSalary(mechanicId: any): void {
    this.router.navigate(['/mechanics/salary', mechanicId]);
  }

  getFinishedOrders(mechanicId: any): void {
    this.router.navigate(['/mechanics', mechanicId,'finished-orders']);
  }

}
