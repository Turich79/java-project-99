//package hexlet.code.app.controller.api;
//
//import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
////import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import aj.org.objectweb.asm.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import hexlet.code.app.dto.UserDTO;
//import hexlet.code.app.mapper.UserMapper;
//import hexlet.code.app.model.User;
//import hexlet.code.app.repository.UserRepository;
//import hexlet.code.app.util.ModelGenerator;
//import net.datafaker.Faker;
//import org.assertj.core.api.Assertions;
//import org.instancio.Instancio;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import java.nio.charset.StandardCharsets;
//import java.util.HashMap;
//import java.util.List;
//
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class UsersControllerTest_token {
//
//    @Autowired
//    private WebApplicationContext wac;
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private Faker faker;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private ModelGenerator modelGenerator;
//
//    @Autowired
//    private ObjectMapper om;
//
//    @Autowired
//    private UserMapper userMapper;
//
////    private JwtRequestPostProcessor token;
//
//    private User testUser;
//
//
//    @BeforeEach
//    public void setUp() {
//        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
//                .defaultResponseCharacterEncoding(StandardCharsets.UTF_8)
////                .apply(springSecurity())
//                .build();
//
////        token = jwt().jwt(builder -> builder.subject("hexlet@example.com"));
//
//        testUser = Instancio.of(modelGenerator.getUserModel())
//                .create();
//        userRepository.save(testUser);
//    }
//
////    @Test
////    public void testIndex() throws Exception {
////        var response = mockMvc.perform(get("/api/users").with(jwt()))
////                .andExpect(status().isOk())
////                .andReturn()
////                .getResponse();
////        var body = response.getContentAsString();
////
////        List<UserDTO> userDTOS = om.readValue(body, new TypeReference<>() {});
////
////        var actual = userDTOS.stream().map(userMapper::map).toList();
////        var expected = userRepository.findAll();
////        Assertions.assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
////    }
//
////    @Test
////    public void testCreate() throws Exception {
////        var data = Instancio.of(modelGenerator.getUserModel())
////                .create();
////
////        var request = post("/api/users")
////                .with(token)
////                .contentType(MediaType.APPLICATION_JSON)
////                .content(om.writeValueAsString(data));
////        mockMvc.perform(request)
////                .andExpect(status().isCreated());
////
////        var user = userRepository.findByEmail(data.getEmail()).get();
////
////        assertNotNull(user);
////        assertThat(user.getFirstName()).isEqualTo(data.getFirstName());
////        assertThat(user.getLastName()).isEqualTo(data.getLastName());
////    }
////
////    @Test
////    public void testUpdate() throws Exception {
////
////        var data = new HashMap<>();
////        data.put("firstName", "Mike");
////
////        var request = put("/api/users/" + testUser.getId())
////                .with(token)
////                .contentType(MediaType.APPLICATION_JSON)
////                .content(om.writeValueAsString(data));
////
////        mockMvc.perform(request)
////                .andExpect(status().isOk());
////
////        var user = userRepository.findById(testUser.getId()).get();
////        assertThat(user.getFirstName()).isEqualTo(("Mike"));
////    }
////
////    @Test
////    public void testShow() throws Exception {
////        var request = get("/api/users/" + testUser.getId()).with(jwt());
////        var result = mockMvc.perform(request)
////                .andExpect(status().isOk())
////                .andReturn();
////        var body = result.getResponse().getContentAsString();
////        assertThatJson(body).and(
////                v -> v.node("username").isEqualTo(testUser.getEmail()),
////                v -> v.node("firstName").isEqualTo(testUser.getFirstName()),
////                v -> v.node("lastName").isEqualTo(testUser.getLastName()));
////    }
//}
