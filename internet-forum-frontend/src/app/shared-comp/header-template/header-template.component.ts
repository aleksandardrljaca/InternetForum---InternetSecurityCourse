import { Component, OnInit } from '@angular/core';
import { TokenService } from '../../services/token-service/token.service';
import { LoginService } from '../../services/login-service/login.service';
import { ActivatedRoute, Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { CommentModalComponent } from '../../modals/comment-modal/comment-modal.component';

@Component({
  selector: 'app-header-template',
  templateUrl: './header-template.component.html',
  styleUrl: './header-template.component.css'
})
export class HeaderTemplateComponent implements OnInit {
  constructor(private tokenService:TokenService,private loginService:LoginService,private router:Router,private dialog:MatDialog){}
  public a:boolean=false;
  public m:boolean=false;

    ngOnInit(): void {
        this.checkRole();
    }
    checkRole(){
      const usr=sessionStorage.getItem('user');
      if(usr){
        const data=JSON.parse(usr);
        switch(data.role){
          case 'ADMIN':
            this.a=true;
            break;
          case 'MODERATOR':
            this.m=true;
            break;
          
          default:
            break;
        }
      }
    }
    onForumClick(){
      this.router.navigate(['/api/home']);
      
    }
    onUsersClick(){
      this.router.navigate(['/api/management/users']);
    }
    onPendingClick(){
      this.router.navigate(['/api/management/pending-comments']);
    }
    logout(){
      this.loginService.logout();
    }
    
    
  
}
