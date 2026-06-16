import { Component, OnInit } from '@angular/core';
import { CartService } from '../service/cart-service';
import { Order } from '../models/order.models';
import { Navbar } from "../navbar/navbar/navbar";
import { CommonModule } from '@angular/common';
import { Footer } from "../footer/footer";
import { OrderService } from '../service/order-service';
import { OrderStatus } from '../models/order.models';



@Component({
  selector: 'app-ordine',
  imports: [ CommonModule],
  templateUrl: './ordine.html',
  styleUrl: './ordine.css'
})
export class Ordine implements OnInit{

  listaordini: Order[]= [];
  errorMsg='';
  

  constructor(private cartservice: CartService, private orderservice: OrderService){}



  ngOnInit(): void {
    this.loadOrder()
  }

  loadOrder() {
      this.cartservice.getUserOrder().subscribe({
            next: (data: Order[]) => {
              this.listaordini= data
              console.log('ORDINI:', data);
            },
            error: err => {
                  console.log(err.error.message);
            }
          });

  }

  deleteOrder(order: Order): void {

  this.orderservice.deleteOrder(order).subscribe({
      next: () => {

        this.loadOrder();

      },

      error: err => {

        console.error(err);

        alert('Ordine non cancellabile');

      }

    });

}

canDelete(order: Order): boolean {

  if(order.orderStatus === OrderStatus.ORDINE_CONFERMATO) {
    return true;
  }
  return false;
}

}