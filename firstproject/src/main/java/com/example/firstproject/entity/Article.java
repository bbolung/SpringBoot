package com.example.firstproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity     //테이블로 생성
//@Table(name = "article")  테이블 이름(name 생략하면 Class 이름 = 테이블 이름)
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class Article {

    @Id     //기본키(PK) 지정
    @GeneratedValue(strategy = GenerationType.AUTO)    //자동 생성 기능(숫자 자동 증가, oracle의 sequence와 동일)
    private Long id;

//  @Column(nullable = false)   Not Null
    private String title;

    private String content;

    public void patch(Article article) {

        if(article.title != null) {
            this.title = article.title;
        }

        if(article.content != null) {
            this.content = article.content;
        }

    }
}
