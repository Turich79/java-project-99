package hexlet.code.app.component;

import hexlet.code.app.dto.UserCreateDTO;
//import hexlet.code.app.model.User;
import hexlet.code.app.repository.UserRepository;
//import hexlet.code.app.service.CustomUserDetailsService;
import hexlet.code.app.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

@Component
@AllArgsConstructor
public class DataInitializer implements ApplicationRunner {
    @Autowired
    private final UserRepository userRepository;

    @Autowired
//    private final CustomUserDetailsService userService;
    private final UserService userService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        var email = "hexlet@example.com";
        var userData = new UserCreateDTO();
        //User();
        userData.setEmail(email);
        userData.setPassword("qwerty");
        userService.create(userData);

//        var user = userRepository.findByEmail(email).get();

//        var faker = new Faker();
//        IntStream.range(1, 10).forEach(i -> {
//            var post = new Post();
//            post.setName(faker.book().title());
//            var paragraphs = faker.lorem().paragraphs(5);
//            post.setBody(String.join("\n", paragraphs));
//            post.setSlug(faker.internet().slug());
//            post.setAuthor(user);
//            postRepository.save(post);
//        });
    }
}
