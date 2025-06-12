package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    private final EntityManager entityManger;

    @Transactional
    public List<Article> createArticles(List<ArticleForm> dtos) {

        // 0. toEntity(List<Article> articles) 변환
        ArticleForm articleForm = new ArticleForm();
        List<Article> articleLists2 = articleForm.toEntity(dtos);

        // 1. dto 묶음 -> Entity 묶음으로 변환
        List<Article> articleList = dtos.stream().map(dto -> dto.toEntity())
                                        .collect(Collectors.toList());

        // 2. Entity 묶음을 DB에 저장
        articleList.stream().forEach(article -> articleRepository.save(article));

        entityManger.flush();

        /*
        for(Article article : articelList){
            articleRepository.save(article);
        }
        */

        // 3. 강제 예외 발생시키기(지금 저장했던 내용을 rollback)
        articleRepository.findById(-1L)
                        .orElseThrow(() -> new IllegalArgumentException("결제 실패!!!"));

        // 4. 결과 값 반환
        return articleList;
    }
}
