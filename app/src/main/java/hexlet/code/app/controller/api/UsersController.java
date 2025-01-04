package hexlet.code.app.controller.api;

import hexlet.code.app.dto.UserCreateDTO;
import hexlet.code.app.dto.UserDTO;
import hexlet.code.app.dto.UserUpdateDTO;
import hexlet.code.app.exception.ResourceNotFoundException;
import hexlet.code.app.mapper.UserMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import hexlet.code.app.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("")
    ResponseEntity<List<UserDTO>> index() {
        var users = repository.findAll();
        var result = users.stream()
                .map(userMapper::map)
                .toList();
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(users.size()))
                .body(result);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    UserDTO create(@Valid @RequestBody UserCreateDTO userData) {
        var user = userMapper.map(userData);
        repository.save(user);
        return userMapper.map(user);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    UserDTO update(@RequestBody UserUpdateDTO userData, @PathVariable Long id) {
        var user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found"));
        userMapper.update(userData, user);
        repository.save(user);
        var userDTO = userMapper.map(user);
        return userDTO;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    UserDTO show(@PathVariable Long id) {
        var user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found"));
        return userMapper.map(user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void destroy(@PathVariable Long id) {
        repository.deleteById(id);
    }

}
