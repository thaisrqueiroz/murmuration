package project.murmuration.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import project.murmuration.security.CustomUserDetail;
import project.murmuration.security.Role;
import project.murmuration.user.dto.UserRequest;
import project.murmuration.user.dto.UserResponse;

import java.util.List;

@AllArgsConstructor
@RequestMapping("/api")
@RestController
@Tag(name = "User", description = "User management endpoints")
public class UserController {
    private final UserService userService;

    @GetMapping("/users")
    @Operation(summary = "Get all users (Admin only)",
            responses = {
                @ApiResponse(responseCode = "200", description = "Successfully retrieved list of users"),
                @ApiResponse(responseCode = "400", description = "Bad request"),
                @ApiResponse(responseCode = "401", description = "Unauthorized access"),
                @ApiResponse(responseCode = "403", description = "Forbidden access"),
                @ApiResponse(responseCode = "404", description = "User not found"),
                @ApiResponse(responseCode = "500", description = "Internal server error")
            })
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    @Operation(summary = "Get user by ID (Admin or the user themselves)",
            responses = {
                @ApiResponse(responseCode = "200", description = "Successfully retrieved the user"),
                @ApiResponse(responseCode = "400", description = "Bad request"),
                @ApiResponse(responseCode = "401", description = "Unauthorized access"),
                @ApiResponse(responseCode = "403", description = "Forbidden access"),
                @ApiResponse(responseCode = "404", description = "User not found"),
                @ApiResponse(responseCode = "500", description = "Internal server error")
            })
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetail currentUser) {
        if (currentUser.getUser().getRole() == Role.ADMIN || currentUser.getUser().getId().equals(id)) {
            UserResponse userResponse = userService.getUserResponseById(id);
            return new ResponseEntity<>(userResponse, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PutMapping("/users/{id}")
    @Operation(summary = "Update user information",
            responses = {
                @ApiResponse(responseCode = "200", description = "User updated successfully"),
                @ApiResponse(responseCode = "400", description = "Bad request"),
                @ApiResponse(responseCode = "401", description = "Unauthorized access"),
                @ApiResponse(responseCode = "403", description = "Forbidden access"),
                @ApiResponse(responseCode = "404", description = "User not found"),
                @ApiResponse(responseCode = "500", description = "Internal server error")
            })
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @Valid @RequestBody UserRequest userRequest) {
        UserResponse updatedUser = userService.updateUser(id, userRequest);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/users/{id}")
    @Operation(summary = "Delete a user (Admin only)",
            responses = {
                @ApiResponse(responseCode = "204", description = "User deleted successfully"),
                @ApiResponse(responseCode = "400", description = "Bad request"),
                @ApiResponse(responseCode = "401", description = "Unauthorized access"),
                @ApiResponse(responseCode = "403", description = "Forbidden access"),
                @ApiResponse(responseCode = "404", description = "User not found"),
                @ApiResponse(responseCode = "500", description = "Internal server error")
            })
    public ResponseEntity<Object> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}