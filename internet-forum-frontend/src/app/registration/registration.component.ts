import { Component } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { UserService } from '../services/user-service/user.service';
import { Router } from '@angular/router';
import { NotifyService } from '../services/notify-service/notify.service';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrl: './registration.component.css'
})
export class RegistrationComponent {
  constructor(
    private formBuilder:FormBuilder,
    private userService:UserService,
    private router:Router,
    private notify:NotifyService){}
  username = new FormControl('',Validators.required);
  password = new FormControl('',Validators.required);
  firstName= new FormControl('',Validators.required);
  lastName = new FormControl('',Validators.required);
  email = new FormControl('',Validators.required);
  registrationForm:FormGroup=this.formBuilder.
                                    group(
                                      {firstName:this.firstName,
                                        lastName:this.lastName,
                                        username:this.username,
                                        password:this.password,
                                        email:this.email
                                      });

  register(){
    this.userService.insert({
      username:this.username.value,
      password:this.password.value,
      firstName:this.firstName.value,
      lastName:this.lastName.value,
      email:this.email.value
    }).subscribe({
      next:(data)=>{
        console.log(data)
      },
      error:(err)=>{
        this.notify.openSnackBar(err.message,"close");
      }
    })
    this.router.navigate(['/api/login']);
  }
                                
}
