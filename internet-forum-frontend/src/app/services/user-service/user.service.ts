import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Properties } from '../../properties/properties';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private httpClient:HttpClient) { 

  }
  getAll(){
    return this.httpClient.get<any>(Properties.API_BASE_URL+'/api/users');
  }
  insert(request:any){
    return this.httpClient.post<any>(Properties.API_BASE_URL+'/api/sign-up',request);
  }
  update(id:number,request:any){
    return this.httpClient.put<any>(Properties.API_BASE_URL+'/api/users/'+id+'/update',request);
  }
  restrictUser(id:number,request:any){
    return this.httpClient.put<any>(Properties.API_BASE_URL+'/api/users/'+id+'/restrict',request);
  }
  approveUser(id:number,request:any){
    return this.httpClient.put<any>(Properties.API_BASE_URL+'/api/users/'+id+'/verify',request);
  }
}
