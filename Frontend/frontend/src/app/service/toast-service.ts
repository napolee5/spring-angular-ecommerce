import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class ToastService {

  toastVisible = false;
  toastMessage = '';
  private toastTimeout: any;

  showToast(message: string) {
    this.toastMessage = message;
    this.toastVisible = true;

    clearTimeout(this.toastTimeout);

    this.toastTimeout = setTimeout(() => {
      this.toastVisible = false;
    }, 2000);
  }

  
}
