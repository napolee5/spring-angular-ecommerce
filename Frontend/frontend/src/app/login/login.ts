import { Component } from '@angular/core';
import { AuthService } from '../service/auth.service';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { RegisterRequest } from '../models/register-request.models';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class LoginComponent {

  // 🔵 LOGIN
  loginData = {
    email: '',
    password: ''
  };

  // 🟢 REGISTER
  registerData = {
    firstName: '',
    lastName: '',
    email: '',
    password: ''
  };

  // 🟡 FORGOT PASSWORD
  forgotData = {
    email: '',
    token: '',
    newPassword: ''
  };

  // UI STATE
  doReg: boolean = false;
  forgotMode: boolean = false;
  step: number = 1;

  // MESSAGES
  errorMsg: string = '';
  successMsg: string = '';

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  // ======================
  // LOGIN
  // ======================
  login() {

    this.errorMsg = '';

    this.authService.login(
      this.loginData.email,
      this.loginData.password
    ).subscribe({

      next: (res) => {

        sessionStorage.setItem('jwt', res.token);

        this.router.navigate(['/home']);
      },

      error: (err) => {

        this.errorMsg = err.error.message;
      }
    });
  }

  // ======================
  // REGISTER UI
  // ======================
  doRegister() {

    this.doReg = true;
    this.forgotMode = false;

    this.errorMsg = '';
    this.successMsg = '';
  }

  // ======================
  // REGISTER
  // ======================
  registerControl(): boolean {

    return !!(
      this.registerData.firstName &&
      this.registerData.lastName &&
      this.registerData.email &&
      this.registerData.password
    );
  }

  register() {

    if (!this.registerControl()) {

      this.errorMsg = 'Compila tutti i campi';

      return;
    }

    const reg: RegisterRequest = {
      firstName: this.registerData.firstName,
      lastName: this.registerData.lastName,
      email: this.registerData.email,
      password: this.registerData.password
    };

    this.authService.register(reg).subscribe({

      next: (res) => {

        sessionStorage.setItem('jwt', res.token);

        this.router.navigate(['/home']);
      },

      error: (err) => {

        this.errorMsg = err.error.message;
        ;
      }
    });
  }

  // ======================
  // FORGOT PASSWORD UI
  // ======================
  goToForgot() {

    this.forgotMode = true;
    this.doReg = false;

    this.errorMsg = '';
    this.successMsg = '';

    this.step = 1;
  }

  // ======================
  // STEP 1 → SEND EMAIL
  // ======================
  sendEmail() {

    if (!this.forgotData.email) {

      this.errorMsg = 'Inserisci email';

      return;
    }

    this.authService.forgotPassword(
      this.forgotData.email
    ).subscribe({

      next: () => {

        this.step = 2;

        this.errorMsg = '';
      },

      error: () => {

        this.errorMsg = 'Errore invio email';
      }
    });
  }

  // ======================
  // STEP 2 → RESET PASSWORD
  // ======================
  resetPassword() {

    if (
      !this.forgotData.token ||
      !this.forgotData.newPassword
    ) {

      this.errorMsg = 'Compila tutti i campi';

      return;
    }

    this.authService.resetPassword(
      this.forgotData.email,
      this.forgotData.token,
      this.forgotData.newPassword
    ).subscribe({

      next: () => {

        // ✅ success message
        this.successMsg = 'Password aggiornata correttamente';

        // ✅ reset UI
        this.forgotMode = false;
        this.step = 1;
        this.doReg = false;

        // ✅ clear forgot form
        this.forgotData = {
          email: '',
          token: '',
          newPassword: ''
        };

        this.errorMsg = '';

        // ✅ remove message after 3 sec
        setTimeout(() => {

          this.successMsg = '';

        }, 3000);
      },

      error: (err) => {

        this.errorMsg = err.error.message;
        ;
      }
    });
  }
}