package hexlet.code.app.dto;

import lombok.Getter;
import lombok.Setter;

//import java.time.LocalDate;

@Setter
@Getter

public class UserDTO {
    private Long id;

    private String username;

    private String firstName;

    private String lastName;

    private String password;

}
