package com.example.firstproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity  //테이블 생성하겠다 선언, @ID(기본키 지정) 필수!
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    @Id     //기본키
    @GeneratedValue(strategy = GenerationType.IDENTITY)     //DB에서 자동으로 1씩 증가
    private Long id;

    private String nickname;    //댓글 작성자

    private String body;        //댓글 본문

    //Comment 엔티티와 Article 엔티티 관계 설정(N:1)
    @ManyToOne //기본 옵션 (fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")    //외래키 이름 지정: article_id
    private Article article;     //외래키 이름 기본값: article_id(테이블명_기본키)

}
