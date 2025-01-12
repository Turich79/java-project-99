package hexlet.code.app.util;

import hexlet.code.app.model.TaskStatus;
import org.instancio.Instancio;
import org.instancio.Model;
import org.instancio.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//import hexlet.code.app.model.Post;
//import hexlet.code.app.model.PostComment;
import hexlet.code.app.model.User;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import net.datafaker.Faker;

@Getter
@Component
public class ModelGenerator {
//    private Model<Post> postModel;
    private Model<User> userModel;
    private Model<TaskStatus> taskStatusModel;
//    private Model<PostComment> postCommentModel;

    @Autowired
    private Faker faker;

    @PostConstruct
    private void init() {


        userModel = Instancio.of(User.class)
                .ignore(Select.field(User::getId))
//                .ignore(Select.field(User::getPosts))
                .supply(Select.field(User::getEmail), () -> faker.internet().emailAddress())
                .toModel();

        taskStatusModel = Instancio.of(TaskStatus.class)
            .supply(Select.field(TaskStatus::getName), () -> faker.internet().slug())
            .supply(Select.field(TaskStatus::getSlug), () -> faker.internet().slug()).toModel();

        userModel = Instancio.of(User.class)
            .ignore(Select.field(User::getId))
            .supply(Select.field(User::getFirstName), () -> faker.name().firstName())
            .supply(Select.field(User::getLastName), () -> faker.name().lastName())
            .supply(Select.field(User::getEmail), () -> faker.internet().emailAddress())
            .supply(Select.field(User::getPasswordDigest), () -> faker.internet().password(3, 100))
//            .ignore(Select.field(User::getTasks))
            .toModel();

//        taskModel = Instancio.of(Task.class)
//            .ignore(Select.field(Task::getId))
//            .ignore(Select.field(Task::getIndex))
//            .ignore(Select.field(Task::getAssignee))
//            .supply(Select.field(Task::getName), () -> faker.name().title())
//            .supply(Select.field(Task::getDescription), () -> faker.text().text())
//            .ignore(Select.field(Task::getTaskStatus))
//            .ignore(Select.field(Task::getLabels))
//            .toModel();

        taskStatusModel = Instancio.of(TaskStatus.class)
            .ignore(Select.field(TaskStatus::getId))
//            .ignore(Select.field(TaskStatus::getTasks))
            .supply(Select.field(TaskStatus::getName), () -> faker.lorem().word())
            .supply(Select.field(TaskStatus::getSlug), () -> faker.internet().slug())
            .toModel();

//        labelModel = Instancio.of(Label.class)
//            .ignore(Select.field(Label::getId))
//            .supply(Select.field(Label::getName), () -> faker.lorem().characters(3, 8))
//            .ignore(Select.field(Label::getTasks))
//            .toModel();
    }
}
