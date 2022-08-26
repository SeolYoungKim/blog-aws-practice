package toyproject.blogawspractice.repository.post;

import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.blogawspractice.domain.post.Post;
import toyproject.blogawspractice.domain.user.User;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {
    Optional<Post> findByTitle(String title);
    Optional<Post> findByContent(String content);
    Optional<Post> findByUser(User user);
}
