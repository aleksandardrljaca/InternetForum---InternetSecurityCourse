import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Properties } from '../../properties/properties';
@Injectable({
  providedIn: 'root'
})
export class PermissionService {

  constructor(private httpClient:HttpClient) { }
  
  insert(request:any){
    return this.httpClient.post<any>(Properties.API_BASE_URL+'/api/permissions',request);
  }

  deleteUsersPermissions(userId:number){
    return this.httpClient.delete<any>(Properties.API_BASE_URL+'/api/permissions/user/'+userId);
  }
}
