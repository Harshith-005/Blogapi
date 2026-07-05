package com.harshith.blog.domain.service.impl;

import com.harshith.blog.domain.CreatePostRequest;
import com.harshith.blog.domain.PostStatus;
import com.harshith.blog.domain.Repository.PostRepository;
import com.harshith.blog.domain.UpdatePostRequest;
import com.harshith.blog.domain.entity.Category;
import com.harshith.blog.domain.entity.Post;
import com.harshith.blog.domain.entity.Tag;
import com.harshith.blog.domain.entity.User;
import com.harshith.blog.domain.service.CategoryService;
import com.harshith.blog.domain.service.PostService;
import com.harshith.blog.domain.service.TagService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private static final double WORDS_PER_MINUTE = 200;

    private final PostRepository postRepository;
    private final CategoryService categoryService;
    private final TagService tagService;

    @Override
    @Transactional(readOnly = true)
    public List<Post> getAllPosts(UUID categoryId, UUID tagId) {

        if (categoryId != null && tagId != null) {
            Category category = categoryService.getCategoryById(categoryId);
            Tag tag = tagService.getTagById(tagId);

            return postRepository.findAllByStatusAndCategoryAndTagsContains(
                    PostStatus.PUBLISHED,
                    category,
                    tag
            );
        }

        if (categoryId != null) {
            Category category = categoryService.getCategoryById(categoryId);

            return postRepository.findAllByStatusAndCategory(
                    PostStatus.PUBLISHED,
                    category
            );
        }

        if (tagId != null) {
            Tag tag = tagService.getTagById(tagId);

            return postRepository.findAllByStatusAndTagsContains(
                    PostStatus.PUBLISHED,
                    tag
            );
        }

        return postRepository.findAllByStatus(PostStatus.PUBLISHED);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Post> getDraftPosts(User user) {
        return postRepository.findAllByAuthorAndStatus(user, PostStatus.DRAFT);
    }

    @Override
    @Transactional
    public Post createPost(CreatePostRequest request, User user) {

        Post post = new Post();

        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setStatus(request.getStatus());

        Category category = categoryService.getCategoryById(request.getCategoryId());
        post.setCategory(category);

        List<Tag> tags = tagService.getTagByIds(request.getTagId());
        post.setTags(new HashSet<>(tags));

        post.setAuthor(user);
        post.setReadingTime(calculateReadingTime(request.getContent()));
        System.out.println("Status = " + request.getStatus());
        return postRepository.save(post);
    }

    @Override
    @Transactional
    public Post updatePost(UUID id, UpdatePostRequest updatePostRequest) {

        Post existingPost = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));


        existingPost.setTitle(updatePostRequest.getTitle());

        String postContent = updatePostRequest.getContent();
        existingPost.setContent(postContent);

        existingPost.setStatus(updatePostRequest.getStatus());
        existingPost.setReadingTime(calculateReadingTime(postContent));

        UUID updateCategoryId = updatePostRequest.getCategoryId();

        if (!existingPost.getCategory().getId().equals(updateCategoryId)) {
            Category newCategory = categoryService.getCategoryById(updateCategoryId);
            existingPost.setCategory(newCategory);
        }

        Set<UUID> existingTagIds = existingPost.getTags()
                .stream()
                .map(Tag::getId)
                .collect(Collectors.toSet());

        Set<UUID> updatedTagIds = updatePostRequest.getTagId();

        if (!existingTagIds.equals(updatedTagIds)) {
            List<Tag> newTags = tagService.getTagByIds(updatedTagIds);
            existingPost.setTags(new HashSet<>(newTags));
        }

        return postRepository.save(existingPost);
    }

    @Override
    public Post getPostById(UUID id) {
       return postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Post not found"));
    }

    @Override
    public void deletePostById(UUID id) {
        Post post = getPostById(id);
        postRepository.delete(post);
    }
    private Integer calculateReadingTime(String content) {

        if (content == null || content.isBlank()) {
            return 0;
        }

        int wordCount = content.trim().split("\\s+").length;

        return (int) Math.ceil((double) wordCount / WORDS_PER_MINUTE);
    }
}