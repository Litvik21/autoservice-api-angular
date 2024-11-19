import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.scss']
})
export class MainComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit(): void {
  }

  addNewClient() {
    this.router.navigate(['car-owners']).then(() => window.location.reload());
  }

  addNewOrder() {
    this.router.navigate(['orders']).then(() => window.location.reload());
  }
}
