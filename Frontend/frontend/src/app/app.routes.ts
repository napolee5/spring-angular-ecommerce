import { Routes } from '@angular/router';
import { AuthGuard } from './service/auth-guard';
import { LoginComponent } from './login/login';
import { Home } from './home/home';
import { Carrello } from './carrello/carrello';
import { Profilo } from './profilo/profilo';
import { Ordine } from './ordine/ordine';



export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'home', component: Home},
  { path: 'profile/myCart', component: Carrello, canActivate: [AuthGuard]},
  { path: 'profile/myOrder', component: Ordine, canActivate: [AuthGuard]},
  { path: 'profile/myCredential', component: Profilo, canActivate: [AuthGuard]},
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: '**', redirectTo: '/home' } // fallback
];

