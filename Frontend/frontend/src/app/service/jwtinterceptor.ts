import { Injectable } from '@angular/core';
import {
  HttpInterceptor,
  HttpRequest,
  HttpHandler,
  HttpEvent
} from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token = sessionStorage.getItem('jwt');  // lo stesso che salvi al login
    console.log('JWT INTERCEPTOR: token letto =', token, 'per URL', req.url);


    if (token) {
      const cloned = req.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`
        }
      });
      console.log('JWT INTERCEPTOR: aggiunto Authorization per URL', cloned.url);
      return next.handle(cloned);
    }

    console.log('JWT INTERCEPTOR: nessun token, request invariata');
    return next.handle(req);
  }
}
