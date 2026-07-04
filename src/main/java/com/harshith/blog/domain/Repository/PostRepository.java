package com.harshith.blog.domain.Repository;

import com.harshith.blog.domain.PostStatus;
import com.harshith.blog.domain.entity.Category;
import com.harshith.blog.domain.entity.Post;
import com.harshith.blog.domain.entity.Tag;
import com.harshith.blog.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;
@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {

    List<Post> findAllByStatusAndCategoryAndTagsContains(
            PostStatus status,
            Category category,
            Tag tag
    );

    List<Post> findAllByStatusAndCategory(
            PostStatus status,
            Category category
    );

    List<Post> findAllByStatusAndTagsContains(
            PostStatus status,
            Tag tag
    );

    List<Post> findAllByStatus(PostStatus status);

    List<Post> findAllByAuthorAndStatus(
            User author,
            PostStatus status
    );
}