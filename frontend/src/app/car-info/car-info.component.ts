import { Component, OnInit } from '@angular/core';
import { Car } from '../model/car';
import { CarService } from '../service/car.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-car-info',
  templateUrl: './car-info.component.html',
  styleUrls: ['./car-info.component.scss']
})
export class CarInfoComponent implements OnInit {
  cars: Car[] = [];
  constructor(private carService: CarService,
              private router: Router) { }

  ngOnInit(): void {
    this.getCars();
  }

  getCars(): void {
    this.carService.getCars()
      .subscribe(cars => this.cars = cars);
  }

  update(carId: any): void {
    this.router.navigate(['/cars', carId]);
  }

}
