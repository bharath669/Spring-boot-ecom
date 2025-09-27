package com.ecommerce.project.controller;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {
    private CategoryService categoryService;
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/public/categories")
//    @RequestMapping(value="/public/categories",method=RequestMethod.GET)
    public ResponseEntity<List<Category>> getAllCategories(){
        return new ResponseEntity<>(categoryService.getAllCategories(),HttpStatus.OK);
    }
    @PostMapping("/admin/categories")
    public ResponseEntity<String> createCategory(@Valid @RequestBody Category category){
        categoryService.createCategory(category);
        return new ResponseEntity<>(" Category Created Successfully ",HttpStatus.CREATED);
    }
    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long  categoryId){
        try {
            String status = categoryService.deleteCategory(categoryId);
            return ResponseEntity.status(HttpStatus.OK).body(status);
        }
        catch (ResponseStatusException e){
            return  new ResponseEntity<>(e.getReason(),e.getStatusCode());
        }
    }
    @PutMapping("/admin/categories/{categoryId}")
    public ResponseEntity<String> updateCategory(@PathVariable Long categoryId,@RequestBody Category category){
        try {
            Category updateCategory=categoryService.updateCategory(categoryId,category);
            return ResponseEntity.status(HttpStatus.OK).body("Category with categoryId"+categoryId);
        }
        catch (ResponseStatusException e){
            return new ResponseEntity<>(e.getReason(),e.getStatusCode());
        }
    }
}
