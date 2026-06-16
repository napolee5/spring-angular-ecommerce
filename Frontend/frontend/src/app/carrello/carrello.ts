import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CartService } from '../service/cart-service';
import { Cart } from '../models/cart.models';
import { FormsModule } from "@angular/forms";
import { OrderItem } from '../models/order-item.model';
import { CommonModule } from '@angular/common';
import { Navbar } from "../navbar/navbar/navbar";
import { Product } from '../models/product.model';
import { OrderRequest } from '../models/order-request.models';
import { Footer } from "../footer/footer";
import { HttpClient } from '@angular/common/http';
import { ToastService } from '../service/toast-service';

@Component({
  selector: 'app-carrello',
  imports: [FormsModule, CommonModule],
  templateUrl: './carrello.html',
  styleUrl: './carrello.css'
})
export class Carrello implements OnInit {

  constructor(private cartservice: CartService, private router: Router,
     private http: HttpClient, public toastservice: ToastService) {}

  data: any = {};
  errorMsg='';

  regions: string[] = [];
  provinces: string[] = [];
  cities: string[] = [];

  carrello?: Cart;                 
  listaProdotti: OrderItem[] = []; 

  region='';
  city='';
  province='';
  address='';


  showForm = false;
  showCheckout=false;

  ngOnInit(): void {

  this.loadCartProducts();

  this.http
    .get<any>('assets/comuni.json')
    .subscribe(json => {

      this.data = json

      this.regions = Object.keys(this.data)

    })

}

  onRegionChange(): void {

  this.province = ''
  this.city = ''

  this.cities = []

  this.provinces = Object.keys(this.data[this.region]);

}

onProvinceChange(): void {

  this.city = ''

  this.cities =
    this.data[this.region][this.province]

}

  ordercontrol(){
    if(!this.region || !this.city || !this.province || !this.address){
      return false;
    }
    return true;
  }

  loadCartProducts() {
    this.cartservice.getUserCart().subscribe({
      next: (data: Cart) => {
        this.carrello = data;                 
        this.listaProdotti = data.items;
      },
      error: err => {
        this.errorMsg = err.error.message;
        this.toastservice.showToast(this.errorMsg);
      }
    });
  }

  deleteProduct(productID: number): void{
    this.cartservice.deleteProduct(productID).subscribe({
      next: newCart => {
        this.carrello = newCart;
        this.listaProdotti= newCart.items;
       console.log('Carrello è:', newCart);
      },
      error: err => {
        this.errorMsg = err.error.message;
        this.toastservice.showToast(this.errorMsg);
      }
    })
  }

  editProduct(product: Product, quantity:number): void{
    const orderItem: OrderItem = {
      product : product,
      quantity: quantity,
      price: product.price*quantity
      };
    this.cartservice.editProduct(orderItem).subscribe(
      newcart => {
        this.carrello=newcart;
        this.listaProdotti=newcart.items;
        console.log(newcart)
        }
      )
  }


  checkoutAndPay() {
    if(this.ordercontrol()){
      const orderReq: OrderRequest = {
        region: this.region,
        city: this.city,
        province: this.province,
        address: this.address,
    };

    this.cartservice.checkout(orderReq).subscribe({
      next: (response) => {
        console.log('checokt ok', response);
        this.router.navigateByUrl('/profile/myOrder');
      },
      error: (err) => {
        console.error(err);
        this.toastservice.showToast("Pagamento Fallito");
      }
    });
  }
}


}

