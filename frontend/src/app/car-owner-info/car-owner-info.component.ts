import { Component, OnInit } from '@angular/core';
import { CarOwner } from '../model/carOwner';
import { CarOwnerService } from '../service/carOwner.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-car-owner-info',
  templateUrl: './car-owner-info.component.html',
  styleUrls: ['./car-owner-info.component.scss']
})
export class CarOwnerInfoComponent implements OnInit {
  owners: CarOwner[] = [];

  constructor(private ownerService: CarOwnerService,
              private router: Router) { }

  ngOnInit(): void {
    this.getCarOwners();
  }
  getCarOwners(): void {
    this.ownerService.getCarOwners()
      .subscribe(owners => this.owners = owners);
  }
  update(ownerId: any): void {
    this.router.navigate(['/car-owners', ownerId]);
  }
  getOrdersOfOwner(ownerId: any): void {
    this.router.navigate(['/car-owners/orders', ownerId]);
  }
}
