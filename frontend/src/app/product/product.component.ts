import { Component, OnInit } from '@angular/core';
import { Product } from '../model/product';
import { ProductService } from '../service/product.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.scss']
})
export class ProductComponent implements OnInit {

  products: Product[] = [];
  title = '';
  price = 0;

  constructor(private productService: ProductService,
              private router: Router) { }

  ngOnInit() {
    this.getProducts();
  }

  getProducts(): void {
    this.productService.getProducts()
      .subscribe(products => this.products = products);
  }

  prev(): void {
    this.router.navigate(['orders']).then(() => window.location.reload());
  }

  add(): void {
    let id = Math.max.apply(Math, this.products.map(function (o) {return o.id;}));

    this.productService.addProduct({id: id + 1, title: this.title, price: this.price} as Product)
      .subscribe(product => {this.products.push(product)});

    this.title = "";
    this.price = 0;
    this.router.navigate(['orders']).then(() => window.location.reload());
  }
}
