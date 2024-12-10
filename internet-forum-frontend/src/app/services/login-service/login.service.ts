import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { observableToBeFn } from 'rxjs/internal/testing/TestScheduler';
import { Properties } from '../../properties/properties';

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  private readonly loginUrl:string=Properties.API_BASE_URL+'/api/login';
  private readonly verifyUrl:string=Properties.API_BASE_URL+'/api/login/verify';
  private readonly githubLoginUrl:string=Properties.API_BASE_URL+'/api/github-login';
  constructor(private httpClient:HttpClient,private router:Router) { }

  loginFirstStep(request:any){
      return this.httpClient.post<number>(this.loginUrl,request);
  }
  secondStepLogin(request:any){
    return this.httpClient.post<any>(this.verifyUrl,request);
  }
  githubLogin(code:string){
   return this.httpClient.get<any>(this.githubLoginUrl+'/'+code);
  }
  
  logout(){
    sessionStorage.clear();
    this.router.navigate(['/api/login']);
  }
  reroute(data:any):void{
    switch(data.role){
      case 'ADMIN':
        this.router.navigate(['api/management/users']);
        break;
      case 'USER':
        this.router.navigate(['api/home']);
        break;
      case 'MODERATOR':
        this.router.navigate(['/api/management/pending-comments']);
        break;
      default:
        break;
    }
  }
}
