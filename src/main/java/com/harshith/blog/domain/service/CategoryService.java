package com.harshith.blog.domain.service;

import com.harshith.blog.domain.entity.Category;

import java.util.List;
import java.util.UUID;

public interface CategoryService {

    List<Category> listCategories();

    Category createCategory(Category category);

    Void deleteCategory(UUID id);

    Category getCategoryById(UUID id);
}
