package project.murmuration.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import project.murmuration.user.dto.UserRequest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("tests")
@Sql(scripts = "/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Should return all users")
    @WithMockUser(username = "admin", roles = "ADMIN")
    void getAllUsers() throws Exception {
        mockMvc.perform(get("/api/users").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(9)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].username").value("admin"))
                .andExpect(jsonPath("$[0].email").value("admin@email.com"))
                .andExpect(jsonPath("$[0].role").value("ADMIN"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].username").value("rubens_garcia"))
                .andExpect(jsonPath("$[1].email").value("elrubens@email.com"))
                .andExpect(jsonPath("$[1].role").value("USER"));
    }

    @Test
    @DisplayName("Should return user by ID when user is ADMIN")
    @WithUserDetails("admin")
    void getUserByIdAsAdmin() throws Exception {

        mockMvc.perform(get("/api/users/{id}", 2).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("rubens_garcia"))
                .andExpect(jsonPath("$.email").value("elrubens@email.com"))
                .andExpect(jsonPath("$.role").value("USER"));
    }

    @Test
    @DisplayName("Should return user by ID when user requests their own data")
    @WithUserDetails("rubens_garcia")
    void getUserByIdAsUser() throws Exception {
        mockMvc.perform(get("/api/users/{id}", 2).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("rubens_garcia"))
                .andExpect(jsonPath("$.email").value("elrubens@email.com"));
    }

    @Test
    @DisplayName("Should return FORBIDDEN when user tries to access another user's data")
    @WithUserDetails("rubens_garcia")
    void getUserByIdForbidden() throws Exception {
        mockMvc.perform(get("/api/users/{id}", 3).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Should update user's data")
    @WithUserDetails("rubens_garcia")
    void updateUser() throws Exception {
        UserRequest updateRequest = new UserRequest("rubens_newUser", "Rubens Updated", "new@email.com", "newPassword#123", "Barcelona", "USER");

        String json = objectMapper.writeValueAsString(updateRequest);

        mockMvc.perform(put("/api/users/{id}", 2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("rubens_newUser"))
                .andExpect(jsonPath("$.name").value("Rubens Updated"))
                .andExpect(jsonPath("$.email").value("new@email.com"));
    }

    @Test
    @DisplayName("Should delete user by ID when user is ADMIN")
    @WithUserDetails("admin")
    void deleteUserAsAdmin() throws Exception {
        mockMvc.perform(delete("/api/users/{id}", 2))
                .andExpect(status().isNoContent());
        mockMvc.perform(get("/api/users/{id}", 2))
                .andExpect(status().isNotFound());
    }
}