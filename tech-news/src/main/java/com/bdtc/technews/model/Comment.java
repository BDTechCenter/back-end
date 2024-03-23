package com.bdtc.technews.model;

import com.bdtc.technews.dto.CommentRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "comment")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String author;

    private LocalDateTime publicationDate;

    @Column(columnDefinition = "TEXT")
    private String comment;

    @ManyToOne
    @JoinColumn(name = "news_id")
    private News news;

    private int upVotes;

    @OneToMany(
            mappedBy = "comment",
            cascade = CascadeType.REMOVE
    )
    private List<CommentUpVoter> commentUpVoters;

    public Comment(CommentRequestDto commentDto) {
        this.author = commentDto.author();
        this.comment = commentDto.comment();
    }

    public void setPublicationDate(LocalDateTime publicationDate) {
        this.publicationDate = publicationDate;
    }

    public void setNews(News news) {
        this.news = news;
    }

    public void addUpVote() {
        this.upVotes += 1;
    }
}
