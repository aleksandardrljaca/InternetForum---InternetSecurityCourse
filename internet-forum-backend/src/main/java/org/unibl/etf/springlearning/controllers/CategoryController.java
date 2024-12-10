package org.unibl.etf.springlearning.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unibl.etf.springlearning.models.dto.Category;
import org.unibl.etf.springlearning.services.CategoryEntityService;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryEntityService categoryEntityService;

    public CategoryController(CategoryEntityService categoryEntityService) {
        this.categoryEntityService = categoryEntityService;
    }
    @GetMapping("")
    public List<Category> findAll(){
        return categoryEntityService.findAll();
    }

    @GetMapping("/{name}")
    public Category findByName(@PathVariable String name){
        return categoryEntityService.findByName(name);
    }
}
