package com.harshith.blog.mapper;

import com.harshith.blog.domain.PostStatus;
import com.harshith.blog.domain.dto.CategoryDto;
import com.harshith.blog.domain.dto.CreateCategoryRequest;
import com.harshith.blog.domain.entity.Category;
import com.harshith.blog.domain.entity.Post;
import jakarta.validation.Valid;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {

    @Mapping(target = "postCount", source = "posts", qualifiedByName = "calculatePostCount")
    CategoryDto toDto(Category category);

    Category toEntity(@Valid CreateCategoryRequest categoryDto);

    @Named("calculatePostCount")
    default Long calculatePostCount(List<Post> posts) {
        if (posts == null || posts.isEmpty()) {
            return 0L;
        }
        return posts.stream().filter(post-> PostStatus.PUBLISHED.equals(post.getStatus())).count();
    }
}