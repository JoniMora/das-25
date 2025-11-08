// src/main.ts (Código CORREGIDO)

import { bootstrapApplication } from '@angular/platform-browser';
import { appConfig } from './app/app.config';
// ❌ ELIMINAR: import { AppComponent } from './app/app.component';

// ✅ Importa tu componente shell
import { AppShellComponent } from './app/pages/shell/app-shell.component';

// ✅ Inicializa AppShellComponent como componente raíz
bootstrapApplication(AppShellComponent, appConfig)
  .catch((err) => console.error(err));
