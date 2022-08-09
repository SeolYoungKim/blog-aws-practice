package toyproject.blogawspractice.domain.post;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toyproject.blogawspractice.web.request.RequestEditPost;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
@Entity
@Getter
public class Post {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private String author;  //TODO: 로그인 한 User의 Id로 대체

    @Builder
    public Post(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void edit(RequestEditPost editPost) {
        this.title = editPost.getTitle();
        this.content = editPost.getContent();
    }
}

