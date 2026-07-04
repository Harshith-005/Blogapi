package com.harshith.blog.mapper;

import com.harshith.blog.domain.dto.PostDto;
import com.harshith.blog.domain.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.springframework.jmx.export.annotation.ManagedNotification;

import java.awt.*;
import java.lang.annotation.Target;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {

    @Mapping(target = "author",source = "author")
    @Mapping(target = "category",source = "category")
    @Mapping(target = "tags",source = "tags")
    PostDto toDto(Post post);
}
