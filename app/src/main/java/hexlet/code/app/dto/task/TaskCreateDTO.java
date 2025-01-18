package hexlet.code.app.dto.task;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

//import java.util.ArrayList;
//import java.util.List;

@Getter
@Setter
public class TaskCreateDTO {
    @NotBlank
    private String title;

    private Integer index;

    private String content;

    @NotNull
    private String status;

    private Long assigneeId;

//    private List<Long> taskLabelIds = new ArrayList<>();
}
