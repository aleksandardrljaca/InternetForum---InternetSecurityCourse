import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { CommentModalComponent } from '../modals/comment-modal/comment-modal.component';
import { ActivatedRoute } from '@angular/router';
import { CommentService } from '../services/comment-service/comment.service';
import { TokenService } from '../services/token-service/token.service';

@Component({
  selector: 'app-comments',
  templateUrl: './comments.component.html',
  styleUrl: './comments.component.css'
})

export class CommentsComponent implements OnInit{
  
    comments:any=[]
    category:string|null="";
    hasCreatePermission:boolean=false;
    hasEditPermission:boolean=false;
    hasDeletePermission:boolean=false;
    constructor(private route: ActivatedRoute,private commentService:CommentService,private dialog:MatDialog,private tokenService:TokenService){
        this.hasCreatePermission=this.tokenService.checkUserCreatePermission();
        this.hasEditPermission=this.tokenService.checkUserEditPermission();
        this.hasDeletePermission=this.tokenService.checkUserDeletePermission();
    }
    
    loadData(){
      this.category=sessionStorage.getItem('category');
      if(this.category){
        this.commentService.getAllByCategory(this.category).subscribe({
          next:(data)=>{
            this.comments=data.filter((c:any)=>c.approved===true)  },
            error:(err)=>{
              console.error(err);
            }
            });
      }
   }
    ngOnInit(): void {
        this.loadData();
    }
    
    isLoggedUserComment(comment:any){
      return comment.userByUserIdUsername==this.tokenService.getUser().username;
    }
    addComment(){
      this.handleCommentModal({
        isEditReq:false,
        comment:null
      });
      
    }
    editComment(comment:any){
      this.handleCommentModal({
        isEditReq:true,
        comment:comment
      });
     
    }
    deleteComment(comment:any){
      this.commentService.deleteComment(comment.id).subscribe(retulst=>{
        this.comments=this.comments.filter((c:any)=>c.id!==comment.id);
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
