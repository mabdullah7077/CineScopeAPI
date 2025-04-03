package com.example.sakila.dto;

import com.example.sakila.controllers.CategoryController;
import com.example.sakila.dto.response.CategoryResponse;
import com.example.sakila.services.CategoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CategoryControllerTest {

    private CategoryService categoryService;
    private CategoryController categoryController;

    @BeforeEach
    void setUp() {
        categoryService = mock(CategoryService.class);
        categoryController = new CategoryController(categoryService);
    }

    @Test
    public void getAllCategoriesReturnsListOfCategoryResponses() {
        List<CategoryResponse> expectedResponses = List.of(
                createCategoryResponse((short) 1, "Action"),
                createCategoryResponse((short) 2, "Comedy")
        );

        when(categoryService.getAllCategories()).thenReturn(expectedResponses);

        List<CategoryResponse> actualResponses = categoryController.getAllCategories();

        assertEquals(expectedResponses.size(), actualResponses.size());
        assertEquals(expectedResponses.get(0).getId(), actualResponses.get(0).getId());
        assertEquals(expectedResponses.get(0).getName(), actualResponses.get(0).getName());
    }

    private CategoryResponse createCategoryResponse(Short id, String name) {
        CategoryResponse response = new CategoryResponse();
        response.setId(id);
        response.setName(name);
        return response;
    }
}
