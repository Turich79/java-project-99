//package hexlet.code.app.service;
//
//import hexlet.code.app.dto.UserCreateDTO;
//import hexlet.code.app.dto.UserDTO;
//import hexlet.code.app.mapper.UserMapper;
//import hexlet.code.app.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class UserService {
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private UserMapper userMapper;
//
//    public UserDTO create(UserCreateDTO userData) {
//        var user = userMapper.map(userData);
//        userRepository.save(user);
//        var userDTO = userMapper.map(user);
//        return userDTO;
//    }
//}
