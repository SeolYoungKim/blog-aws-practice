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

//        //TODO: 두고두고 잊지 않길. 8시간 삽질의 원흉이다.
//        if (category != null) {
//            category.addPost(this);
//        } // category가 지정된 경우, Category를 post에 넣어주면서, 자동으로 카테고리의 리스트에도 추가
    }

    public void edit(RequestEditPost editPost) {
        this.title = editPost.getTitle();
        this.content = editPost.getContent();
        this.category = editPost.getCategory(); // 글 생성 시점에 카테고리를 지정할 수도 있지만, 글 생성 후에 카테고리를 지정할 수 있음.
    }

}

