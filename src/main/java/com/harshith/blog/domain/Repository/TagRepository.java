package com.harshith.blog.domain.Repository;

import com.harshith.blog.domain.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface TagRepository extends JpaRepository<Tag, UUID> {
}
