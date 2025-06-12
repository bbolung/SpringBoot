package com.example.firstproject.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CommentRepositoryTest {

    @Autowired
    private CommentRepository comment;

    @Test
    void findByArticleId() {
        comment.findByArticleId(4L)
                .forEach(System.out::println);
                //.forEach(comment -> System.out.println(comment));
    }

    @Test
    @DisplayName("특정 게시글 닉네임으로 조회")
    void findByNickname(){

        comment.findByNickname("Kim")
                .forEach(System.out::println);
    }

}