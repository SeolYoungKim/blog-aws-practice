package toyproject.blogawspractice.web.response;

import lombok.Getter;
import toyproject.blogawspractice.domain.post.Post;

@Getter
public class ResponsePost {

    private final Long id;
    private final String title;
    private final String content;
    private final String author;

    public ResponsePost(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.author = post.getAuthor();
    }
}
