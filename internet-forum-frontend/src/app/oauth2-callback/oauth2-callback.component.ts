import { Component, OnInit } from '@angular/core';
import { LoginService } from '../services/login-service/login.service';
import { ActivatedRoute, Route, Router } from '@angular/router';

@Component({
  selector: 'app-oauth2-callback',
  templateUrl: './oauth2-callback.component.html',
  styleUrl: './oauth2-callback.component.css'
})
export class Oauth2CallbackComponent implements OnInit {
  constructor(private loginService:LoginService,private route:ActivatedRoute,private router:Router){}
  ngOnInit(): void {
      const code=this.route.snapshot.queryParamMap.get('code');
      const strCode:string=code+"";
      this.loginService.githubLogin(strCode).subscribe({
        next:(data:any)=>{
          sessionStorage.clear();
          sessionStorage.setItem("user",JSON.stringify(data));
          sessionStorage.setItem("token",data.token);
          this.loginService.reroute(data);
        },
        error:(err)=>{
          if(err.status==404){
            alert("You don't have an account! Please create one!");
            this.router.navigate(['']);
          }
            
        }
      })
      
  }

}
