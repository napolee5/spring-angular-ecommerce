import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RegisterRequest } from '../models/register-request.models';

@Injectable({ providedIn: 'root' })
export class AuthService {

  private baseUrl = 'http://localhost:9095';

  constructor(private http: HttpClient) {}

  // LOGIN
    login(email: string, password: string) {
    return this.http.post<{ token: string; role: string }>(
      `${this.baseUrl}/login`,
      { email, password }
    );
  }

  // SALVA TOKEN
  setToken(token: string): void {
    sessionStorage.setItem('jwt', token);
  }

  // RECUPERA TOKEN
  getToken(): string | null {
    return sessionStorage.getItem('jwt');
  }

  // LOGOUT
  logout(): void {
    sessionStorage.removeItem('jwt');
  }

  isLogged(): boolean{
     return sessionStorage.getItem('jwt') !== null;
  }

  register(data: RegisterRequest){
     return this.http.post<{ token: string; role: string }>(`${this.baseUrl}/register`, data)
  }

  forgotPassword(email: string): Observable<any> {
    return this.http.post(`${this.baseUrl}/forgot-password`, {
      email
    });
  }

  resetPassword(email: string, token: string, newPassword: string) {
  return this.http.post(this.baseUrl+'/reset-password', {
    email,
    token,
    newPassword
  });
}
}
