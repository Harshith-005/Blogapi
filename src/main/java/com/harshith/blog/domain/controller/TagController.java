package com.harshith.blog.domain.controller;

import com.harshith.blog.domain.dto.CreateTagRequest;
import com.harshith.blog.domain.dto.TagResponse;
import com.harshith.blog.domain.entity.Tag;
import com.harshith.blog.domain.service.TagService;
import com.harshith.blog.mapper.TagMapper;
import lombok.Generated;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;
    private final TagMapper tagMapper;

    @GetMapping
    public ResponseEntity<List<TagResponse>> getAllTags() {
        List<Tag> tags = tagService.getAllTags();
        List<TagResponse> responses =
                tags.stream()
                        .map(tagMapper::toTagResponse)
                        .toList();

        return ResponseEntity.ok(responses);
    }

    @PostMapping()
    public ResponseEntity<List<TagResponse>> createTags(@RequestBody CreateTagRequest createTagRequest)
    {
        List<Tag> savedTags = tagService.createTags(createTagRequest.getName());
        List<TagResponse> createdTagRespons = savedTags.stream().map(tagMapper::toTagResponse).toList();
        return new ResponseEntity<>(
                createdTagRespons,
                HttpStatus.CREATED
        );
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteTags(@PathVariable UUID id)
    {
        tagService.deleteTags(id);
        return ResponseEntity.noContent().build();
    }

}
