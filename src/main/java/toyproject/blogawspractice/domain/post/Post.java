package toyproject.blogawspractice.domain.post;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toyproject.blogawspractice.domain.BaseTimeEntity;
import toyproject.blogawspractice.domain.category.Category;
import toyproject.blogawspractice.web.request.post.RequestEditPost;

import javax.persistence.*;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
@Entity
@Getter
public class Post extends BaseTimeEntity {

    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private String author;  //TODO: 로그인 한 User의 Id로 대체

    @ManyToOne(cascade = PERSIST, fetch = LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Builder
    public Post(String title, String content, String author, Category category) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.category = category;
    }

    public void edit(RequestEditPost editPost) {
        this.title = editPost.getTitle();
        this.content = editPost.getContent();
    }
}

