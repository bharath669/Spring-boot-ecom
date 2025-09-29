package com.ecommerce.project.service;

import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;
import com.ecommerce.project.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CategoryResponse getAllCategories() {
        List<Category> categories=categoryRepository.findAll();
        if(categories.isEmpty()){
            throw new APIException("No Category created till now ");
        }
        List<CategoryDTO> categoryDTOS=categories.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .toList();
        CategoryResponse categoryResponse=new CategoryResponse();
        categoryResponse.setContent(categoryDTOS);
        return categoryResponse;
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category=modelMapper.map(categoryDTO, Category.class);
        Category categoryFromDb  =categoryRepository.findByCategoryName(categoryDTO.getCategoryName());
        if(categoryFromDb !=null){
            throw new APIException("Category with Name "+categoryDTO.getCategoryName()+" Already Exist");
        }
        Category savedCategory=categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDTO.class);
    }

    @Override
    public CategoryDTO deleteCategory(Long categoryId) {
        Category category=categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("category","categoryId",categoryId));
        categoryRepository.delete(category);
        return modelMapper.map(category, CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategory(Long categoryId,CategoryDTO categoryDTO) {
        Category category=modelMapper.map(categoryDTO, Category.class);
        Category savedCategory= categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category","CategoryId",categoryId));
        categoryDTO.setCategoryId(categoryId);
        Category savedCategoryDTO=categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDTO.class);
    }
}