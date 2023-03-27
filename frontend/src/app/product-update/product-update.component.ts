import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { ProductService } from '../service/product.service';

@Component({
  selector: 'app-product-update',
  templateUrl: './product-update.component.html',
  styleUrls: ['./product-update.component.scss']
})

export class ProductUpdateComponent implements OnInit {
  product: any;
  title = '';
  price = 0;

  constructor(
    private route: ActivatedRoute,
    private productService: ProductService,
    private location: Location
  ) {
  }

  ngOnInit(): void {
    this.getProduct();
  }

  getProduct(): void {
    const id = +this.route.snapshot.paramMap.get('id')!;
    this.productService.getProduct(id)
      .subscribe(product => {
        this.product = product;
        this.title = product.title;
        this.price = product.price;
      });
  }

  goBack(): void {
    this.location.back();
  }

  save(): void {
    this.product = {
      id: this.product.id,
      title: this.title != '' ? this.title : this.product.title,
      price: this.price != 0 ? this.price : this.product.price
    };
    this.productService.updateProduct(this.product)
      .subscribe(() => this.goBack());
  }
}

