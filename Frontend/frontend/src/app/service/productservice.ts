import { Injectable } from '@angular/core';
import { ProdCategory, Product, SubCategory } from '../models/product.model';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class Productservice {
   private apiUrl = 'http://localhost:9095/products';

  constructor(private http: HttpClient) {}

  getProducts(): Observable<Product[]> {
    return this.http.get<Product[]>(this.apiUrl);
  }


  searchByCategory(category: ProdCategory, subCategory?: SubCategory): Observable<Product[]> {
  let params = new HttpParams().set('category', category);

  if (subCategory) {
     params = params.set('subCategory', subCategory);
  }

  return this.http.get<Product[]>(this.apiUrl+ '/search/bycategory', { params });
}

  searchByName(nome: string): Observable<Product[]>{
    return this.http.get<Product[]>(this.apiUrl + '/search/byname', { params: { name: nome } });
  }

}
