package hexlet.code.app.component;

//import java.util.stream.IntStream;

import hexlet.code.app.dto.label.LabelCreateDTO;
import hexlet.code.app.dto.taskStatus.TaskStatusCreateDTO;
//import hexlet.code.app.model.TaskStatus;
import hexlet.code.app.repository.LabelRepository;
import hexlet.code.app.repository.TaskStatusRepository;
import hexlet.code.app.service.LabelService;
import hexlet.code.app.service.TaskStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

//import hexlet.code.app.model.Post;
import hexlet.code.app.model.User;
//import hexlet.code.app.repository.PostRepository;
//import hexlet.code.app.repository.UserRepository;
import hexlet.code.app.service.CustomUserDetailsService;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import net.datafaker.Faker;

@Component
@AllArgsConstructor
public class DataInitializer implements ApplicationRunner {

//    @Autowired
//    private final UserRepository userRepository;
    @Autowired
    private final LabelRepository labelRepository;

    @Autowired
    private final LabelService labelService;

    @Autowired
    private final TaskStatusRepository taskStatusRepository;

    @Autowired
    private final CustomUserDetailsService userService;

    @Autowired
    private final TaskStatusRepository statusRepository;

    @Autowired
    private final TaskStatusService statusService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        addUsers();

        addStatuses();

        addLabels();
    }

    private void addLabels() {
        List<String> labels = new ArrayList<>(List.of("bug", "feature"));
        LabelCreateDTO labelData = new LabelCreateDTO();
        for (String label : labels) {
            if (labelRepository.findByName(label).isEmpty()) {
                labelData.setName(label);
                labelService.create(labelData);
            }
        }
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
        Map<String, String> statuses = new HashMap<>(
            Map.of("draft", "Draft", "to_review", "To review",
                "to_be_fixed", "Must be fixed",
                "to_publish", "Ready to publish", "published", "Published")
        );

        TaskStatusCreateDTO statusData = new TaskStatusCreateDTO();
        for (Map.Entry<String, String> status : statuses.entrySet()) {
            if (statusRepository.findBySlug(status.getKey()).isEmpty()) {
                statusData.setSlug(status.getKey());
                statusData.setName(status.getValue());
                statusService.create(statusData);
            }
        }
//        var nameStatus = "Draft";
//        var nameSlug = "draft";
//        var taskStatus = new TaskStatus();
//        taskStatus.setName(nameStatus);
//        taskStatus.setSlug(nameSlug);
//        taskStatusRepository.save(taskStatus);
//
//        nameStatus = "ToReview";
//        nameSlug = "to_review";
//        taskStatus = new TaskStatus();
//        taskStatus.setName(nameStatus);
//        taskStatus.setSlug(nameSlug);
//        taskStatusRepository.save(taskStatus);
//
//        nameStatus = "ToBeFixed";
//        nameSlug = "to_be_fixed";
//        taskStatus = new TaskStatus();
//        taskStatus.setName(nameStatus);
//        taskStatus.setSlug(nameSlug);
//        taskStatusRepository.save(taskStatus);
//
//        nameStatus = "ToPublish";
//        nameSlug = "to_publish";
//        taskStatus = new TaskStatus();
//        taskStatus.setName(nameStatus);
//        taskStatus.setSlug(nameSlug);
//        taskStatusRepository.save(taskStatus);
//
//        nameStatus = "Published";
//        nameSlug = "published";
//        taskStatus = new TaskStatus();
//        taskStatus.setName(nameStatus);
//        taskStatus.setSlug(nameSlug);
//        taskStatusRepository.save(taskStatus);
    }
}
