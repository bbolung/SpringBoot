package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;

    @GetMapping("/articles/new")
    public String newArticleForm() {
        log.info("New article form");
        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form){
        log.info("New article created");
        log.info("articleForm: {}", form);

        // 1. DTO를 Entity로 변환
        Article article = form.toEntity();

        // 2. Repository 이용하여 Entity를 DB에 저장
        Article saved = articleRepository.save(article);
        log.info("New article: {}", saved);

        return "redirect:/articles/" + saved.getId();
    }
    
    //단일 데이터 조회
    @GetMapping("/articles/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        log.info("Show article");

        // 1. {id}값을 DB에서 꺼내오기
        Article articleEntity = articleRepository.findById(id).orElse(null);     //id값 조회(반환타입: Optional)
        log.info("articleEntity: {}", articleEntity);

        // 2. Entity -> DTO 변환
        //필요하지만 우선 생략
        
        //3. view 전달
        model.addAttribute("article", articleEntity);
        
        return "articles/show";     //화면 전달
    }

    //전체 데이터 조회
    @GetMapping("/articles")
    public String index(Model model) {

        // 1. 모든 데이터 가져오기
        List<Article> articleEntityList = articleRepository.findAll();

        articleEntityList.forEach(list -> {log.info("list: {}", list);});

        // 2. 모델에 데이터 등록하기
        model.addAttribute("articleList", articleEntityList);

        // 3. 뷰 페이지 설정하기
        return "articles/index";
    }
}
