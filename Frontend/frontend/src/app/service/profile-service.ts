import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { User } from '../models/user.models';

@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  private apiUrl=  'http://localhost:9095';

  constructor(private http: HttpClient) {}

  getMyCredential() : Observable<User>{
    return this.http.get<User>(this.apiUrl+ '/profile/myCredential');
  }
  
}
