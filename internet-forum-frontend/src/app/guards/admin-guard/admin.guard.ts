import { inject} from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { TokenService } from '../../services/token-service/token.service';

export const adminGuard: CanActivateFn = (route, state) => {
  const router=inject(Router);
  const tokenService=inject(TokenService);
  const user=tokenService.getUser();
  if(user.role==="ADMIN")
    return true;
  router.navigate(['']);
  return false;
};
