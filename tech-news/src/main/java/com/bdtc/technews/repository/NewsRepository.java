package com.bdtc.technews.repository;

import com.bdtc.technews.model.News;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NewsRepository extends JpaRepository<News, UUID> {
}
