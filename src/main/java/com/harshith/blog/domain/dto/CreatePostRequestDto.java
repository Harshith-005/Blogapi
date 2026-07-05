package com.harshith.blog.domain.dto;


import com.harshith.blog.domain.PostStatus;
import com.harshith.blog.domain.service.PostService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatePostRequestDto {

    @Length(min = 1, max = 100,message = "title must be {min} and {max}")
    public String title;

    @NotBlank(message = "can't be empty")
    @Size(min = 3, max = 1000,message = "content must be {min} and {max}" )
    private String content;
    @NotNull(message = "categoryId is required")
    private UUID categoryId;

    @Builder.Default
    @Size(max = 10,message = "max {max} tags")
    private Set<java.util.UUID> tagId = new HashSet<>();
    @NotNull(message = "status required")
    private PostStatus status;
}
