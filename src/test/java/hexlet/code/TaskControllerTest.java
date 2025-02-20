package hexlet.code;

import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.dto.TaskDTO;
import hexlet.code.model.Label;
import hexlet.code.model.Task;
import hexlet.code.model.TaskStatus;
import hexlet.code.model.User;
import hexlet.code.repository.LabelRepository;
import hexlet.code.repository.TaskRepository;
import hexlet.code.repository.TaskStatusRepository;
import hexlet.code.repository.UserRepository;
import hexlet.code.util.ModelGenerator;

import org.instancio.Instancio;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public final class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskStatusRepository taskStatusRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private LabelRepository labelRepository;

    @Autowired
    private ModelGenerator modelGenerator;

    @Autowired
    private ObjectMapper om;

    private User testUser;

    private TaskStatus testTaskStatus;

    private Task testTask;

    private Label testLabel;

    private JwtRequestPostProcessor token;

    @BeforeEach
    public void setUp() {
        testUser = Instancio.of(modelGenerator.getUserModel()).create();
        userRepository.save(testUser);
        token = jwt().jwt(builder -> builder.subject(testUser.getEmail()));

        testTaskStatus = Instancio.of(modelGenerator.getTaskStatusModel()).create();
        taskStatusRepository.save(testTaskStatus);

        testLabel = Instancio.of(modelGenerator.getLabelModel()).create();
        labelRepository.save(testLabel);

        testTask = Instancio.of(modelGenerator.getTaskModel()).create();
        testTask.setAssignee(testUser);
        testTask.setTaskStatus(testTaskStatus);
        testTask.getLabels().add(testLabel);
        taskRepository.save(testTask);
    }

    @AfterEach
    public void clear() {
        taskRepository.deleteAll();
        labelRepository.deleteAll();
        taskStatusRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void testGetAll() throws Exception {
        var request = get("/api/tasks").with(token);
        mockMvc.perform(request)
            .andExpect(status().isOk());
    }

    @Test
    public void testGetById() throws Exception {
        var taskId = testTask.getId();
        var request = get("/api/tasks/" + taskId).with(token);
        mockMvc.perform(request)
            .andExpect(status().isOk());
    }

    @Test
    public void testIndexWithAllParams() throws Exception {
        var key1 = testTask.getName().substring(2);
        var key2 = testTask.getAssignee().getId();
        var key3 = testTask.getTaskStatus().getSlug();
        var key4 = testLabel.getId();

        var request = get("/api/tasks?"
            + "titleCont=" + key1
            + "&assigneeId=" + key2
            + "&status=" + key3
            + "&labelId=" + key4).with(token);
        mockMvc.perform(request)
            .andExpect(status().isOk());
    }

    @Test
    public void testIndexWithParam() throws Exception {
        var key = testTask.getTaskStatus().getSlug();

        var request = get("/api/tasks?" + "status=" + key).with(token);
        mockMvc.perform(request)
            .andExpect(status().isOk());
    }

    @Test
    public void testIndexUnauthorized() throws Exception {
        var request = get("/api/tasks");
        mockMvc.perform(request)
            .andExpect(status().isUnauthorized());
    }

    @Test
    public void testCreate() throws Exception {
        var taskDTO = Instancio.of(modelGenerator.getTaskDTOModel()).create();
        taskDTO.setStatus(testTaskStatus.getSlug());
        taskDTO.setAssigneeId(testUser.getId());

        var request = post("/api/tasks").with(token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(om.writeValueAsString(taskDTO));

        mockMvc.perform(request)
            .andExpect(status().isCreated());

        var task = taskRepository.findByName(taskDTO.getTitle()).get();
        assertThat(task.getId()).isNotNull();
        assertThat(task.getCreatedAt()).isBeforeOrEqualTo(LocalDate.now());
        assertThat(task.getAssignee().getId()).isEqualTo(testUser.getId());
        assertThat(task.getTaskStatus().getSlug()).isEqualTo(testTaskStatus.getSlug());
    }

    @Test
    public void testUpdate() throws Exception {
        var taskId = testTask.getId();

        var data = new TaskDTO();
        data.setContent("surprize!");

        var request = put("/api/tasks/" + taskId).with(token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(om.writeValueAsString(data));

        mockMvc.perform(request)
            .andExpect(status().isOk());

        var task = taskRepository.getReferenceById(taskId);
        assertThat(task.getDescription()).isEqualTo("surprize!");
    }

    @Test
    public void testDelete() throws Exception {
        var taskId = testTask.getId();
        var request = delete("/api/tasks/" + taskId).with(token);
        mockMvc.perform(request)
            .andExpect(status().isNoContent());
    }
}
