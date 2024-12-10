import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { UserEditModalComponent } from '../modals/user-edit-modal/user-edit-modal.component';
import { UserService } from '../services/user-service/user.service';

@Component({
  selector: 'app-user-management',
  templateUrl: './user-management.component.html',
  styleUrl: './user-management.component.css'
})
export class UserManagementComponent implements OnInit{
  constructor(public dialog: MatDialog,private userService:UserService){}
  
  users: any= [];
  ngOnInit(): void {
      this.loadData();
      console.log(this.users);
  }
  manage(user:any){
    this.handleCommentModal(user);
  }
  restrict(user:any){
    this.userService.restrictUser(user.id,{approve:true}).subscribe({
      next:()=>{
        this.loadData();
      },
      error:(err)=>{
        console.log(err);
      }
    });
    
  }
  verify(user:any){
    this.userService.approveUser(user.id,{approve:true}).subscribe({
      next:()=>{
        this.loadData();
      },
      error:(err)=>{
        console.log(err);
      }
    });
    
  }
  loadData(){
    this.userService.getAll().subscribe({
      next:(data)=>{
        this.users=data.filter((u:any)=>u.role!=='ADMIN');
      },
      error:(err)=>{
        console.log(err.message);
      }
    })
  }
  handleCommentModal(data:any){
    const dialogRef=this.dialog.open(UserEditModalComponent,{
      data:data
    });
    dialogRef.afterClosed().subscribe((result) => {
      this.loadData();
    });
  }
}
