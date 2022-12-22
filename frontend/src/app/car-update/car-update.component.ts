import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {Location} from "@angular/common";
import {Car} from "../model/car";
import {CarService} from "../service/car.service";
import {CarOwnerService} from "../service/carOwner.service";
import {CarOwner} from "../model/carOwner";

@Component({
  selector: 'app-car-update',
  templateUrl: './car-update.component.html',
  styleUrls: ['./car-update.component.scss']
})
export class CarUpdateComponent implements OnInit {

  car!: Car;
  owners: CarOwner[] = [];

  constructor(
    private route: ActivatedRoute,
    private carService: CarService,
    private location: Location,
    private ownerService: CarOwnerService
  ) {}

  ngOnInit(): void {
    this.getCar();
    this.getOwners();
  }

  getOwners(): void {
    this.ownerService.getCarOwners()
      .subscribe(owners =>  this.owners = owners);
  }

  getCar(): void {
    const id = +this.route.snapshot.paramMap.get('id')!;
    this.carService.getCar(id)
      .subscribe(car => this.car = car);
  }

  goBack(): void {
    this.location.back();
  }

  save(): void {
    this.carService.updateCar(this.car)
      .subscribe(() => this.goBack());
  }
}
