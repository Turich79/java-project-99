package hexlet.code.app.dto.taskStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

@Getter
@Setter
public class TaskStatusUpdateDTO {
    @NotBlank
    @Size(min = 1)
    private JsonNullable<String> name;

    //по теории это запрещено, но на всякий сделаю
    @NotBlank
    @Size(min = 1)
    private JsonNullable<String> slug;
}
