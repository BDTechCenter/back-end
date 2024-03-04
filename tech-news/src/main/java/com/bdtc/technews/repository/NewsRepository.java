package com.bdtc.technews.repository;

import com.bdtc.technews.model.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface NewsRepository extends JpaRepository<News, UUID> {
    Page<News> findByOrderByViewsDesc(Pageable pageable);

    @Query(
            """
            SELECT DISTINCT n FROM News n
            JOIN n.tags t
            WHERE t.name  
            IN :tagNames
            GROUP BY n
            HAVING COUNT(DISTINCT t) = :tagCount
            """
    )
    Page<News> findByTagNames(Pageable pageable,
                              @Param("tagNames") List<String> tags,
                              @Param("tagCount") Long tagCount);
}
