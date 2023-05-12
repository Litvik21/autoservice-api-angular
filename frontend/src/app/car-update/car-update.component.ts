import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { Car } from '../model/car';
import { CarService } from '../service/car.service';
import { CarOwnerService } from '../service/carOwner.service';
import { CarOwner } from '../model/carOwner';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-car-update',
  templateUrl: './car-update.component.html',
  styleUrls: ['./car-update.component.scss']
})
export class CarUpdateComponent implements OnInit {

  contactForm = new FormGroup({
    owner: new FormControl()
  });

  cars: Car[] = [];
  owners: CarOwner[] = [];
  owner: CarOwner | undefined;
  carBrand = '';
  carModel = '';
  carYear = '';
  carNumber = '';
  car: any;

  constructor(
    private route: ActivatedRoute,
    private carService: CarService,
    private location: Location,
    private ownerService: CarOwnerService,
    private fb: FormBuilder
  ) {
  }

  ngOnInit(): void {
    this.getCar();
    this.getOwners();
    this.contactForm = this.fb.group({
      owner: [null]
    });
  }

  getOwners(): void {
    this.ownerService.getCarOwners()
      .subscribe(owners => this.owners = owners);
  }

  getCar(): void {
    const id = +this.route.snapshot.paramMap.get('id')!;
    this.carService.getCar(id)
      .subscribe(car => {
        this.car = car;
        this.carModel = car.model;
        this.carYear = car.year;
        this.carBrand = car.brand;
        this.carNumber = car.number;
        this.contactForm = this.fb.group({
          owner: [this.car.ownerId]
        });
      });
  }

  submit() {
    if (this.contactForm.valid) {
      const ownerId = this.contactForm.get('owner')!.value;
      if (ownerId !== null) {
        this.owner = this.owners.find(o => o.id === ownerId)!;
      }
    }
  }

  goBack(): void {
    this.location.back();
  }

  save(): void {
    this.submit();

    this.car = {
      id: this.car.id,
      brand: this.carBrand != '' ? this.carBrand : this.car.brand,
      model: this.carModel != '' ? this.carModel : this.car.model,
      year: this.carYear != '' ? this.carYear : this.car.year,
      number: this.carNumber != '' ? this.carNumber : this.car.number,
      ownerId: this.owner?.id ?? this.car.ownerId
    };
    this.carService.updateCar(this.car)
      .subscribe(() => this.goBack());
  }
}
