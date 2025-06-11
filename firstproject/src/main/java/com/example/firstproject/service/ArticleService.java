package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleService {

    private final ArticleRepository articleRepository;

    //전체 게시글 조회
    public List<Article> index(){
        return articleRepository.findAll();
    }

    //단일 게시글 조회
    public Article show(Long id){
        log.info("1....................");
        articleRepository.findById(id).ifPresent(article -> {});
        log.info("2....................");
        Article article = articleRepository.findById(id).orElse(null);
        log.info("3....................");
        return article;
    }

    //create - insert sql 실행
    public Article create(ArticleForm dto){
        Article article = dto.toEntity();

        //id값을 입력할 경우 실행하지 않음 (변경 감지 -> update 방지)
        if(article.getId() != null){
            return null;
        }

        return articleRepository.save(article);
    }

    //update - update sql 실행
    public Article update(Long id, ArticleForm dto) {

        // 1. DTO -> entity 변환
        Article article = dto.toEntity();
        log.info("restapi update: {}", article);

        // 2. 타깃 설정
        Article target = articleRepository.findById(id).orElse(null);

        // 3. 잘못된 요청 처리
        if (target == null || id != article.getId()) {
            log.info("id : {}, article: {}", id, article.getId());
            return null;
        }

        log.info("reatapi update =====> {}", article);

        // 4. update 및 정상 응답
        target.patch(article);
        return articleRepository.save(target);
    }

    //delete -> delete sql 실행
    public Article delete(Long id) {

        // 1. 대상 찾기 (sql: select)
        Article target = articleRepository.findById(id).orElse(null);

        // 2. 잘못된 요청 처리하기(target == null이면 데이터 없음 처리)
        if(target == null){
            return null;
        }

        // 3. 대상 삭제하기 (sql: delete)
        articleRepository.delete(target);
        return target;
    }

}
