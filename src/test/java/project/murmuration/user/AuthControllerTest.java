package project.murmuration.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import project.murmuration.user.dto.UserRequest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("tests")
@Sql(scripts = "/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Should register a new user successfully")
    void registerUser_whenValidUserRequest_returnsCreatedUser() throws Exception {
        UserRequest userRequest = new UserRequest("newuser", "New User", "newuser@email.com", "Str0ngP@ssw0rd", "Valencia", null);

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("newuser"))
                .andExpect(jsonPath("$.name").value("New User"))
                .andExpect(jsonPath("$.email").value("newuser@email.com"));
    }

    @Test
    @DisplayName("Should fail to register a user with existing username")
    void registerUser_whenUsernameExists_returnsConflict() throws Exception {
        UserRequest userRequest = new UserRequest("rubens_garcia", "Another User", "another@email.com", "Str0ngP@ssw0rd", "Valencia", null);

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("User with username rubens_garcia already exists"));
    }

    @Test
    @DisplayName("Should fail to register a user with weak password")
    void registerUser_whenWeakPassword_returnsBadRequest() throws Exception {
        UserRequest userRequest = new UserRequest("weakpassworduser", "Weak Password User", "weakpassword@email.com", "weakpass", "Valencia", null);

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should login an existing user successfully")
    void loginUser_whenValidCredentials_returnsJwtToken() throws Exception {
        UserRequest loginRequest = new UserRequest("admin", null, null, "Str0ngP@ssw0rd", null, null);

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.token").isNotEmpty());
    }

    @Test
    @DisplayName("Should fail to login with invalid credentials")
    void loginUser_whenInvalidCredentials_returnsUnauthorized() throws Exception {
        UserRequest loginRequest = new UserRequest("admin", null, null, "WrongPassword", null, null);

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized());
    }
}