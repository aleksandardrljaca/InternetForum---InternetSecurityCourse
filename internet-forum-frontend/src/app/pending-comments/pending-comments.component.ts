import { Component, OnInit } from '@angular/core';
import { CommentService } from '../services/comment-service/comment.service';
import { MatDialog } from '@angular/material/dialog';
import { CommentModalComponent } from '../modals/comment-modal/comment-modal.component';

@Component({
  selector: 'app-pending-comments',
  templateUrl: './pending-comments.component.html',
  styleUrl: './pending-comments.component.css'
})
export class PendingCommentsComponent implements OnInit{
  comments:any=[];
  constructor(
    private commentService:CommentService,
    private dialog:MatDialog
  ){

  }
  loadData(){
      this.commentService.getAll().subscribe({
        next:(data)=>{
          this.comments=data.filter((c:any)=>c.approved===false)  },
          error:(err)=>{
            console.error(err);
          }
          });
    
  }
  ngOnInit(): void {
      this.loadData();
  }
  acceptComment(comment:any){
    this.commentService.approveComment(comment.id,{approve:true}).subscribe({
      next:data=>{
        this.loadData();
      },
      error:err=>{
          console.log(err.message);
      }
        
    });
  }
  editComment(comment:any){
    this.handleCommentModal({
      isEditReq:true,
      comment:comment
    });
  }
  denyComment(comment:any){
    this.commentService.deleteComment(comment.id).subscribe(retulst=>{
      this.comments=this.comments.filter((c:any)=>c.id!=comment.id);
    });
  }
  
  handleCommentModal(data:any){
    const dialogRef=this.dialog.open(CommentModalComponent,{
      data:data
    });
    dialogRef.afterClosed().subscribe((result) => {
      this.loadData();
    });
  }
}
