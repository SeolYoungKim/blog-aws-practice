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

    @Column
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
}
