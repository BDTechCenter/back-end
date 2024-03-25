package com.bdtc.technews.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
public class NewsUpVoter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String voterEmail;

    @ManyToOne
    @JoinColumn(name = "news_id")
    private News news;

    public NewsUpVoter(String voterEmail, News news) {
        this.voterEmail = voterEmail;
        this.news = news;
    }
}
