package com.harshith.blog.domain.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateTagRequest {
    @NotEmpty(message = "post should have least one tag ")
    @Size(message = "Maximum 10 tags allowed",max = 10)
    private Set<
            @Size(min = 2,max = 30,message = "tag name can be between 2 to 30 characters")
            @Pattern(
                    regexp = "^[\\w\\s-]+$",
                    message = "Tag name can only contain letters, numbers, spaces, and hyphens."
            )
             String> name;


}
