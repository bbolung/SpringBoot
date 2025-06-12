package com.example.firstproject.service;

import com.example.firstproject.entity.Article;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class ArticleServiceTest {

    @Autowired
    ArticleService articleService;

    @Test
    void index() {

        // 1. 예상 데이터

        // 2. 실제 데이터

        // 3. 비교 및 검증
    }

    @Test
    void show_success(){
        /*
            DB에 존재하는 데이터
            id : 102 (존재하는 id)
            title : "test data"
            content : "test content"
         */

        // 1. 예상 데이터
        Article expected = new Article(102L, "test data", "test content");

        // 2. 실제 데이터
        Article article = articleService.show(102L);

        // 3. 비교 및 검증
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    void show_fail(){
        /*
            DB에 존재하는 데이터
            id : 102 (존재하는 id)
            title : "test data"
            content : "test content"
         */

        // 1. 예상 데이터
        Article expected = new Article(102L, "test data", "fail content");

        // 2. 실제 데이터
        Article article = articleService.show(102L);

        // 3. 비교 및 검증
        assertEquals(expected.toString(), article.toString());
    }
}