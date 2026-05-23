import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ProductRequest } from '../models/product-request.models';
import { Product } from '../models/product.model';
import { Order } from '../models/order.models';
import { User } from '../models/user.models';

@Injectable({
  providedIn: 'root',
})
export class AdminService {
  
  
  private baseUrl = 'http://localhost:9095/admin';

  constructor(private http: HttpClient) {}

  createProduct(product: ProductRequest): Observable<Product>{
    return this.http.post<Product>(this.baseUrl+'/create', product);
  }

  deleteProduct(product: Product): Observable<void> {
  return this.http.delete<void>(
    `${this.baseUrl}/delete`,
    { body: product }
  );
}

  getAllOrder(): Observable<any>{
    return this.http.get<any>(this.baseUrl+'/allOrder');
  }

  getAllUsers(): Observable<User[]>{
    return this.http.get<User[]>(this.baseUrl+'/allUsers');
  }
}
