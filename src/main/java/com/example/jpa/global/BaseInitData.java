package com.example.jpa.global;

import com.example.jpa.domain.post.comment.entity.Comment;
import com.example.jpa.domain.post.comment.service.CommentService;
import com.example.jpa.domain.post.post.entity.Post;
import com.example.jpa.domain.post.post.service.PostService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;

@Configuration
@RequiredArgsConstructor
public class BaseInitData {
    private final PostService postService;
    private final CommentService commentService;

    // 프록시 객체를 획득
    @Autowired
    @Lazy private BaseInitData self;    // 프록시

    @Bean
    @Order(1)
    public ApplicationRunner applicationRunner() {
        return args -> {
            self.work1();
            self.work2();
        };
    }

    @Transactional
    public void work1() {
        if (postService.count() > 0) {
            return;
        }

        Post p1 = postService.write("title1", "body1");

        Comment c1 = Comment.builder()
                .body("comment1")
                .build();
        p1.addComment(c1);

        Comment c2 = Comment.builder()
                .body("comment2")
                .build();
        p1.addComment(c2);

        Comment c3 = Comment.builder()
                .body("comment3")
                .build();
        p1.addComment(c3);
    }

    @Transactional
    public void work2() {
        Post post = postService.findById(1L).get();
        // 외래키 제약 때문에 자식이 있는 post는 바로 지울 수 없다. post가 가지고 있는 댓글 3개를 먼저 지워야 한다.
//        for(Comment comment : post.getComments()) {
//            commentService.delete(comment);
//        }

        // cascade.REMOVE를 걸면 JPA가 알아서 지워준다.
        postService.delete(post);
    }
}
