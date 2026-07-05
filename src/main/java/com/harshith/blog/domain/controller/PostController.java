package com.harshith.blog.domain.controller;

import com.harshith.blog.domain.CreatePostRequest;
import com.harshith.blog.domain.UpdatePostRequest;
import com.harshith.blog.domain.dto.CreatePostRequestDto;
import com.harshith.blog.domain.dto.PostDto;
import com.harshith.blog.domain.dto.UpdatePostRequestDto;
import com.harshith.blog.domain.entity.Post;
import com.harshith.blog.domain.entity.User;
import com.harshith.blog.domain.service.PostService;
import com.harshith.blog.domain.service.UserService;
import com.harshith.blog.mapper.PostMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final PostMapper postMapper;
    private final UserService userService;
    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts(
            @RequestParam(required = false)UUID cateogryId,
            @RequestParam(required = false)UUID tagId
            )
    {
        List<Post> posts = postService.getAllPosts(cateogryId, tagId);
        List<PostDto> postDtos = posts.stream().map(postMapper::toDto).toList();
        return ResponseEntity.ok(postDtos);

    }

    @GetMapping("/drafts")
    public ResponseEntity<List<PostDto>> getDraft(
            @RequestAttribute("userId") UUID userId) {

        User loggedInUser = userService.getUserByid(userId);

        List<Post> draftPosts = postService.getDraftPosts(loggedInUser);

        List<PostDto> postDtos = draftPosts.stream()
                .map(postMapper::toDto)
                .toList();

        return ResponseEntity.ok(postDtos);
    }

    @PostMapping()
    public ResponseEntity<PostDto> createPost(@RequestBody @Valid CreatePostRequestDto createPostRequestDto,@RequestAttribute UUID userId)
    {
        User loggedInUser = userService.getUserByid(userId);
        CreatePostRequest createPostRequest = postMapper.tocreatePostRequest(createPostRequestDto);
        Post createdPost = postService.createPost(createPostRequest,loggedInUser);
        PostDto createdpostDto = postMapper.toDto(createdPost);
        return new ResponseEntity<>(createdpostDto, HttpStatus.CREATED);

    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable UUID id, @Valid @RequestBody UpdatePostRequestDto updatePostRequestDto){
        UpdatePostRequest updatePostRequest = postMapper.toupdatePostRequest(updatePostRequestDto);
        Post updatedPost = postService.updatePost(id, updatePostRequest);
        PostDto updatedpostDto = postMapper.toDto(updatedPost);
        return new ResponseEntity<>(updatedpostDto, HttpStatus.OK);
    }


    @GetMapping(path = "/{id}")
    public ResponseEntity<PostDto> getPost(@PathVariable UUID id){
        Post post = postService.getPostById(id);
        PostDto postDto = postMapper.toDto(post);
        return new ResponseEntity<>(postDto, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable UUID id){
        postService.deletePostById(id);
        return ResponseEntity.noContent().build();
    }




}
