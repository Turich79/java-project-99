package hexlet.code.app.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
//import hexlet.code.app.mapper.TaskMapper;
import hexlet.code.app.model.Task;
import hexlet.code.app.model.TaskStatus;
import hexlet.code.app.model.User;
import hexlet.code.app.repository.TaskRepository;
import hexlet.code.app.repository.TaskStatusRepository;
import hexlet.code.app.repository.UserRepository;
import hexlet.code.app.util.ModelGenerator;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
//import java.util.HashSet;
//import java.util.List;
import java.util.Map;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskStatusRepository statusRepository;

//    @Autowired
//    private LabelRepository labelRepository;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ModelGenerator modelGenerator;

    private Task testTask;

    private TaskStatus testStatus;

    private User testUser;

//    private Label testLabel;

    private SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor token;

    @BeforeEach
    public void setUp() {
        testTask = Instancio.of(modelGenerator.getTaskModel())
            .create();

        User testUser = Instancio.of(modelGenerator.getUserModel())
            .create();
        userRepository.save(testUser);

        testStatus = Instancio.of(modelGenerator.getTaskStatusModel())
            .create();
        statusRepository.save(testStatus);

//        testLabel = Instancio.of(modelGenerator.getLabelModel())
//            .create();
//        labelRepository.save(testLabel);

        testTask.setTaskStatus(testStatus);
        testTask.setAssignee(testUser);
//        testTask.setLabels(new HashSet<>(List.of(testLabel)));
        taskRepository.save(testTask);

        token = jwt().jwt(builder -> builder.subject("hexlet@example.com"));
    }

    @Test
    public void testShow() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/tasks/" + testTask.getId()).with(token))
            .andExpect(status().isOk())
            .andReturn();

        String body = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        System.out.println(body);
        assertThatJson(body).and(
            a -> a.node("title").isEqualTo(testTask.getName()),
            a -> a.node("index").isEqualTo(testTask.getIndex()),
            a -> a.node("content").isEqualTo(testTask.getDescription())
        );

        mockMvc.perform(delete("/api/tasks/" + testTask.getId()).with(token));
        mockMvc.perform(get("/api/tasks/" + testTask.getId()).with(token))
            .andExpect(status().isNotFound());
    }

    @Test
    public void testCreate() throws Exception {
        Map<String, String> data = new HashMap<>(Map.of(
            "title", "newTitle", "content", "newContent", "status", testStatus.getSlug()));

        MockHttpServletRequestBuilder request = post("/api/tasks").with(token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(om.writeValueAsString(data));

        mockMvc.perform(request)
            .andExpect(status().isCreated());

        Task addedTask = taskRepository.findByName(data.get("title")).orElse(null);
        assertThat(addedTask).isNotNull();
        assertThat(addedTask.getName()).isEqualTo(data.get("title"));
        assertThat(addedTask.getDescription()).isEqualTo(data.get("content"));
        assertThat(addedTask.getTaskStatus()).isEqualTo(testStatus);
        assertThat(addedTask.getAssignee()).isNull();
    }

    @Test
    public void testUpdate() throws Exception {
        Map<String, String> data = new HashMap<>(Map.of("title", "newTitle"));

        MockHttpServletRequestBuilder request = put("/api/tasks/" + testTask.getId()).with(token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(om.writeValueAsString(data));

        mockMvc.perform(request)
            .andExpect(status().isOk());

        Task updatedTask = taskRepository.findById(testTask.getId()).orElse(null);
        assertThat(updatedTask).isNotNull();
        assertThat(updatedTask.getName()).isEqualTo("newTitle");
        assertThat(updatedTask.getIndex()).isEqualTo(testTask.getIndex());


        mockMvc.perform(delete("/api/tasks/" + testTask.getId()).with(token));
        mockMvc.perform(put("/api/tasks/" + testTask.getId()).with(token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(data)))
            .andExpect(status().isNotFound());
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete("/api/tasks/" + testTask.getId()).with(token))
            .andExpect(status().isNoContent());

        Task destroyedTask = taskRepository.findById(testTask.getId()).orElse(null);
        assertThat(destroyedTask).isNull();

    }
}
