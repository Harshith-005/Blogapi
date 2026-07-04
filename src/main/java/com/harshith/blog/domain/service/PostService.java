package com.harshith.blog.domain.service;

import com.harshith.blog.domain.dto.CategoryDto;
import com.harshith.blog.domain.entity.Post;
import com.harshith.blog.domain.entity.User;

import java.util.List;
import java.util.UUID;

public interface PostService {
    List<Post> getAllPosts(UUID categoryId, UUID tagId);
    List<Post> getDraftPosts(User user);

}
