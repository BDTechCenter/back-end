package com.bdtc.technews.repository;

import com.bdtc.technews.dto.NewsRequestDto;
import com.bdtc.technews.model.News;
import com.bdtc.technews.model.Tag;
import com.bdtc.technews.service.news.utils.DateHandler;
import com.bdtc.technews.service.tag.TagService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class NewsRepositoryTest {

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private EntityManager entityManager;


    @Test
    @DisplayName("""
                Should return Page<News>; 
                First news needs to have title '#2' (inject more views into it);
                """)
    void findByIsPublishedTrueOrderByViewsDescCase1() {
        NewsRequestDto newsOne = new NewsRequestDto("#1", "body #1",
                new HashSet<>(Set.of("test", "unit-test")), null, null);
        this.createNews(newsOne, 10, 0);

        NewsRequestDto newsTwo = new NewsRequestDto("#2", "body #2",
                new HashSet<>(Set.of("test", "unit-test")), null, null);
        this.createNews(newsTwo, 275, 0);

        Pageable pageable = PageRequest.of(0, 2);
        Page<News> newsPage = newsRepository.findByIsPublishedTrueOrderByViewsDesc(pageable);

        // VERIFYING IF RETURNED BASED ON VIEWS DESC
        News firstNews = newsPage.getContent().get(0);
        Assertions.assertEquals("#2", firstNews.getTitle());
    }

    @Test
    void findByTagNames() {
    }

    @Test
    void findByIsPublishedTrueAndLatestUpdate() {
    }

    @Test
    void getNewsByRelevance() {
    }

    @Test
    void findAllByLikeTitleFilter() {
    }

    // SOME UTILS FUNCTIONS TO HELP ON TESTS
    private News createNews(NewsRequestDto newsRequestDto, int views, int upVotes) {
        News news = new News(newsRequestDto);
        LocalDateTime dateNow = LocalDateTime.now();

        news.setAuthor("beck@gmail.com");
        news.setCreationDate(dateNow);
        news.setUpdateDate(dateNow);
        news.setTags(this.getTagSet(newsRequestDto.tags()));
        news.setImageUrl("imageUrl");

        for (int i = 0; i < views; i++) news.addAView();
        for (int i = 0; i < upVotes; i++) news.addUpVote();

        this.entityManager.persist(news);
        return news;
    }

    private Set<Tag> getTagSet(Set<String> tagsStringList) {
        Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tagsStringList) {
            Tag tagEntity = new Tag(tagName);
            tagSet.add(tagEntity);
        }
        return tagSet;
    }
}