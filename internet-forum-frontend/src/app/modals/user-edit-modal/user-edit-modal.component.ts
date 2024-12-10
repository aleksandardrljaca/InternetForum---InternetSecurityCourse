import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { NotifyService } from '../../services/notify-service/notify.service';
import { UserService } from '../../services/user-service/user.service';
import { TokenService } from '../../services/token-service/token.service';
import { PermissionService } from '../../services/permission-service/permission.service';

@Component({
  selector: 'app-user-edit-modal',
  templateUrl: './user-edit-modal.component.html',
  styleUrl: './user-edit-modal.component.css'
})
export class UserEditModalComponent{
  user:any;
  selectedItem:string=""
  hasDeletePermission:boolean=false;
  hasUpdatePermission:boolean=false;
  hasCreatePermission:boolean=false;
  permissions:any=[];
  constructor(
    @Inject(MAT_DIALOG_DATA) public data:any,
    public dialogRef: MatDialogRef<UserEditModalComponent>,
    private userService:UserService,
    private notify:NotifyService,
    private permissionService:PermissionService){
      this.user=data;
      this.selectedItem=this.user.role;
      this.hasCreatePermission=this.user.permissions.some((p:any)=>p.type==='create');
      this.hasUpdatePermission=this.user.permissions.some((p:any)=>p.type==='update');
      this.hasDeletePermission=this.user.permissions.some((p:any)=>p.type==='delete');
  }
  
  onCreateCheck(){
   this.hasCreatePermission=!this.hasCreatePermission;
  }
  onUpdateCheck(){
    this.hasUpdatePermission=!this.hasUpdatePermission;
  }
  onDeleteCheck(){
    this.hasDeletePermission=!this.hasDeletePermission;
  }
  onItemSelected(){
    this.user.role=this.selectedItem;
  }
  onSave(){
    
    this.permissionService.deleteUsersPermissions(this.user.id).subscribe({
      error:(err)=>{
        this.notify.openSnackBar("GRESKA PRI BRISANJU","close");
      }
    });
    
    if(this.hasCreatePermission)
      this.permissionService.insert({type:'create',userByUserIdId:this.user.id})
          .subscribe({
            error:(err)=>{
              console.log(err);
            }
          });
    if(this.hasUpdatePermission)
      this.permissionService.insert({type:'update',userByUserIdId:this.user.id}).subscribe({
        error:(err)=>{
          console.log(err);
        }
      });
    if(this.hasDeletePermission)
      this.permissionService.insert({type:'delete',userByUserIdId:this.user.id}).subscribe({
        error:(err)=>{
          console.log(err);
        }
      });
    this.userService.update(this.user.id,{role:this.user.role}).subscribe({
      next:()=>{
        this.notify.openSnackBar("User updated","close");
      },
      error:(err)=>{
        console.log(err);
      }
    });
    this.onClose();
  }
  onClose(){
    this.dialogRef.close();
  }
  
}
