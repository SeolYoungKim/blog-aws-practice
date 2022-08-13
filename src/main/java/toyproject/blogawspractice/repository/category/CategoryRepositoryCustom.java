package toyproject.blogawspractice.repository.category;

import toyproject.blogawspractice.domain.category.Category;

public interface CategoryRepositoryCustom {

    Category findByName(String name);
}
