import { Component } from '@angular/core';
import { LoginService } from '../services/login-service/login.service';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NotifyService } from '../services/notify-service/notify.service';

@Component({
  selector: 'app-verify-login',
  templateUrl: './verify-login.component.html',
  styleUrl: './verify-login.component.css'
})
export class VerifyLoginComponent {
  constructor(
    private loginService:LoginService,
    private formBuilder:FormBuilder,
    private router:Router,
    private notifyService:NotifyService

  ){}
  code=new FormControl('',Validators.required);
  verifyForm=this.formBuilder.group({code:this.code});
  
  secondStepLogin(){
      const val=sessionStorage.getItem("id");
      let id;
      if(val){
        id=JSON.parse(val);
        console.log(id);
        this.loginService.secondStepLogin({userId:id,code:this.code.value}).subscribe({
          next:(result:any)=>{
              sessionStorage.clear();
              // store token
              sessionStorage.setItem("token",result.token);
              sessionStorage.setItem("user",JSON.stringify(result));
              // refactor to navigate to adequate page for specific role
              this.loginService.reroute(result);
          },
          error:(err)=>{
              this.notifyService.openSnackBar("Wrong verification code!","Close");
          }
        });
      }
  }
  
}
