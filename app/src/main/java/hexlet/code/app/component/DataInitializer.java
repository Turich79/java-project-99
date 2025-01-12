package hexlet.code.app.component;

//import java.util.stream.IntStream;

import hexlet.code.app.model.TaskStatus;
import hexlet.code.app.repository.TaskStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

//import hexlet.code.app.model.Post;
import hexlet.code.app.model.User;
//import hexlet.code.app.repository.PostRepository;
import hexlet.code.app.repository.UserRepository;
import hexlet.code.app.service.CustomUserDetailsService;
import lombok.AllArgsConstructor;
//import net.datafaker.Faker;

@Component
@AllArgsConstructor
public class DataInitializer implements ApplicationRunner {

//    @Autowired
//    private final UserRepository userRepository;

    @Autowired
    private final TaskStatusRepository taskStatusRepository;

    @Autowired
    private final CustomUserDetailsService userService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        addUsers();

        addStatuses();
    }

    private void addUsers() {
        var email = "hexlet@example.com";
        var userData = new User();
        userData.setEmail(email);
        userData.setFirstName(email);
        userData.setPasswordDigest("qwerty");
        userService.createUser(userData);
    }

    private void addStatuses() {
        var nameStatus = "Draft";
        var nameSlug = "draft";
        var taskStatus = new TaskStatus();
        taskStatus.setName(nameStatus);
        taskStatus.setSlug(nameSlug);
        taskStatusRepository.save(taskStatus);

        nameStatus = "ToReview";
        nameSlug = "to_review";
        taskStatus = new TaskStatus();
        taskStatus.setName(nameStatus);
        taskStatus.setSlug(nameSlug);
        taskStatusRepository.save(taskStatus);

        nameStatus = "ToBeFixed";
        nameSlug = "to_be_fixed";
        taskStatus = new TaskStatus();
        taskStatus.setName(nameStatus);
        taskStatus.setSlug(nameSlug);
        taskStatusRepository.save(taskStatus);

        nameStatus = "ToPublish";
        nameSlug = "to_publish";
        taskStatus = new TaskStatus();
        taskStatus.setName(nameStatus);
        taskStatus.setSlug(nameSlug);
        taskStatusRepository.save(taskStatus);

        nameStatus = "Published";
        nameSlug = "published";
        taskStatus = new TaskStatus();
        taskStatus.setName(nameStatus);
        taskStatus.setSlug(nameSlug);
        taskStatusRepository.save(taskStatus);
    }
}
