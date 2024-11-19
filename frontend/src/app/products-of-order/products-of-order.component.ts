import { Component, OnInit } from '@angular/core';
import { Product } from '../model/product';
import { ActivatedRoute } from '@angular/router';
import { ProductService } from '../service/product.service';
import { OrderService } from '../service/order.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-products-of-order',
  templateUrl: './products-of-order.component.html',
  styleUrls: ['./products-of-order.component.scss']
})
export class ProductsOfOrderComponent implements OnInit {
  orderId: any;
  products: Product[] = []
  allProducts: Product[] = []

    constructor(private productService: ProductService,
                private orderService: OrderService,
                private route: ActivatedRoute,
                private router: Router) { }

    ngOnInit(): void {
      this.getProducts();
    }

    getProducts(): void {
      const id = +this.route.snapshot.paramMap.get('id')!;
      this.orderId = id;
      this.orderService.getProducts(this.orderId)
              .subscribe(products => this.products = products);
    }

    getAllProducts(): void {
      this.productService.getProducts()
              .subscribe(products => this.allProducts = products);
    }

    delete(productId: any): void {
      this.orderService.removeProductFromOrder(this.orderId, productId)
              .subscribe(o => {
                    console.log(o);
                    window.location.reload()
                })
    }

    addProduct(): void {
      this.router.navigate(['/orders/add-product', this.orderId]);
    }

    prev(): void {
      this.router.navigate(['orders-info']).then(() => window.location.reload());
    }

}
