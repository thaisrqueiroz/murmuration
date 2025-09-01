package project.murmuration.category;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import project.murmuration.category.dto.CategoryRequest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("tests")
@Sql(scripts = "/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class CategoryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Should return all categories when user is ADMIN")
    @WithUserDetails("admin")
    void getAllCategories_whenUserIsAdmin_returnsListOfCategories() throws Exception {
        mockMvc.perform(get("/api/categories").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", org.hamcrest.Matchers.hasSize(7)))
                .andExpect(jsonPath("$[0].name").value("Classes"))
                .andExpect(jsonPath("$[1].name").value("Electronics"))
                .andExpect(jsonPath("$[2].name").value("Exchange"));
    }

    @Test
    @DisplayName("Should return FORBIDDEN when user is not ADMIN trying to get all categories")
    @WithUserDetails("rubens_garcia")
    void getAllCategories_whenUserIsNotAdmin_returnsForbidden() throws Exception {
        mockMvc.perform(get("/api/categories").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Should create a new category when user is ADMIN")
    @WithUserDetails("admin")
    void addCategory_whenUserIsAdmin_createsCategory() throws Exception {
        CategoryRequest categoryRequest = new CategoryRequest("Clothes");

        mockMvc.perform(post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Clothes"));
    }

    @Test
    @DisplayName("Should return FORBIDDEN when user is not ADMIN trying to create a category")
    @WithUserDetails("rubens_garcia")
    void addCategory_whenUserIsNotAdmin_returnsForbidden() throws Exception {
        CategoryRequest categoryRequest = new CategoryRequest("Electronics");

        mockMvc.perform(post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryRequest)))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Should update a category when user is ADMIN")
    @WithUserDetails("admin")
    void updateCategory_whenUserIsAdmin_updatesCategory() throws Exception {
        CategoryRequest categoryRequest = new CategoryRequest("Updated Category");

        mockMvc.perform(put("/api/categories/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Category"));
    }

    @Test
    @DisplayName("Should return NOT FOUND when trying to update a non-existing category")
    @WithUserDetails("admin")
    void updateCategory_whenCategoryDoesNotExist_returnsNotFound() throws Exception {
        CategoryRequest categoryRequest = new CategoryRequest("Updated Category");

        mockMvc.perform(put("/api/categories/{id}", 99)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should return FORBIDDEN when user is not ADMIN trying to update a category")
    @WithUserDetails("rubens_garcia")
    void updateCategory_whenUserIsNotAdmin_returnsForbidden() throws Exception {
        CategoryRequest categoryRequest = new CategoryRequest("Updated Category");

        mockMvc.perform(put("/api/categories/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryRequest)))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Should delete a category when user is ADMIN")
    @WithUserDetails("admin")
    void deleteCategory_whenUserIsAdmin_deletesCategory() throws Exception {
        mockMvc.perform(delete("/api/categories/{id}", 7))
                .andExpect(status().isNoContent());
        mockMvc.perform(delete("/api/categories/{id}", 7))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should return NOT FOUND when trying to delete a non-existing category")
    @WithUserDetails("admin")
    void deleteCategory_whenCategoryDoesNotExist_returnsNotFound() throws Exception {
        mockMvc.perform(delete("/api/categories/{id}", 99))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should return FORBIDDEN when user is not ADMIN trying to delete a category")
    @WithUserDetails("rubens_garcia")
    void deleteCategory_whenUserIsNotAdmin_returnsForbidden() throws Exception {
        mockMvc.perform(delete("/api/categories/{id}", 1))
                .andExpect(status().isForbidden());
    }
}