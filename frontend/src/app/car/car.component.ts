import { Component, OnInit } from '@angular/core';
import {Car} from "../model/car";
import {CarService} from "../service/car.service";
import {CarOwner} from "../model/carOwner";
import {CarOwnerService} from "../service/carOwner.service";
import {FormBuilder, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-car',
  templateUrl: './car.component.html',
  styleUrls: ['./car.component.scss']
})
export class CarComponent implements OnInit {
  contactForm!: FormGroup;

  cars: Car[] = [];
  owners: CarOwner[] = [];
  owner!: CarOwner;
  carBrand = "";
  carModel = "";
  carYear = "";
  carNumber = "";


  constructor(private carService: CarService,
              private ownerService: CarOwnerService,
              private fb:FormBuilder) { }

  ngOnInit() {
    this.getCars();
    this.getOwners();
    this.contactForm = this.fb.group({
      owner: [null]
    })
  }

  getOwners(): void {
    this.ownerService.getCarOwners()
      .subscribe(owners => this.owners = owners);
  }

  getCars(): void {
    this.carService.getCars()
      .subscribe(cars => this.cars = cars);
  }

  submit() {
    this.owner = this.owners.find(o => o.id == this.contactForm.value)!
  }

  add(): void {
    let id = Math.max.apply(Math, this.cars.map(function (o) {return o.id;}));

    this.carService.addCar({id: id + 1, brand: this.carBrand, model: this.carModel, year: this.carYear,
      number: this.carNumber, carOwner: this.owner} as Car).subscribe(car => {this.cars.push(car)});

    this.carBrand = "";
    this.carModel = "";
    this.carYear = "";
    this.carNumber = "";
  }
}
