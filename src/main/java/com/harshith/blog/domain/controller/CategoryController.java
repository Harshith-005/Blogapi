package com.harshith.blog.domain.controller;

import com.harshith.blog.domain.dto.CategoryDto;
import com.harshith.blog.domain.dto.CreateCategoryRequest;
import com.harshith.blog.domain.entity.Category;
import com.harshith.blog.domain.service.CategoryService;
import com.harshith.blog.mapper.CategoryMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping(path = "/api/v1/categories")
@AllArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;
    @GetMapping()
    public ResponseEntity<List<CategoryDto>> listCategories()
    {
        List<CategoryDto> categories = categoryService.listCategories().stream().map(categoryMapper::toDto).toList();
        return ResponseEntity.ok(categories);
    }

    @PostMapping
    public ResponseEntity<CategoryDto> CreateCategory(@RequestBody @Valid CreateCategoryRequest createCategoryRequest)
    {
        Category categoryToCreate = categoryMapper.toEntity(createCategoryRequest);
        categoryService.createCategory(categoryToCreate);
        return new ResponseEntity<>(
                categoryMapper.toDto(categoryToCreate), HttpStatus.CREATED
        );


    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable UUID id)
    {
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
