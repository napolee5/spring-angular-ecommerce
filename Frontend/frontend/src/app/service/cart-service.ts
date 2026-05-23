import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Product } from '../models/product.model';
import { Observable } from 'rxjs';
import { OrderItem } from '../models/order-item.model';
import { Cart } from '../models/cart.models';
import { OrderRequest } from '../models/order-request.models';
import { Order } from '../models/order.models';


@Injectable({
  providedIn: 'root'
})
export class CartService {

  private apiUrl = 'http://localhost:9095';

  constructor(private http: HttpClient) {}
  
  add(orderItem: OrderItem): Observable<HttpResponse<any>> {
    return this.http.post<any>(this.apiUrl + '/cart/add', orderItem, { observe: 'response' });
  }

  getUserCart(): Observable<Cart>{
    return this.http.get<Cart>(this.apiUrl + '/profile/myCart');
  }

  getUserOrder(): Observable<Order[]>{
    return this.http.get<Order[]>(this.apiUrl + '/profile/myOrder');
  }

  deleteProduct(productID: number): Observable<Cart>{
    return this.http.delete<Cart>(this.apiUrl + '/cart/deleteProd', {params: { ProdID: productID }});
  }

  editProduct(orderItem: OrderItem): Observable<Cart>{
    return this.http.put<Cart>(this.apiUrl + '/cart/edit', orderItem);
  }

  checkout(orderRequest: OrderRequest): Observable<any>{
    return this.http.post<any>(this.apiUrl + '/checkout/pay', orderRequest);
  }
}
