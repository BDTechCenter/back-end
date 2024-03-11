package com.bdtc.technews.repository;

import com.bdtc.technews.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
    boolean existsByName(String name);

    Tag findByName(String tagName);
}
