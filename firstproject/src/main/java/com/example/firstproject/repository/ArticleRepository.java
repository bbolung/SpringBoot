package com.example.firstproject.repository;

import com.example.firstproject.entity.Article;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

//테이블명, 기본키의 자료형
public interface ArticleRepository extends CrudRepository<Article, Long> {

    //findAll()의 반환타입 변경: Iterable -> List
    @Override
    List<Article> findAll();
}
