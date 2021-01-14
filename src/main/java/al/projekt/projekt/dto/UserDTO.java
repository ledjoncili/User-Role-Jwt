package al.projekt.projekt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data @AllArgsConstructor @NoArgsConstructor
public class UserDTO implements Serializable {
    private String username;
    private String password;
    private String email;
    private String name;
    private int age;
    private String gender;
    private String role;
}

