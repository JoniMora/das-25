import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

// 👇 Ruta correcta (3 niveles): pages/login/login.page -> services
import { AuthService } from '../../../service/auth.service';

@Component({
  standalone: true,
  selector: 'app-login-page',
  imports: [CommonModule, FormsModule],
  template: `
    <h1>Ingresar</h1>
    <form (ngSubmit)="submit()">
      <label>Email
        <input name="email" type="email" [(ngModel)]="email" required />
      </label>
      <label>Contraseña
        <input name="password" type="password" [(ngModel)]="password" required />
      </label>
      <button type="submit">Entrar</button>
      <div class="error" *ngIf="error">{{ error }}</div>
    </form>
  `
})
export class LoginPageComponent {
  email = '';
  password = '';
  error = '';

  constructor(private auth: AuthService, private router: Router) {}

  submit() {
    this.auth.login(this.email, this.password).subscribe({
      next: () => this.router.navigateByUrl('/'),
      error: () => this.error = 'Credenciales inválidas'
    });
  }
}


