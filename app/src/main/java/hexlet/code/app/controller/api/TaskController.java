package hexlet.code.app.controller.api;

import hexlet.code.app.dto.task.TaskCreateDTO;
import hexlet.code.app.dto.task.TaskDTO;
//import hexlet.code.app.dto.task.TaskParamsDTO;
import hexlet.code.app.dto.task.TaskUpdateDTO;
//import hexlet.code.app.dto.taskStatus.TaskStatusCreateDTO;
//import hexlet.code.app.dto.taskStatus.TaskStatusDTO;
//import hexlet.code.app.dto.taskStatus.TaskStatusUpdateDTO;
//import hexlet.code.app.exception.ResourceNotFoundException;
import hexlet.code.app.mapper.TaskMapper;
import hexlet.code.app.repository.TaskRepository;

import hexlet.code.app.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RestController
@RequestMapping(path = "/api/tasks")
public class TaskController {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private TaskService taskService;

    @GetMapping("")
//    public ResponseEntity<List<TaskDTO>> index(TaskParamsDTO params) {
    public List<TaskDTO> index() {
        var tasks = taskRepository.findAll();
        var result = tasks.stream()
            .map(taskMapper::map)
            .toList();

        return result;
//        var taskDTOList = taskService.getAll(params);
//        return ResponseEntity.ok()
//            .header("X-Total-Count", String.valueOf(taskDTOList.size()))
//            .body(taskDTOList);
    }

    @GetMapping("/{id}")
    public TaskDTO show(@PathVariable Long id) {
        return taskService.getById(id);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDTO create(
        @Valid @RequestBody TaskCreateDTO data) {
        return taskService.create(data);
    }

    @PutMapping("/{id}")
    public TaskDTO update(
        @PathVariable Long id,
        @Valid @RequestBody TaskUpdateDTO data) {
        return taskService.update(id, data);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        taskService.delete(id);
    }
}
//import jakarta.validation.Valid;
//import lombok.AllArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/tasks")
//@AllArgsConstructor
//public class TaskController {
//    @Autowired
//    private TaskRepository taskRepository;
//
//    @Autowired
//    private TaskMapper taskMapper;
//
//    @GetMapping(path = "")
//    @ResponseStatus(HttpStatus.OK)
//    public List<TaskDTO> index() {
//        var tasks = taskRepository.findAll();
//        var result = tasks.stream()
//            .map(taskMapper::map)
//            .toList();
//
//        return result;
//    }
//
//    @PostMapping("")
//    @ResponseStatus(HttpStatus.CREATED)
//    public TaskDTO create(@Valid @RequestBody TaskCreateDTO taskCreateDTO) {
//        var task = taskMapper.map(taskCreateDTO);
//        taskRepository.save(task);
//        var taskDTO = taskMapper.map(task);
//        return taskDTO;
//    }
//
//    @GetMapping("/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public TaskDTO show(@PathVariable Long id) {
//        var task = taskRepository.findById(id)
//            .orElseThrow(() -> new ResourceNotFoundException("Not Found: " + id));
//        var taskDTO = taskMapper.map(task);
//        return taskDTO;
//    }
//
//    @PutMapping("/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public TaskDTO update(@RequestBody @Valid TaskUpdateDTO taskUpdateDTO, @PathVariable Long id) {
//        var task = taskRepository.findById(id)
//            .orElseThrow(() -> new ResourceNotFoundException("Not Found: " + id));
//        taskMapper.update(taskUpdateDTO, task);
//        taskRepository.save(task);
//        var taskDTO = taskMapper.map(task);
//        return taskDTO;
//    }
//
//    @DeleteMapping("/{id}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void delete(@PathVariable Long id) {
//        taskRepository.deleteById(id);
//    }
//}
