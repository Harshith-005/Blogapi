package com.harshith.blog.domain.service;


import com.harshith.blog.domain.entity.Tag;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface TagService {
    List<Tag> getAllTags();
    List<Tag> createTags(Set<String> tagNames);
    void deleteTags(UUID id);
    Tag getTagById(UUID id);
}
