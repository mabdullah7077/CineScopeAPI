package com.example.sakila.controllers;

import com.example.sakila.dto.response.CategoryResponse;
import com.example.sakila.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping // Get all categories
    public List<CategoryResponse> getAllCategories() {
        return categoryService.getAllCategories();
    }
}