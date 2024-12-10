import { NgModule } from '@angular/core';
import { BrowserModule, provideClientHydration } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, provideHttpClient } from '@angular/common/http';
import { MaterialModule } from './material/material/material.module';
import { VerifyLoginComponent } from './verify-login/verify-login.component';
import { HomeComponent } from './home/home.component';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { AuthInterceptorService } from './services/interceptors/auth-interceptor.service';
import { CommentsComponent } from './comments/comments.component';
import { CommentModalComponent } from './modals/comment-modal/comment-modal.component';
import { HeaderTemplateComponent } from './shared-comp/header-template/header-template.component';
import { UserManagementComponent } from './user-management/user-management.component';
import { UserEditModalComponent } from './modals/user-edit-modal/user-edit-modal.component';
import { RegistrationComponent } from './registration/registration.component';
import { PendingCommentsComponent } from './pending-comments/pending-comments.component';
import { Oauth2CallbackComponent } from './oauth2-callback/oauth2-callback.component';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    VerifyLoginComponent,
    HomeComponent,
    CommentsComponent,
    CommentModalComponent,
    HeaderTemplateComponent,
    UserManagementComponent,
    UserEditModalComponent,
    RegistrationComponent,
    PendingCommentsComponent,
    Oauth2CallbackComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    MaterialModule,
    FormsModule
   
    
  ],
  providers: [
    provideClientHydration(),
    provideAnimationsAsync(),
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptorService, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
