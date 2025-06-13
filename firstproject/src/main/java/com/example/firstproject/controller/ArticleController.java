package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
@RequestMapping("/articles")
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CommentService commentService;

    @GetMapping("/new")
    public String newArticleForm() {
        log.info("New article form");
        return "articles/new";
    }

    @PostMapping("/create")
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
    @GetMapping("/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        log.info("Show article");

        // 1. {id}값을 DB에서 꺼내오기
        Article articleEntity = articleRepository.findById(id).orElse(null);     //id값 조회(반환타입: Optional)
        log.info("articleEntity: {}", articleEntity);

        //댓글 목록 가져오기
        List<CommentDto> commentDtos = commentService.comments(id);

        // 2. Entity -> DTO 변환
        //필요하지만 우선 생략
        
        //3. view 전달
        model.addAttribute("article", articleEntity);
        model.addAttribute("commentDtos", commentDtos);
        
        return "articles/show";     //화면 전달
    }

    //전체 데이터 조회
    @GetMapping("")
    public String index(Model model) {

        // 1. 모든 데이터 가져오기
        List<Article> articleEntityList = articleRepository.findAll();

        articleEntityList.forEach(list -> {log.info("list: {}", list);});

        // 2. 모델에 데이터 등록하기
        model.addAttribute("articleList", articleEntityList);

        // 3. 뷰 페이지 설정하기
        return "articles/index";
    }

    //게시글 수정: localhost:8080/articles/2/edit 요청 -> edit() 메소드가 응답
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model) {

        // 1. 수정할 데이터 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null); //id값이 존재하지 않을 경우 에러 발생하지 않도록 null

        // 2. Model에 데이터 등록하기
        model.addAttribute("article", articleEntity);

        //3. View 페이지 설정하기
        return "articles/edit";
    }

    @PostMapping("/update")
    public String updateArticle(ArticleForm form) {

        log.info("Update article: {}", form);

        // 1. DTO를 Entity로 변환하기
        Article articleEntity = form.toEntity();

        // 2. Entity를 DB에 저장하기(동일한 id값 존재하는지 확인 후 저장)
        Article target = articleRepository.findById(articleEntity.getId()).orElse(null);

        if(target != null) {
            articleRepository.save(articleEntity);
        }

        // 3. 수정 결과 -> 상세페이지로 redirect
        return "redirect:/articles/" + articleEntity.getId();
    }

    //게시글 삭제 : localhost:8080/articles/2/delete 요청 -> delete() 메소드가 응답
    @GetMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {

        // 1. 삭제할 데이터 가져오기
        Article target = articleRepository.findById(id).orElse(null); //id값이 존재하지 않을 경우 에러 발생하지 않도록 null

        // 2. 대상 Entity 삭제, message 1번만 표시
        if(target != null) {
            articleRepository.delete(target);
            redirectAttributes.addFlashAttribute("msg", "삭제했습니다!");
        }

        //3. 결과 페이지(전체 목록 페이지) redirect
        return "redirect:/articles";
    }
}
