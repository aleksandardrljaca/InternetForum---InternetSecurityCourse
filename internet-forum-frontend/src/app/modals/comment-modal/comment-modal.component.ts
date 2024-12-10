import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { NotifyService } from '../../services/notify-service/notify.service';
import { CommentService } from '../../services/comment-service/comment.service';
import { TokenService } from '../../services/token-service/token.service';
import { CategoryService } from '../../services/category-service/category.service';
@Component({
  selector: 'app-comment-modal',
  templateUrl: './comment-modal.component.html',
  styleUrl: './comment-modal.component.css'
})
export class CommentModalComponent{
  isEditReq:boolean;
  comment:any;
  constructor(
  @Inject(MAT_DIALOG_DATA) public data:any, 
  public dialogRef: MatDialogRef<CommentModalComponent>,
  private notifyService:NotifyService,
  private commentService:CommentService,
  private categoryService:CategoryService,
  private tokenService:TokenService
  ) {
    this.isEditReq=data.isEditReq;
    this.comment=data.comment;
    
  }

  close(): void {
    this.dialogRef.close(false);
  }
  send(comment:string):void{
    if(this.isEditReq){
      this.commentService.updateComment(this.comment.id,{
                                          userByUserIdId:this.comment.userByUserIdId,
                                          content:comment,
                                          categoryByCategoryIdId:this.comment.categoryByCategoryIdId
                                        }).subscribe({next:(data)=>{
                                          if(data)
                                            console.log(data.content);
                                        }});
    }
    else{
      const category=sessionStorage.getItem('category');
    console.log(category)
    if(category){
      this.categoryService.getSelectedCategory(category).subscribe({
        next:(data)=>{
          const user=this.tokenService.getUser();
          this.commentService.insertComment(
            {
              userByUserIdId:user.id,
              content:comment,
              categoryByCategoryIdId:data.id
            }).subscribe({next:(data)=>{
            if(data)
              console.log(data.content);
          }});
          
        },
        error:(err)=>{
          console.log(err);
        }
      });
    }
    }
      
    this.close();
  }
}
