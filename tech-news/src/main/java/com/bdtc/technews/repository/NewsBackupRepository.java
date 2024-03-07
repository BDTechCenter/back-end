package com.bdtc.technews.repository;

import com.bdtc.technews.model.News;
import com.bdtc.technews.model.NewsBackup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface NewsBackupRepository extends JpaRepository<NewsBackup, Long> {
    List<NewsBackup> findAllByNewsId(UUID id);

    @Query("""
            SELECT COUNT(b) > :limit
            FROM NewsBackup b
            WHERE b.newsId = :newsId
            """)
    boolean hasMoreThanBackupLimit(@Param("newsId") UUID newsId, @Param("limit") int limit);
}
