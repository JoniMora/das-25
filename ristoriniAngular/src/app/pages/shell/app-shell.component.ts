import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink, RouterOutlet } from '@angular/router'; // ¡Importación necesaria!
import { AuthService } from '../../service/auth.service';

@Component({
  standalone: true,
  selector: 'app-root',
  imports: [CommonModule, RouterLink, RouterOutlet],
  templateUrl: './app-shell.component.html',
})
export class AppShellComponent {
  // ✅ Usamos 'inject' para el Router (más moderno)
  private router = inject(Router);
  public auth = inject(AuthService);
  // ✅ Usamos inyección tradicional SÓLO para AuthService si queremos que sea pública en el template
  // Opcionalmente, puedes usar 'inject' también para auth: public auth = inject(AuthService);
  constructor() {
    console.log();
  }

  logout(): void {
    this.auth.logout();
    this.router.navigateByUrl('/');
  }
}

