import { Component } from '@angular/core';
import { AuthService } from '../../service/auth.service';
import { Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';



@Component({
  selector: 'app-navbar',
  imports: [RouterLink, CommonModule],
  templateUrl: './navbar.html',
  styleUrl: './navbar.css'
})
export class Navbar {

  constructor(private authservice: AuthService, private router: Router){}

  logout(): void{
    this.authservice.logout();
    this.router.navigate(['home']);
  }

  isLogged(): boolean{
    return this.authservice.isLogged();
  }

}
