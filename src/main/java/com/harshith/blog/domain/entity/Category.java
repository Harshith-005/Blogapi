package com.harshith.blog.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.*;

@Data
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false,updatable = false)
    private String name;

    @OneToMany(mappedBy = "category")
    private List<Post> posts = new ArrayList<>();



    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(id, category.id) && Objects.equals(name, category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
