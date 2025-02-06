package hexlet.code.component;

import hexlet.code.model.Label;
import hexlet.code.model.TaskStatus;
import hexlet.code.repository.LabelRepository;
import hexlet.code.repository.TaskStatusRepository;
import hexlet.code.service.LabelService;
import hexlet.code.service.TaskStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import hexlet.code.model.User;
import hexlet.code.service.CustomUserDetailsService;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
public class DataInitializer implements ApplicationRunner {

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
        List<String> labels = List.of("feature", "bug");
        for (String labelName : labels) {
            var label = new Label();
            label.setName(labelName);
            labelRepository.save(label);
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
        Map<String, String> taskStatuses = Map.of(
            "Draft", "draft",
            "ToReview", "to_review",
            "ToBeFixed", "to_be_fixed",
            "ToPublish", "to_publish",
            "Published", "published");

        var taskStatusNames = taskStatuses.keySet();
        for (String name : taskStatusNames) {
            var slug = taskStatuses.get(name);
            var data = new TaskStatus();
            data.setName(name);
            data.setSlug(slug);
            taskStatusRepository.save(data);
        }
    }
}
