package com.bdtc.technews.model;

import com.bdtc.technews.dto.NewsRequestDto;
import com.bdtc.technews.service.news.utils.DateHandler;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "news")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
public class News {

    @Id
    @GeneratedValue
    private UUID id;

    private String author;

    private LocalDateTime creationDate;

    private LocalDateTime publicationDate;

    private LocalDateTime updateDate;

    @Column(length = 100)
    private String title;

    @Column(length = 255)
    private String summary;

    @Column(columnDefinition = "TEXT")
    private String body;

    private Long views;

    private String imageUrl;

    @ManyToMany
    @JoinTable(
            name = "news_tags",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag", referencedColumnName = "name")
    )
    private Set<Tag> tags = new HashSet<>();

    @OneToMany(
            mappedBy = "news",
            cascade = CascadeType.REMOVE
    )
    private List<Comment> comments;

    private boolean isPublished;

    public News(NewsRequestDto newsDto) {
        this.author = newsDto.author();
        this.title = newsDto.title();
        this.summary = newsDto.summary();
        this.body = newsDto.body();
        this.views = 0L;
        this.isPublished = newsDto.isPublished();
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public void setPublicationDate(LocalDateTime publicationDate) {
        this.publicationDate = publicationDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public void addAView() {
        this.views += 1;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateSummary(String summary) {
        this.summary = summary;
    }

    public void updateBody(String body) {
        this.body = body;
    }

    public void restoreBackup(NewsBackup newsBackup) {
        this.title = newsBackup.getTitle();
        this.summary = newsBackup.getSummary();
        this.body = newsBackup.getBody();
        this.imageUrl = newsBackup.getImageUrl();
    }

    public void archiveNews() {
        this.isPublished = false;
    }

    public void publishNews() {
        this.isPublished = true;
    }
}