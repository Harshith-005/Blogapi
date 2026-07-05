package com.harshith.blog.domain.service;

import com.harshith.blog.domain.CreatePostRequest;
import com.harshith.blog.domain.UpdatePostRequest;
import com.harshith.blog.domain.dto.CreatePostRequestDto;
import com.harshith.blog.domain.dto.UpdatePostRequestDto;
import com.harshith.blog.domain.entity.Post;
import com.harshith.blog.domain.entity.User;

import java.util.List;
import java.util.UUID;

public interface PostService {
    List<Post> getAllPosts(UUID categoryId, UUID tagId);
    List<Post> getDraftPosts(User user);
    Post createPost(CreatePostRequest createPostRequest, User loggedInUser);
    Post updatePost(UUID id, UpdatePostRequest updatePostRequest);
    Post getPostById(UUID id);
    void deletePostById(UUID id);

}
