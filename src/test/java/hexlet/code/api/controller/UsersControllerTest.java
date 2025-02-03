package hexlet.code.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.model.User;
import hexlet.code.repository.UserRepository;
import hexlet.code.util.ModelGenerator;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

import net.datafaker.Faker;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;

import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.instancio.Instancio;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
class UsersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Faker faker;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelGenerator modelGenerator;

    @Autowired
    private ObjectMapper om;

    private JwtRequestPostProcessor token;

    private User testUser;

    @BeforeEach
    public void setUp() {
        token = jwt().jwt(builder -> builder.subject("hexlet@example.com"));
        testUser = Instancio.of(modelGenerator.getUserModel())
            .create();
    }

    @Test
    public void testIndex() throws Exception {
        userRepository.save(testUser);
        var result = mockMvc.perform(get("/api/users").with(jwt()))
            .andExpect(status().isOk())
            .andReturn();

        var body = result.getResponse().getContentAsString();
        assertThatJson(body).isArray();
    }

    @Test
    public void testShow() throws Exception {
        userRepository.save(testUser);

        var request = get("/api/users/{id}", testUser.getId()).with(jwt());
        var result = mockMvc.perform(request)
            .andExpect(status().isOk())
            .andReturn();
        var body = result.getResponse().getContentAsString();

        assertThatJson(body).and(
            v -> v.node("firstName").isEqualTo(testUser.getFirstName()),
            v -> v.node("lastName").isEqualTo(testUser.getLastName()),
            v -> v.node("email").isEqualTo(testUser.getEmail())
        );
    }

    @Test
    public void testIndexWithoutAuth() throws Exception {
        userRepository.save(testUser);
        var result = mockMvc.perform(get("/api/users"))
            .andExpect(status().isUnauthorized());

    }

    @Test
    public void testCreate() throws Exception {
        var request = post("/api/users").with(jwt())
            .contentType(APPLICATION_JSON)
            .content(om.writeValueAsString(testUser));

        mockMvc.perform(request)
            .andExpect(status().isCreated());

        var user = userRepository.findByEmail(testUser.getEmail()).get();

        assertNotNull(user);
        assertEquals(user.getFirstName(), testUser.getFirstName());
        assertEquals(user.getLastName(), testUser.getLastName());
        assertEquals(user.getEmail(), testUser.getEmail());
        assertNotEquals(user.getPasswordDigest(), testUser.getPasswordDigest());
    }

    @Test
    void testCreate2() throws Exception {
        var data = Instancio.of(modelGenerator.getUserModel())
            .create();

        var request = post("/api/users")
            .with(token)
            .contentType(APPLICATION_JSON)
            .content(om.writeValueAsString(data));
        var result = mockMvc
            .perform(request)
            .andExpect(status().isCreated())
            .andReturn();
        var body = result.getResponse().getContentAsString();

        assertThatJson(body).and(
            v -> v.node("password").isAbsent(),
            v -> v.node("id").isPresent(),
            v -> v.node("firstName").isEqualTo(data.getFirstName()),
            v -> v.node("lastName").isEqualTo(data.getLastName()),
            v -> v.node("email").isEqualTo(data.getEmail()),
            v -> v.node("createdAt").isPresent()
        );
    }


    @Test
    public void testShowWithoutAuth() throws Exception {

        userRepository.save(testUser);

        var request = get("/api/users/{id}", testUser.getId());
        mockMvc.perform(request)
            .andExpect(status().isUnauthorized());
    }
}
