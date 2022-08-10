package toyproject.blogawspractice.web.response.category;

import lombok.Getter;
import toyproject.blogawspractice.domain.category.Category;
import toyproject.blogawspractice.domain.post.Post;

import java.util.List;

@Getter
public class ResponseCategory {

    private final Long id;
    private final String name;
    private final List<Post> postList;

    public ResponseCategory(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.postList = category.getPostList();
    }
}
