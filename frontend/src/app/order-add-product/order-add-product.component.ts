import { Component, OnInit } from '@angular/core';
import {Order} from "../model/order";
import {Product} from "../model/product";
import {ActivatedRoute} from "@angular/router";
import {OrderService} from "../service/order.service";
import {Location} from "@angular/common";
import {ProductService} from "../service/product.service";
import {FormBuilder, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-order-add-product',
  templateUrl: './order-add-product.component.html',
  styleUrls: ['./order-add-product.component.scss']
})
export class OrderAddProductComponent implements OnInit {
  productForm!: FormGroup;

  order!: Order;
  products: Product[] = [];
  newProduct!: Product;

  constructor(
    private route: ActivatedRoute,
    private orderService: OrderService,
    private location: Location,
    private productService: ProductService,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.getOrder();
    this.getProducts();
    this.productForm = this.fb.group({
      product: [null]
    })
  }

  getProducts(): void {
    this.productService.getProducts()
      .subscribe(products => this.products = products);
  }


  getOrder(): void {
    const id = +this.route.snapshot.paramMap.get('id')!;
    this.orderService.getOrder(id)
      .subscribe(order => this.order = order);
  }

  goBack(): void {
    this.location.back();
  }

  submitProduct() {
    this.newProduct = this.products.find(p => p.id == this.productForm.value)!
  }

  save(): void {
    this.orderService.addProductToOrder(this.order.id!, this.newProduct)
      .subscribe(() => this.goBack());
  }
}
