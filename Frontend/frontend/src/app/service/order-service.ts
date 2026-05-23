import { Injectable } from '@angular/core';
import { Order } from '../models/order.models';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class OrderService {
    private apiUrl = 'http://localhost:9095';
  
    constructor(private http: HttpClient) {}

    deleteOrder(order: Order): Observable<Order>{
      return this.http.delete<Order>(this.apiUrl + '/order/deleteOrder', {body: order});
    }
  
}
