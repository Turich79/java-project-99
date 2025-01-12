package hexlet.code.app.service;

import hexlet.code.app.dto.taskStatus.TaskStatusCreateDTO;
import hexlet.code.app.dto.taskStatus.TaskStatusDTO;
import hexlet.code.app.dto.taskStatus.TaskStatusUpdateDTO;
import hexlet.code.app.exception.ResourceNotFoundException;
import hexlet.code.app.mapper.TaskStatusMapper;
import hexlet.code.app.repository.TaskStatusRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@AllArgsConstructor
public class TaskStatusService {

//    private final TaskStatusRepository taskStatusRepository;
//    private final TaskStatusMapper taskStatusMapper;
//
//
//    public TaskStatusDTO create(TaskStatusCreateDTO taskStatusCreateDTO) {
//        var taskStatus = taskStatusMapper.map(taskStatusCreateDTO);
//        taskStatusRepository.save(taskStatus);
//
//        return taskStatusMapper.map(taskStatus);
//    }
//
//    public List<TaskStatusDTO> getAll() {
//        var taskStatuses = taskStatusRepository.findAll();
//
//        return taskStatuses.stream()
//            .map(taskStatusMapper::map)
//            .toList();
//    }
//
//    public TaskStatusDTO findById(Long id) {
//        var taskStatus = taskStatusRepository.findByIdWithEagerUpload(id)
//            .orElseThrow(() -> new ResourceNotFoundException("TaskStatus With Id: " + id + " Not Found"));
//
//        return taskStatusMapper.map(taskStatus);
//    }
//
//    public TaskStatusDTO update(TaskStatusUpdateDTO taskStatusUpdateDTO, Long id) {
//        var taskStatus = taskStatusRepository.findByIdWithEagerUpload(id)
//            .orElseThrow(() -> new ResourceNotFoundException("TaskStatus With Id: " + id + " Not Found"));
//
//        taskStatusMapper.update(taskStatusUpdateDTO, taskStatus);
//        taskStatusRepository.save(taskStatus);
//
//        return taskStatusMapper.map(taskStatus);
//    }
//
//    public void delete(Long id) throws Exception {
//        taskStatusRepository.deleteById(id);
//    }
}
