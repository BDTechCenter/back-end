package com.bdtc.technews.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CommentUpVoter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String voterEmail;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;
}
