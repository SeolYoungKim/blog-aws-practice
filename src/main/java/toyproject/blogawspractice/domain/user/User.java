package toyproject.blogawspractice.domain.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toyproject.blogawspractice.domain.BaseTimeEntity;
import toyproject.blogawspractice.domain.post.Post;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

//TODO: Post와 User를 다대일 매핑해야 하지 않을까? 이에 대한 ERD를 작성해보자!

@Table(name = "Users")
@Getter
@NoArgsConstructor
@Entity
public class User extends BaseTimeEntity {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false, name = "user_name")
    private String username;  // TODO: Post entity의 작성자 이름에 들어갈 내용이다.

    @Column(nullable = false)
    private String userEmail;

    @Column
    private String userPicture;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role userRole;

    @OneToMany(mappedBy = "user")
    private final List<Post> postList = new ArrayList<>();

    @Builder
    public User(String username, String userEmail, String userPicture, Role userRole) {
        this.username = username;
        this.userEmail = userEmail;
        this.userPicture = userPicture;
        this.userRole = userRole;
    }

    public User update(String name, String picture, Role userRole) {
        this.username = name;
        this.userPicture = picture;
        this.userRole = userRole;

        return this;
    }

    // Role의 key를 얻음 > ROLE_USER...
    public String getRoleKey() {
        return userRole.getKey();
    }

    // 연관관계 편의 메서드
    public void addPost(Post post) {
        postList.add(post);
    }
}
