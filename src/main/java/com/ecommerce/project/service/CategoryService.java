package com.ecommerce.project.service;

import com.ecommerce.project.model.Category;

import java.util.List;

public interface CategoryController {
    List<Category> getAllCategories();
    void createCategory(Category category);
}
