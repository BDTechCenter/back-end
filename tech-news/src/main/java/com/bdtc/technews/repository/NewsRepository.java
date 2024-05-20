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

    Page<News> findByIsPublishedTrueOrderByViewsDesc(Pageable pageable);

    @Query(
            """
            SELECT DISTINCT n FROM News n
            JOIN n.tags t
            WHERE t.name IN :tagNames
            AND n.isPublished = true
            GROUP BY n
            HAVING COUNT(DISTINCT t) = :tagCount
            """
    )
    Page<News> findByTagNames(Pageable pageable,
                              @Param("tagNames") List<String> tags,
                              @Param("tagCount") Long tagCount);

    Page<News> findAllByIsPublishedTrue(Pageable pageable);

    Page<News> findAllByIsPublishedFalse(Pageable pageable);

    @Query(
            """
            SELECT n FROM News n
            WHERE n.isPublished = true
            ORDER BY n.updateDate DESC
            """
    )
    Page<News> findByIsPublishedTrueAndLatestUpdate(Pageable pageable);

    @Query("""
            SELECT n FROM News n
            WHERE n.isPublished = true
            ORDER BY n.upVotes DESC
            """)
    Page<News> getNewsByRelevance(Pageable pageable);

    @Query("""
           SELECT n FROM News n
           WHERE n.isPublished = true
           AND LOWER(n.title) LIKE LOWER(CONCAT('%', :titleFilter, '%')) 
           """)
    Page<News> findAllByLikeTitleFilter(Pageable pageable, String titleFilter);

    @Query("""
            SELECT n FROM News n
            WHERE n.authorEmail = :currentUserEmail
            ORDER BY n.updateDate DESC
            """)
    Page<News> getNewsByAuthor(String currentUserEmail, Pageable pageable);

    @Query("""
            SELECT n FROM News n
            WHERE n.authorEmail = :currentUserEmail
            AND n.isPublished = :isPublished
            """)
    Page<News> getNewsByAuthorAndPublication(String currentUserEmail, Pageable pageable, boolean isPublished);

    @Query(
            """
            SELECT DISTINCT n FROM News n
            JOIN n.tags t
            WHERE t.name IN :tagNames
            AND n.isPublished = true
            GROUP BY n
            HAVING COUNT(DISTINCT t) = :tagCount
            AND LOWER(n.title) LIKE LOWER(CONCAT('%', :titleFilter, '%'))
            """
    )
    Page<News> findNewsByTagsAndTitle(
            Pageable pageable,
            @Param("tagNames") List<String> tags,
            @Param("tagCount") Long tagCount,
            String titleFilter
    );
}
