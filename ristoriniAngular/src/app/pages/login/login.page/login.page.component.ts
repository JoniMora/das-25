import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../../service/auth.service';

@Component({
  standalone: true,
  selector: 'app-login-page',
  imports: [CommonModule, FormsModule],
  template: `
    <div class="login-container">
      <h1>Ingresar</h1>
      <form (ngSubmit)="submit()" #loginForm="ngForm">
        <label>
          Email
          <input name="email" type="email" [(ngModel)]="email" required />
        </label>

        <label>
          Contraseña
          <input name="password" type="password" [(ngModel)]="password" required />
        </label>

        <button type="submit" [disabled]="loading">
          {{ loading ? 'Cargando...' : 'Entrar' }}
        </button>

        <div class="error" *ngIf="error">{{ error }}</div>
      </form>
    </div>
  `,
  styles: [`
    .login-container {
      max-width: 400px;
      margin: 2rem auto;
      padding: 2rem;
    }
    label {
      display: block;
      margin-bottom: 1rem;
    }
    input {
      width: 100%;
      padding: 0.5rem;
      margin-top: 0.25rem;
    }
    button {
      width: 100%;
      padding: 0.75rem;
    }
    .error {
      color: red;
      margin-top: 1rem;
    }
  `]
})
export class LoginPageComponent {
  email = '';
  password = '';
  error = '';
  loading = false;

  constructor(private auth: AuthService, private router: Router) {}

  submit() {
    this.loading = true;
    this.error = '';

    this.auth.login(this.email, this.password).subscribe({
      next: () => {
        this.loading = false;
        this.router.navigateByUrl('/');
      },
      error: (err) => {
        this.loading = false;
        this.error = 'Credenciales inválidas';
        console.error('Login error:', err);
      }
    });
  }
}
