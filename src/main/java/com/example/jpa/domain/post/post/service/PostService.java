package com.example.jpa.domain.post.post.service;

import com.example.jpa.domain.post.post.entity.Post;
import com.example.jpa.domain.post.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public Post write(String title, String body) {
        // 1. Post 조립
        Post post = new Post();
//        post.setId(1L); id는 기본적으로 JPA가 관리함.
        post.setCreatedDate(LocalDateTime.now());
        post.setModifiedDate(LocalDateTime.now());
        post.setTitle(title);
        post.setBody(body);

        // 2. repository에게 넘김 // 3. DB 반영
        postRepository.save(post);

        return post;
    }

    public long count() {
        return postRepository.count();
    }
}
