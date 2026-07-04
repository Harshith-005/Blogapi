package com.harshith.blog.domain.service.impl;

import com.harshith.blog.domain.Repository.CategoryRepository;
import com.harshith.blog.domain.entity.Category;
import com.harshith.blog.domain.service.CategoryService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;


    @Override
    public List<Category> listCategories() {
        return categoryRepository.findAllWithPosts();
    }

    @Override
    @Transactional
    public Category createCategory(Category category) {
        if (categoryRepository.findByName(category.getName()) != null) {
            throw new IllegalArgumentException("Category with name " + category.getName() + " already exists");
        }
        return categoryRepository.save(category);
    }

    public Void deleteCategory(UUID id) {
        Optional<Category> category = categoryRepository.findById(id);
        if(category.isPresent()) {
            if(!category.get().getPosts().isEmpty()) {
                throw new IllegalStateException("Category with id " + id + " has posts");
            }
            categoryRepository.delete(category.get());
        }
        return null;
    }
}
