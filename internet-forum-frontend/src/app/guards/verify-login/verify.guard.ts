import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { TokenService } from '../../services/token-service/token.service';

export const verifyGuard: CanActivateFn = (route, state) => {
  const router=inject(Router);
  const tokenService=inject(TokenService);
  if(tokenService.credentialsInserted())
    return true;
  router.navigate(['']);
  return false;
};
