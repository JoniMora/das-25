import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink, RouterOutlet } from '@angular/router';
import { AuthService } from '../../service/auth.service';

@Component({
  standalone: true,
  selector: 'app-shell', 
  imports: [CommonModule, RouterLink, RouterOutlet],
  templateUrl: './app-shell.component.html',
  styleUrls: ['./app-shell.component.css']
})
export class AppShellComponent {
  private router = inject(Router);
  
  public auth = inject(AuthService); 
  
  constructor() {
    console.log('AppShellComponent cargado.');
  }

  logout(): void {
    this.auth.logout();
    this.router.navigateByUrl('/');
  }
}