package toyproject.blogawspractice.repository.category;

import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.blogawspractice.domain.category.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
