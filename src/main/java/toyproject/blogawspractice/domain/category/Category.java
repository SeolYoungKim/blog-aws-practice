package toyproject.blogawspractice.domain.category;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toyproject.blogawspractice.domain.BaseTimeEntity;
import toyproject.blogawspractice.domain.post.Post;
import toyproject.blogawspractice.web.request.category.RequestEditCategory;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
@Entity
@Getter
public class Category extends BaseTimeEntity {

    @Id
    @Column(name = "category_id")
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "category_name")
    private String name;

    @OneToMany(mappedBy = "category")
    private final List<Post> postList = new ArrayList<>();

    @Builder
    public Category(String name) {
        this.name = name;
    }

    // 연관관계 편의 메서드
    public void addPost(Post post) {
        postList.add(post);
    }

    // 수정 메서드
    public void edit(RequestEditCategory editCategory) {
        this.name = editCategory.getName();
    }

    // TODO: 카테고리에 글을 추가하는 기능 구현.
    //  - 카테고리에 글을 지정해주면(카테고리에 글 추가) post entity의 category도 함께 수정된다.
    //  - 카테고리에 글 목록을 체크박스화해서 체크하여 전달해주면 post의 category가 수정된다.
    //  - 이 내용을 카테고리에 구현할지, post entity에 onlyEditCategory로 구현할지 고민중이다.
}
