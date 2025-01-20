package com.example.jpa.global;

import com.example.jpa.domain.post.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BaseInitData {
    private final PostService postService;

    @Bean
    public ApplicationRunner applicationRunner() {
        return args -> {
            // 데이터 3개가 이미 있으면 패스
            if (postService.count() > 0) {
                return;
            }

            // 샘플 데이터 3개 생성.
            postService.write("title1", "body1");
            postService.write("title2", "body2");
            postService.write("title3", "body3");
        };
    }
}
