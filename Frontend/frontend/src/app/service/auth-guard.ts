import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { AuthService } from './auth.service';

@Injectable({ providedIn: 'root' })
export class AuthGuard implements CanActivate {
  constructor(private authService: AuthService, private router: Router) {}

  canActivate(): boolean {
  const token = this.authService.getToken();
  console.log("TOKEN RICEVUTO DAL GUARD:", token);

  if (token) {
    console.log("➡️ Accesso consentito");
    return true;
  }

  console.log("⛔ Nessun token. Redirect a login.");
  this.router.navigate(['/login']);
  return false;
}


}
