import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class TokenService {

  constructor() { }
  getToken():string|null{
    return sessionStorage.getItem("token");
  }
  getUser():any{
    const data=sessionStorage.getItem('user');
    if(data)
      return JSON.parse(data);
    return null;
  }
  checkUserCreatePermission():boolean{
    const user=this.getUser();
    return Boolean(user.permissions.some((p:any)=>p.type==='create' && p.userByUserIdId==user.id));
  }
  checkUserEditPermission():boolean{
    const user=this.getUser();
    return Boolean(user.permissions.some((p:any)=>p.type==='update' && p.userByUserIdId==user.id));
  }
  checkUserDeletePermission():boolean{
    const user=this.getUser();
    return Boolean(user.permissions.some((p:any)=>p.type==='delete' && p.userByUserIdId==user.id));
  }
  credentialsInserted():boolean{
    return sessionStorage.getItem("id")!=null;
  }

  
}
