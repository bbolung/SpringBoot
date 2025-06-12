-- 기존 데이터
insert into article(id, title, content) values (1, '가가가','1111');
insert into article(id, title, content) values (2, '나나나','2222');
insert into article(id, title, content) values (3, '다다다','3333');

-- article 테이블에 데이터 추가
INSERT into article(id, title, content) values(4, '당신의 인생 영화는?', '댓글 고');
INSERT into article(id, title, content) values(5, '당신의 소울 푸드는?', '댓글 고고');
INSERT into article(id, title, content) values(6, '당신의 취미는?', '댓글 고고고');

-- 4번 게시글의 댓글 추가
INSERT into comment(article_id, nickname, body) values(4, 'Park', '굿 윌 헌팅');
INSERT into comment(article_id, nickname, body) values(4, 'Kim', '아이 엠 샘');
INSERT into comment(article_id, nickname, body) values(4, 'Choi', '쇼생크 탈출');

-- 5번 게시글의 댓글 추가
INSERT into comment(article_id, nickname, body) values(5, 'Park', '치킨');
INSERT into comment(article_id, nickname, body) values(5, 'Kim', '샤브샤브');
INSERT into comment(article_id, nickname, body) values(5, 'Choi', '초밥');

-- 6번 게시글의 댓글 추가
INSERT into comment(article_id, nickname, body) values(6, 'Park', '조깅');
INSERT into comment(article_id, nickname, body) values(6, 'Kim', '유튜브 시청');
INSERT into comment(article_id, nickname, body) values(6, 'Choi', '독서');
