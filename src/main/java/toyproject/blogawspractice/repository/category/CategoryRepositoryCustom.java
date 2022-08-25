package toyproject.blogawspractice.repository.category;

import toyproject.blogawspractice.domain.category.Category;

import java.util.Optional;

public interface CategoryRepositoryCustom {

    Optional<Category> findByName(String name);
}
