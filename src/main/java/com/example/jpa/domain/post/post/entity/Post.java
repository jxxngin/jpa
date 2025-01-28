package com.example.jpa.domain.post.post.entity;

import com.example.jpa.domain.member.entity.Member;
import com.example.jpa.domain.post.comment.entity.Comment;
import com.example.jpa.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Post extends BaseEntity {
    @Column(length = 100)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String body;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member author;

    @OneToMany(mappedBy = "post", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    @Builder.Default    // mappedBy를 사용하지 않은 쪽이 주인
    private List<Comment> comments = new ArrayList<>();

    public void addComment(Comment c) {
        comments.add(c);
        c.setPost(this);
    }

    public void removeComment(Comment c1) {
        comments.remove(c1);
    }

    public void removeComment(long id) {
        Optional<Comment> opComment = comments.stream()
                .filter(com -> com.getId() == id)
                .findFirst();

        opComment.ifPresent(comment -> comments.remove(comment));
    }

    public void removeAllComments() {
        comments.forEach(comment -> {
            comment.setPost(null);
        });
        comments.clear();
    }
}
