package hexlet.code.app.util;

import hexlet.code.app.model.Task;
import hexlet.code.app.model.TaskStatus;
//import hexlet.code.app.repository.TaskRepository;
//import hexlet.code.app.repository.TaskStatusRepository;
//import hexlet.code.app.service.TaskStatusService;
import org.instancio.Instancio;
import org.instancio.Model;
import org.instancio.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hexlet.code.app.model.User;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import net.datafaker.Faker;

@Getter
@Component
public class ModelGenerator {
    private Model<User> userModel;
    private Model<TaskStatus> taskStatusModel;
    private Model<Task> taskModel;

    @Autowired
    private Faker faker;

    @PostConstruct
    private void init() {

        userModel = Instancio.of(User.class)
            .ignore(Select.field(User::getId))
            .supply(Select.field(User::getFirstName), () -> faker.name().firstName())
            .supply(Select.field(User::getLastName), () -> faker.name().lastName())
            .supply(Select.field(User::getEmail), () -> faker.internet().emailAddress())
            .supply(Select.field(User::getPasswordDigest), () -> faker.internet().password(3, 100))
            .ignore(Select.field(User::getTasks))
            .toModel();

        taskStatusModel = Instancio.of(TaskStatus.class)
            .ignore(Select.field(TaskStatus::getId))
            .ignore(Select.field(TaskStatus::getTasks))
            .supply(Select.field(TaskStatus::getName), () -> faker.lorem().word())
            .supply(Select.field(TaskStatus::getSlug), () -> faker.internet().slug())
            .toModel();

        taskModel = Instancio.of(Task.class)
            .ignore(Select.field("id"))
//            .ignore(Select.field("taskStatus"))
//            .ignore(Select.field("assignee"))
//            .ignore(Select.field("labels"))
            .supply(Select.field("name"), () -> faker.beer().name())
            .supply(Select.field("index"), () -> faker.number().positive())
            .supply(Select.field("description"), () -> faker.beer().brand())
            .toModel();

//        labelModel = Instancio.of(Label.class)
//            .ignore(Select.field(Label::getId))
//            .supply(Select.field(Label::getName), () -> faker.lorem().characters(3, 8))
//            .ignore(Select.field(Label::getTasks))
//            .toModel();
    }
}
