package com.bdtc.techradar.repository;

import com.bdtc.techradar.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ItemRepository extends JpaRepository<Item, UUID> {
    List<Item> findAllByIsActiveTrue();

    List<Item> findAllByNeedAdminReviewTrue();

    @Query(
            """
            SELECT i FROM Items i
            WHERE i.authorEmail = :authorEmail
            ORDER BY i.needAdminReview = true
            """
    )
    List<Item> findAllByAuthorEmail(String authorEmail);
}
