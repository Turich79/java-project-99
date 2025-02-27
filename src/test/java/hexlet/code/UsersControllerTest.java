package hexlet.code;

import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.dto.UserDTO;
import hexlet.code.model.User;
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
public final class UsersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelGenerator modelGenerator;

    @Autowired
    private ObjectMapper om;

    private User testUser;

    private JwtRequestPostProcessor token;

    @BeforeEach
    public void setUp() {
        testUser = Instancio.of(modelGenerator.getUserModel()).create();
        userRepository.save(testUser);
        token = jwt().jwt(builder -> builder.subject(testUser.getEmail()));
    }

    @AfterEach
    public void clear() {
        userRepository.deleteAll();
    }

    @Test
    public void testGetAll() throws Exception {
        var request = get("/api/users").with(token);
        mockMvc.perform(request)
            .andExpect(status().isOk());
    }

    @Test
    public void testGetById() throws Exception {
        var userId = testUser.getId();
        var request = get("/api/users/" + userId).with(token);
        mockMvc.perform(request)
            .andExpect(status().isOk());
    }

    @Test
    public void testCreate() throws Exception {
        var userDTO = Instancio.of(modelGenerator.getUserDTOModel()).create();

        var request = post("/api/users").with(token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(om.writeValueAsString(userDTO));

        mockMvc.perform(request)
            .andExpect(status().isCreated());

        var user = userRepository.findByEmail(userDTO.getEmail()).get();
        assertThat(user.getId()).isNotNull();
        assertThat(user.getCreatedAt()).isBeforeOrEqualTo(LocalDate.now());
        assertThat(user.getPassword()).doesNotMatch(userDTO.getPassword());
        assertThat(user.getLastName()).isEqualTo(userDTO.getLastName());
    }

    @Test
    public void testUpdate() throws Exception {
        var userId = testUser.getId();
        var data = new UserDTO();
        data.setFirstName("Gregory");

        var request = put("/api/users/" + userId).with(token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(om.writeValueAsString(data));

        mockMvc.perform(request)
            .andExpect(status().isOk());

        var user = userRepository.findByEmail(testUser.getEmail()).get();
        assertThat(user.getId()).isNotNull();
        assertThat(user.getFirstName()).isEqualTo("Gregory");
    }

    @Test
    public void testDelete() throws Exception {
        var userId = testUser.getId();
        var request = delete("/api/users/" + userId).with(token);
        mockMvc.perform(request)
            .andExpect(status().isNoContent());
    }
}
