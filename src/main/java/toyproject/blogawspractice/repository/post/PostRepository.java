package toyproject.blogawspractice.repository.post;

import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.blogawspractice.domain.post.Post;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {
}
