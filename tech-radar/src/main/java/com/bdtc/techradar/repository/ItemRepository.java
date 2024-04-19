package com.bdtc.techradar.repository;

import com.bdtc.techradar.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ItemRepository extends JpaRepository<Item, UUID> {
    List<Item> findAllByIsActiveTrue();
}
