import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Properties } from '../../properties/properties';
@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  constructor(
    private httpClient:HttpClient,
    private route:ActivatedRoute
  ) { }

  getAll(){
   return this.httpClient.get(Properties.API_BASE_URL+'/api/categories');
  }
  getSelectedCategory(cateogry:string){
    return this.httpClient.get<any>(Properties.API_BASE_URL+'/api/categories/'+cateogry);
    
  }
}
