package project.murmuration.user;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.murmuration.user.dto.UserRequest;
import project.murmuration.user.dto.UserResponse;

import java.util.List;

@AllArgsConstructor
@RequestMapping("/api")
@RestController
public class UserController {
    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        UserResponse userResponse = userService.getUserResponseById(id);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @Valid @RequestBody UserRequest userRequest) {
        UserResponse updatedUser = userService.updateUser(id, userRequest);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}