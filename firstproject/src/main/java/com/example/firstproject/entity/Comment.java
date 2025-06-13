package com.example.firstproject.entity;

import com.example.firstproject.dto.CommentDto;
import jakarta.persistence.*;
import lombok.*;

@Entity  //테이블 생성하겠다 선언, @ID(기본키 지정) 필수!
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comment {

    @Id     //기본키
    @GeneratedValue(strategy = GenerationType.IDENTITY)     //DB에서 자동으로 1씩 증가
    private Long id;

    private String nickname;    //댓글 작성자

    private String body;        //댓글 본문

    //Comment 엔티티와 Article 엔티티 관계 설정(N:1)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")    //외래키 이름 지정: article_id
    private Article article;     //외래키 이름 기본값: article_id(테이블명_기본키)

    //새 Entity 생성
    public static Comment create(CommentDto dto, Article article) {
        //예외 발생
        if(dto.getId() !=null){
            throw new IllegalArgumentException("댓글 생성 실패! 댓글의 id가 없어야 합니다.");
        }

        if(dto.getArticleId() != article.getId()){
            throw new IllegalArgumentException("댓글 생성 실패! 게시글의 id가 잘못됐습니다.");
        }

        //Entity 생성 및 반환
        return new Comment(dto.getId(), dto.getNickname(), dto.getBody(), article);
    }

    //댓글 수정(입력한 값만 변경)
    public void patch(CommentDto dto) {
        //예외 발생
        if(this.id != dto.getId()){
            throw new IllegalArgumentException("댓글 수정 실패! 잘못된 id가 입력되었습니다.");
        }
        
        //객체 갱신
        if(dto.getNickname() != null){      //nickname 수정? -> 있으면 덮어씌움
            this.nickname = dto.getNickname();
        }
        
        if(dto.getBody() != null){      //body 수정? -> 있으면 덮어씌움
            this.body = dto.getBody();
        }
    }
}
