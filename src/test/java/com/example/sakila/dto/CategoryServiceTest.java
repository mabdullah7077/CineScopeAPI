package com.example.sakila.dto;

import com.example.sakila.dto.response.CategoryResponse;
import com.example.sakila.entities.Category;
import com.example.sakila.repos.CategoryRepo;
import com.example.sakila.services.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryServiceTest {

    private CategoryRepo categoryRepo;
    private CategoryService categoryService;
    private Category category1;
    private Category category2;

    @BeforeEach
    void setUp() {
        categoryRepo = mock(CategoryRepo.class);
        categoryService = new CategoryService(categoryRepo);

        category1 = new Category();
        category1.setId((short) 1);
        category1.setName("Action");
        category2 = new Category();
        category2.setId((short) 2);
        category2.setName("Comedy");
    }

    @Test
    void testGetAllCategories() {
        when(categoryRepo.findAll()).thenReturn(Arrays.asList(category1, category2));

        List<CategoryResponse> responses = categoryService.getAllCategories();

        assertEquals(2, responses.size());
        assertEquals("Action", responses.get(0).getName());
        assertEquals("Comedy", responses.get(1).getName());
    }
}

