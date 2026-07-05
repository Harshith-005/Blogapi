package com.harshith.blog.domain;

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
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePostRequest {
    private UUID id;
    public String title;
    private String content;
    private UUID categoryId;
    @Builder.Default
    private Set<java.util.UUID> tagId = new HashSet<>();
    private PostStatus status;
}

