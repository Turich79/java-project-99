package hexlet.code.app.repository;

import hexlet.code.app.model.Task;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<Task> findByName(@NotBlank @Size(min = 1) String name);

//    Optional<Task> findBySlug(String slug);

}
