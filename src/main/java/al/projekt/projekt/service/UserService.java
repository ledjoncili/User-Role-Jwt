package al.projekt.projekt.service;

import al.projekt.projekt.dto.UserDTO;
import al.projekt.projekt.exception.AppException;
import al.projekt.projekt.model.Roles;
import al.projekt.projekt.model.User;
import al.projekt.projekt.repository.RoleRepository;
import al.projekt.projekt.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;


    public List<User> findAll() {
        log.info("Getting all users!");
        return userRepository.findAll();
    }


    public User signUp(UserDTO userDTO) {
        User user = new User();
        verifyUserDoesntExist(userDTO);

        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        user.setName(userDTO.getName());
        user.setAge(userDTO.getAge());
        user.setGender(userDTO.getGender());

        verifyRoles();
        user.setRoles(new HashSet<>(Arrays.asList(roleRepository.getOne(1L))));

        log.info("You have successfully created an accounted with username: " + user.getUsername() + "!");
        return userRepository.save(user);
    }

    public User addNewUser(UserDTO userDTO) {
        User user = new User();
        verifyUserDoesntExist(userDTO);

        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        user.setName(userDTO.getName());
        user.setAge(userDTO.getAge());
        user.setGender(userDTO.getGender());

        if (userDTO.getRole().equals("ROLE_USER")) {
            user.setRoles(new HashSet<>(Arrays.asList(roleRepository.getOne(1L))));
        } else {
            user.setRoles(new HashSet<>(Arrays.asList(roleRepository.getOne(2L))));
        }

        log.info("User with username "+ user.getUsername() + " was created by admin!");
        return userRepository.save(user);
    }

    private void verifyRoles() {
        if (!roleRepository.findById(1L).isPresent()) {
            roleRepository.save(new Roles("ROLE_USER"));
        }
        if (!roleRepository.findById(2L).isPresent()) {
            roleRepository.save(new Roles("ROLE_ADMIN"));
        }
    }

    private void verifyUserDoesntExist(UserDTO userDTO){
        if (userRepository.findByUsername(userDTO.getUsername()) != null) {
            throw new AppException("This Username has been taken");
        }

        if (userRepository.findByEmail(userDTO.getEmail()) != null) {
            throw new AppException("This email has already been registered");
        }
    }

    public User updateUser(UserDTO userDTO, Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        verifyUserDoesntExist(userDTO);

        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        user.setName(userDTO.getName());
        user.setAge(userDTO.getAge());
        user.setGender(userDTO.getGender());

        if (userDTO.getRole().equals("ROLE_USER")) {
            user.setRoles(new HashSet<>(Arrays.asList(roleRepository.getOne(1L))));
        } else {
            user.setRoles(new HashSet<>(Arrays.asList(roleRepository.getOne(2L))));
        }

        log.info("User with id: " + user.getUserId() + " was successfully updated!");
        return userRepository.save(user);
    }

    public User deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElse(null);

        user.setTo_date(new Date());
        log.info("User with username: " + user.getUsername() + " was deleted!");
        return userRepository.save(user);
    }

    public String getNameByUsername(String username) {
        return userRepository.findByUsername(username).getName();
    }
}
