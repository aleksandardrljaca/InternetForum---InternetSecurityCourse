import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CategoryService } from '../services/category-service/category.service';
@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit {
  categories:any=[]
  
  constructor(
    private router:Router,
    private categoryService:CategoryService
  ){}
  ngOnInit(): void {
      this.categoryService.getAll().subscribe({
        next:(data)=>{
          this.categories=data;
        },
        error:(err)=>{
          console.error(err);
        }
      });
      sessionStorage.removeItem('category');
  }
  onTopicClick(card:any){
    sessionStorage.setItem('category',card.name);
    this.router.navigate(['/api/categories/'+card.name]);
  
  }
  

}
