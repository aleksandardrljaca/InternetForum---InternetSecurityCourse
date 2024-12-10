import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Properties } from '../properties/properties';
import { LoginService } from '../services/login-service/login.service';
import { NotifyService } from '../services/notify-service/notify.service';
import { FormControl, Validators, FormBuilder, FormGroup } from '@angular/forms';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent{
    constructor(
      private loginService:LoginService,
      private notifyService:NotifyService,
      private formBuilder:FormBuilder,
      private router:Router
    ){}
    private readonly key:string="id"
    formValid:boolean=false;
    username = new FormControl('',Validators.required);
    password = new FormControl('',Validators.required);
    loginForm:FormGroup=this.formBuilder.group({username:this.username,password:this.password});
    firstStageLogin():void{
      this.loginService.loginFirstStep({username:this.username.value,password:this.password.value}).subscribe({
        next:(result:number)=>{
            sessionStorage.setItem(this.key,JSON.stringify(result));
            this.router.navigate(["api/login/verify"]);
        },
        error:(err)=>{
            if(err.status==403)
              this.notifyService.openSnackBar("Wrong credentials!","Close");
            else if(err.status==401)
              this.notifyService.openSnackBar("Access forbidden!","Close");
        }
      });
      
    }
    githubLogin(){
      window.location.href =Properties.GITHUB_OAUTH_URL;
    }
   
    
}
