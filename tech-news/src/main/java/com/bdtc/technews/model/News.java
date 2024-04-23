package com.bdtc.technews.model;

import com.bdtc.technews.dto.news.NewsRequestDto;
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

    private String authorEmail;

    private LocalDateTime creationDate;

    private LocalDateTime publicationDate;

    private LocalDateTime updateDate;

    @Column(length = 100)
    private String title;

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

    private int upVotes;

    @OneToMany(
            mappedBy = "news",
            cascade = CascadeType.REMOVE
    )
    private List<NewsUpVoter> newsUpVoters;

    public News(NewsRequestDto newsDto) {
        this.title = newsDto.title();
        this.body = newsDto.body();
        this.views = 0L;
        this.isPublished = Boolean.parseBoolean(newsDto.isPublished());
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setAuthorEmail(String authorEmail) {
        this.authorEmail = authorEmail;
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


    public void updateBody(String body) {
        this.body = body;
    }

    public void restoreBackup(NewsBackup newsBackup) {
        this.title = newsBackup.getTitle();
        this.body = newsBackup.getBody();
        this.imageUrl = newsBackup.getImageUrl();
    }

    public void archiveNews() {
        this.isPublished = false;
    }

    public void publishNews() {
        this.isPublished = true;
    }

    public void addUpVote() {
        this.upVotes += 1;
    }

    public void removeUpVote() {
        this.upVotes -=1;
    }
}