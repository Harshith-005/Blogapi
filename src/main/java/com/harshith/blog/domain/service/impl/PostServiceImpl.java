package com.harshith.blog.domain.service.impl;

import com.harshith.blog.domain.PostStatus;
import com.harshith.blog.domain.Repository.PostRepository;
import com.harshith.blog.domain.entity.Category;
import com.harshith.blog.domain.entity.Post;
import com.harshith.blog.domain.entity.Tag;
import com.harshith.blog.domain.entity.User;
import com.harshith.blog.domain.service.CategoryService;
import com.harshith.blog.domain.service.PostService;
import com.harshith.blog.domain.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final CategoryService categoryService;
    private final TagService tagService;
    @Override
    @Transactional(readOnly = true)
    public List<Post> getAllPosts(UUID categoryId, UUID tagId) {
        if(categoryId != null && tagId != null){
            Category category = categoryService.getCategoryById(categoryId);
            Tag tag =  tagService.getTagById(tagId);
            return postRepository.findAllByStatusAndCategoryAndTagsContains(
                    PostStatus.PUBLISHED,
                    category,
                    tag
            );
        }

        if(categoryId != null){
            Category category = categoryService.getCategoryById(categoryId);
            return postRepository.findAllByStatusAndCategory(
                    PostStatus.PUBLISHED,
                    category
            );
        }

        if(tagId != null){
            Tag tag = tagService.getTagById(tagId);
            return postRepository.findAllByStatusAndTagsContains(
                    PostStatus.PUBLISHED,
                    tag
            );
        }
        return postRepository.findAllByStatus(PostStatus.PUBLISHED);
    }

    public List<Post> getDraftPosts(User user) {
        return postRepository.findAllByAuthorAndStatus(user, PostStatus.DRAFT);
    }

}
