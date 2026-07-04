package com.harshith.blog.domain.controller;

import com.harshith.blog.domain.dto.PostDto;
import com.harshith.blog.domain.entity.Post;
import com.harshith.blog.domain.entity.User;
import com.harshith.blog.domain.service.PostService;
import com.harshith.blog.domain.service.UserService;
import com.harshith.blog.mapper.PostMapper;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<List<PostDto>> getDraft(@RequestAttribute UUID uuid)
    {
        User loggedInUser = userService.getUserByid(uuid);
        List<Post> draftPost =  postService.getDraftPosts(loggedInUser);
        List<PostDto> postDtos = draftPost.stream().map(postMapper::toDto).toList();
        return ResponseEntity.ok(postDtos);
    }

}
