package com.example.jpa.domain.post.post.service;

import com.example.jpa.domain.post.post.entity.Post;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class PostServiceTest {

    @Autowired
    private PostService postService;

    @Test
    @DisplayName("글 2개 작성")
    @Transactional
    public void t1() {
        postService.write("title1", "body1");
        postService.write("title2", "body2");
    }

    @Test
    @DisplayName("모든 글 조회")
    @Transactional
    public void t2() {
        List<Post> all = this.postService.findAll();
        assertThat(all.size()).isEqualTo(3);

        Post q = all.get(0);
        assertThat("title1").isEqualTo(q.getTitle());
    }

    @Test
    @DisplayName("아이디로 글 조회")
    @Transactional
    public void t3() {
        Optional<Post> opPost = postService.findById(1);
        if(opPost.isPresent()) {
            assertThat(opPost.get().getTitle()).isEqualTo("title1");
        }
    }

    @Test
    @DisplayName("제목으로 검색")
    @Transactional
    public void t4() {
        List<Post> posts = postService.findByTitle("title1"); // select * from post where title = 'title1';
        assertThat(posts.size()).isEqualTo(3);
    }
}
