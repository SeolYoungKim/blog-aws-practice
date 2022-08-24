# blog-aws-practice

## 소개
RestAPI(js) + Serverside template(Thymeleaf)를 혼용하여 구현한 게시판입니다.

### 현재 완료된 작업
- 글 및 카테고리 관련 CRUD
- OAuth2.0을 이용한 구글, 네이버, 카카오 로그인 구현
- Spring security를 이용한 글 수정 및 삭제 권한 부여(작성자만 가능하도록) 및 개인 페이지 구현
  - 특히, 삭제 기능은 관리자에게는 제한 없이 삭제가 가능하도록 구성하였습니다.
- AWS EC2 + Nginx 무중단배포
- `Utterances`를 이용한 댓글 기능 추가

### 예정
- 좋아요, 싫어요, 파일업로드 기능
- 뷰 파일을 보기 좋게 변경하기
- API 문서 작성

## ERD
![img.png](readmeImg/img.png)

## 프로젝트 일지
프로젝트를 만들면서, 프로젝트 일지를 작성했습니다.
- [프로젝트 일지 바로가기](https://robust-price-530.notion.site/049dff13906643878a9f2c7e40ee44f3)


## Error Log
프로젝트를 만들면서 부딪혔던 문제들과 에러에 대한 해결 과정을 작성했습니다.
- [에러 로그 바로가기](https://robust-price-530.notion.site/ERROR-LOG-5e4b68af096b40f480c4c023c4e797c5)
- [OAuth2, Session, 그리고 테스트](https://velog.io/@kimsy8979/OAuth2-Session-%EA%B7%B8%EB%A6%AC%EA%B3%A0-%ED%85%8C%EC%8A%A4%ED%8A%B8)