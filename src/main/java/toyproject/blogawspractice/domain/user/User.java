package toyproject.blogawspractice.domain.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

//TODO: Post와 User를 다대일 매핑해야 하지 않을까? 이에 대한 ERD를 작성해보자!

@Getter
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;  // TODO: Post entity의 작성자 이름에 들어갈 내용이다.

    @Column(nullable = false)
    private String email;

    @Column
    private String picture;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Builder
    public User(String name, String email, String picture, Role role) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
    }

    public User update(String name, String picture) {
        this.name = name;
        this.picture = picture;

        return this;
    }

    // Role의 key를 얻음 > ROLE_USER...
    public String getRoleKey() {
        return role.getKey();
    }
}
