package toyproject.blogawspractice.web.response.category;

import lombok.Getter;
import toyproject.blogawspractice.domain.category.Category;
import toyproject.blogawspractice.web.response.post.ResponsePost;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ResponseCategory {

    private final Long id;
    private final String name;
    private final List<ResponsePost> postList;

    public ResponseCategory(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.postList = category.getPostList().stream()
                .map(ResponsePost::new)
                .collect(Collectors.toList());
    }
}
