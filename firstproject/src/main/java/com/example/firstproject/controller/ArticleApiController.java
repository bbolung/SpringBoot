package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class ArticleApiController {

    //@Autowired 생성자 주입할 때 final을 더 많이 사용
    //private final ArticleRepository articleRepository;
    private final ArticleService articleService;

    //전체 데이터 조회
    @GetMapping("/articles")
    public List<Article> index(){
        return articleService.index();
    }

    //단건 데이터 조회
    @GetMapping("/articles/{id}")
    public Article show(@PathVariable Long id){
        return articleService.show(id);
    }

    //데이터 생성
    @PostMapping("/articles")
    public ResponseEntity<Article> create(@RequestBody ArticleForm dto){

        Article created = articleService.create(dto);

        return (created != null) ?
                ResponseEntity.status(HttpStatus.OK).body(created) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    
    //데이터 수정
    @PatchMapping("/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleForm dto){

        Article updated =  articleService.update(id, dto);

        return (updated != null) ?
                ResponseEntity.status(HttpStatus.OK).body(updated) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    //데이터 삭제
    @DeleteMapping("/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable Long id){

        Article deleted = articleService.delete(id);

        return (deleted != null) ?
                ResponseEntity.status(HttpStatus.NO_CONTENT).build() :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    //트랜잭션 연습
    @PostMapping("/transaction-test")
    public ResponseEntity<List<Article>> transactionTest(@RequestBody List<ArticleForm> dtos){
        List<Article> createdList = articleService.createArticles(dtos);

        return (createdList != null) ?
                ResponseEntity.status(HttpStatus.OK).body(createdList) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
