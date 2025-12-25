import { inject } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivateFn,
  Router,
  RouterStateSnapshot,
} from '@angular/router';
import { catchError, map, of } from 'rxjs';
import { AuthService } from '../services/auth.service';

export const authGuard: CanActivateFn = (
  route: ActivatedRouteSnapshot,
  state: RouterStateSnapshot
) => {
  const router = inject(Router);
  const authService = inject(AuthService);

  if (authService.currentUser()) {
    return true;
  }

  return authService.checkAuthStatus().pipe(
    map(() => true), // Si responde 200, usuario autenticado
    catchError(() => {
      router.navigate(['auth/login']); // Si responde 401, al login
      return of(false);
    })
  );
};