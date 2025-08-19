package project.murmuration.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import project.murmuration.user.dto.UserRequest;
import project.murmuration.user.dto.UserResponse;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static project.murmuration.security.Role.USER;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;
    private User user;
    private UserResponse userResponse;
    private UserRequest userRequest;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("thais");
        user.setName("Thais");
        user.setEmail("thais@email.com");
        user.setRole(USER);

        userResponse = new UserResponse(1L, "thais", "Thais", "thais@email.com", USER);

        userRequest = new UserRequest("thais", "Thais", "thais@email.com", "Str0ngP@ssw0rd", "Valencia", "ROLE_USER");
    }

    @Test
    @DisplayName("Should return all users")
    void getAllUsers() {
        when(userRepository.findAll()).thenReturn(List.of(user));

        List<UserResponse> result = userService.getAllUsers();

        assertThat(result).hasSize(1).containsExactly(userResponse);
        verify(userRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return user by ID")
    void getUserResponseById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserResponse result = userService.getUserResponseById(1L);

        assertThat(result).isEqualTo(userResponse);
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should encrypts the password, verifies that the username does not already exist, save the new user and returns a response object")
    void addUser() {
        when(passwordEncoder.encode(userRequest.password())).thenReturn("encodedPassword");
        when(userRepository.existsByUsername(userRequest.username())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserResponse result = userService.addUser(userRequest);

        assertThat(result).isEqualTo(userResponse);
        verify(passwordEncoder, times(1)).encode(userRequest.password());
        verify(userRepository, times(1)).existsByUsername(userRequest.username());
        verify(userRepository, times(1)).save(any(User.class));
    }
}