package com.harshith.blog.mapper;

import com.harshith.blog.domain.CreatePostRequest;
import com.harshith.blog.domain.UpdatePostRequest;
import com.harshith.blog.domain.dto.CreatePostRequestDto;
import com.harshith.blog.domain.dto.PostDto;
import com.harshith.blog.domain.dto.UpdatePostRequestDto;
import com.harshith.blog.domain.entity.Post;
import jakarta.validation.Valid;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {

    @Mapping(target = "author",source = "author")
    @Mapping(target = "category",source = "category")
    @Mapping(target = "tags",source = "tags")
    @Mapping(target = "status",source = "status")
    PostDto toDto(Post post);

    CreatePostRequest tocreatePostRequest(CreatePostRequestDto toDto);

    UpdatePostRequest toupdatePostRequest(@Valid UpdatePostRequestDto toDto);
}
