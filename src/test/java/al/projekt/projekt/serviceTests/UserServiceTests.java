package al.projekt.projekt.serviceTests;

import al.projekt.projekt.dto.UserDTO;
import al.projekt.projekt.model.User;
import al.projekt.projekt.repository.RoleRepository;
import al.projekt.projekt.repository.UserRepository;
import al.projekt.projekt.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
public class UserServiceTests {

    @Autowired
    UserService userService;

    @Autowired
    RoleRepository roleRepository;

    @MockBean
    UserRepository userRepository;


    private List<User> createUser() {
        List<User> mockUsers = new ArrayList<>();

        User user1 = new User();

        user1.setUsername("ledjon");
        user1.setPassword("password");
        user1.setEmail("ledjon@gmail.com");
        user1.setName("ledjon");
        user1.setAge(22);
        user1.setGender("male");
        user1.setRoles(new HashSet<>(Arrays.asList(roleRepository.getOne(1L))));

        User user2 = new User();

        user2.setUsername("ledjon1");
        user2.setPassword("password");
        user2.setEmail("ledjon1@gmail.com");
        user2.setName("ledjon");
        user2.setAge(22);
        user2.setGender("male");
        user2.setRoles(new HashSet<>(Arrays.asList(roleRepository.getOne(1L))));

        User user3 = new User();

        user3.setUsername("ledjon2");
        user3.setPassword("password");
        user3.setEmail("ledjon2@gmail.com");
        user3.setName("ledjon");
        user3.setAge(22);
        user3.setGender("male");
        user3.setRoles(new HashSet<>(Arrays.asList(roleRepository.getOne(1L))));

        mockUsers.add(user1);
        mockUsers.add(user2);
        mockUsers.add(user3);
        return mockUsers;
    }

    private User createUserByDTO(UserDTO userDTO) {
        User user = new User();

        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        user.setName(userDTO.getName());
        user.setAge(userDTO.getAge());
        user.setGender(userDTO.getGender());

        return user;
    }

    @Test
    public void findAllTest() {
        when(userRepository.findAll()).thenReturn(createUser());
        assertEquals(3, userService.findAll().size());
    }

    @Test
    public void signUpTest() {
        UserDTO userDTO = new UserDTO("ledjon5d", "pass", "email2@gmail.com", "name", 22, "male", "");
        User user = createUserByDTO(userDTO);
        user.setRoles(new HashSet<>(Arrays.asList(roleRepository.getOne(1L))));

        when(userRepository.findByUsername("ledjon5d")).thenReturn(null);
        when(userRepository.findByEmail("email2@gmail.com")).thenReturn(null);
        when(userRepository.save(user)).thenReturn(user);

        assertEquals(user, userService.signUp(userDTO));
    }

    @Test
    public void addNewUserTest() {
        UserDTO userDTO = new UserDTO("ledjon5", "pass", "email@gmail.com", "name", 22, "male", "ROLE_ADMIN");
        User user = createUserByDTO(userDTO);

        if (userDTO.getRole().equals("ROLE_USER")) {
            user.setRoles(new HashSet<>(Arrays.asList(roleRepository.getOne(1L))));
        } else {
            user.setRoles(new HashSet<>(Arrays.asList(roleRepository.getOne(2L))));
        }

        when(userRepository.findByUsername(userDTO.getUsername())).thenReturn(null);
        when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(null);
        when(userRepository.save(user)).thenReturn(user);

        assertEquals(user, userService.addNewUser(userDTO));
    }

    @Test
    public void updateUserTest() {
        UserDTO updatedUserDTO = new UserDTO("updated", "update", "emailup@gmail.com", "name", 32, "male", "ROLE_ADMIN");
        User userUpdate = createUserByDTO(updatedUserDTO);

        if (updatedUserDTO.getRole().equals("ROLE_USER")) {
            userUpdate.setRoles(new HashSet<>(Arrays.asList(roleRepository.getOne(1L))));
        } else {
            userUpdate.setRoles(new HashSet<>(Arrays.asList(roleRepository.getOne(2L))));
        }

        when(userRepository.findById(1L)).thenReturn(Optional.of(createUser().get(0)));
        when(userRepository.save(userUpdate)).thenReturn(userUpdate);
        assertEquals(userUpdate, userService.updateUser(updatedUserDTO, 1L));
    }

    @Test
    public void deleteUserTest() {
        User user = createUser().get(0);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        assertNotEquals(null, userService.deleteUser(1L).getTo_date());
    }

    @Test
    public void getNameByUsernameTest() {
        User user = createUser().get(0);

        when(userRepository.findByUsername("ledjon")).thenReturn(user);
        assertNotEquals(user, userService.getNameByUsername("ledjon"));
    }
}
