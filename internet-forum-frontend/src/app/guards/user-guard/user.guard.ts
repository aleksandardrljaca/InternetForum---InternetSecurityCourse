import { CanActivateFn, Router } from '@angular/router';
import { TokenService } from '../../services/token-service/token.service';
import { inject } from '@angular/core';

export const userGuard: CanActivateFn = (route, state) => {
  const router=inject(Router);
  const tokenService=inject(TokenService);
  const user=tokenService.getUser();
  if(user.role==="USER" || user.role==="ADMIN" || user.role==="MODERATOR")
    return true;
  router.navigate(['']);
  return false;
};
