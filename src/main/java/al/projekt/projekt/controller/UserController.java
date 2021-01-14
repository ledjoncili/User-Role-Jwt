package al.projekt.projekt.controller;

import al.projekt.projekt.dto.UserDTO;
import al.projekt.projekt.exception.AppException;
import al.projekt.projekt.model.AuthenticationRequest;
import al.projekt.projekt.model.AuthenticationResponse;
import al.projekt.projekt.model.User;
import al.projekt.projekt.service.MyUserDetailsService;
import al.projekt.projekt.service.UserService;
import al.projekt.projekt.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    MyUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    //GET ALL USERS
    @GetMapping("/users")
    public List<User> getUsers() {
        return userService.findAll();
    }

    //USER SIGN UP
    @PostMapping("/users/signup")
    public User addUser(@RequestBody UserDTO userDTO) {
        return userService.signUp(userDTO);
    }

    //ADD USER BY ADMIN
    @PostMapping("/users/add_user")
    public User addUserFromAdmin(@RequestBody UserDTO userDTO) {
        return userService.addNewUser(userDTO);
    }

    //UPDATE USER BY ADMIN
    @RequestMapping(value = "/users/update_user/{userId}", method = RequestMethod.PUT)
    public User updateUser(@RequestBody UserDTO userDTO,
                           @PathVariable Long userId) {
        return userService.updateUser(userDTO, userId);
    }

    //DELETE USER BY ADMIN
    @RequestMapping(value = "/users/delete_user/{userId}", method = RequestMethod.DELETE)
    public User deleteUser(@PathVariable Long userId) {
        return userService.deleteUser(userId);
    }

    //AUTHENTICATE USER
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
            throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                            authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e) {

            throw new AppException("Incorrect username or password");
        }
        final UserDetails userDetails = userDetailsService.
                loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtTokenUtil.generateToken(userDetails);

        log.info("User: " + authenticationRequest.getUsername() + " was successfully logged in!");
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}
