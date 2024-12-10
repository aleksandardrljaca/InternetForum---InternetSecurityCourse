import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Properties } from '../../properties/properties';
@Injectable({
  providedIn: 'root'
})
export class CommentService {

  constructor(private httpClient:HttpClient,private route:ActivatedRoute) { }
  getAll(){
    return this.httpClient.get<any>(Properties.API_BASE_URL+'/api/comments');
  }

  getAllByCategory(name:string){
    return this.httpClient.get<any>(Properties.API_BASE_URL+'/api/comments/'+name);
  }
  insertComment(request:any){
   return this.httpClient.post<any>(Properties.API_BASE_URL+'/api/comments',request);
  }
  updateComment(id:number,request:any){
    return this.httpClient.put<any>(Properties.API_BASE_URL+'/api/comments/'+id,request);
  }
  deleteComment(id:number){
    return this.httpClient.delete<any>(Properties.API_BASE_URL+'/api/comments/'+id);
  }
  approveComment(id:number,request:any){
    return this.httpClient.put<any>(Properties.API_BASE_URL+'/api/comments/approve/'+id,request);
  }
  
}
