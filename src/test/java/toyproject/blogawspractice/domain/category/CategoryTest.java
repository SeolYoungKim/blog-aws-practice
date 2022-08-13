package toyproject.blogawspractice.domain.category;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import toyproject.blogawspractice.domain.post.Post;
import toyproject.blogawspractice.repository.category.CategoryRepository;
import toyproject.blogawspractice.repository.post.PostRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootTest
class CategoryTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void categoryJsonTest1() throws JsonProcessingException {
        //given
        Category category = Category.builder()
                .name("category")
                .build();

        categoryRepository.save(category);

        Post post = Post.builder()
                .title("title")
                .content("content")
                .author("author")
                .category(category)
                .build();

        postRepository.save(post);

        //when
        String s = objectMapper.writeValueAsString(category);
        //then
        System.out.println(s);
    }

    @Test
    void categoryJsonTest2() throws JsonProcessingException {
        //given
        Category category = Category.builder()
                .name("category")
                .build();

        categoryRepository.save(category);

        //when
        String s = objectMapper.writeValueAsString(category);
        //then
        System.out.println(s);
    }

    @Test
    void categoryJsonTest3() throws JsonProcessingException {
        //given
        List<Category> categories = IntStream.range(1, 11)
                .mapToObj(i -> Category.builder()
                        .name("category" + i)
                        .build())
                .collect(Collectors.toList());

        categoryRepository.saveAll(categories);

        //when
        for (Category category : categories) {
            System.out.println(objectMapper.writeValueAsString(category));
        }
        //then
    }

}