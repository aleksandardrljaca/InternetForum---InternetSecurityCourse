import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { VerifyLoginComponent } from './verify-login/verify-login.component';
import { HomeComponent } from './home/home.component';
import { CommentsComponent } from './comments/comments.component';
import { UserManagementComponent } from './user-management/user-management.component';
import { UserEditModalComponent } from './modals/user-edit-modal/user-edit-modal.component';
import { RegistrationComponent } from './registration/registration.component';
import { PendingCommentsComponent } from './pending-comments/pending-comments.component';
import { adminGuard } from './guards/admin-guard/admin.guard';
import { moderatorGuard } from './guards/moderator-guard/moderator.guard';
import { userGuard } from './guards/user-guard/user.guard';
import { verifyGuard } from './guards/verify-login/verify.guard';
import { Oauth2CallbackComponent } from './oauth2-callback/oauth2-callback.component';

const routes: Routes = [
  
  {
    path:"api/login",
    component:LoginComponent
  },
  {
    path:"api/login/verify",
    component:VerifyLoginComponent,
    canActivate:[verifyGuard]
  },
  {
    path:"api/register",
    component:RegistrationComponent
  },
  {
    path:"api/home",
    component:HomeComponent,
    canActivate:[userGuard]
  },
  {
    path:"api/categories/:name",
    component:CommentsComponent,
    canActivate:[userGuard]
  },
  {
    path:'api/management/users',
    component:UserManagementComponent,
    canActivate:[adminGuard]
  },
  {
    path:'api/management/pending-comments',
    component:PendingCommentsComponent,
    canActivate:[moderatorGuard]
  },
  {
    path:'api/oauth2-callback',
    component:Oauth2CallbackComponent
  },
  {
    path:"",
    redirectTo:"api/login",
    pathMatch:"full"
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
