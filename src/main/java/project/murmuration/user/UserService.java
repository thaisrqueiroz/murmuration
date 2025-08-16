package project.murmuration.user;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.murmuration.exceptions.EntityNotFoundException;
import project.murmuration.security.CustomUserDetail;
import project.murmuration.user.dto.UserMapper;
import project.murmuration.user.dto.UserResponse;

import java.util.List;

@AllArgsConstructor
@Service
public class UserService implements UserDetailsService {
    public final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws EntityNotFoundException {
        return userRepository.findByUsername(username)
                .map(CustomUserDetail::new)
                .orElseThrow(() -> new EntityNotFoundException(User.class.getSimpleName(), "username", username));
    }

    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(UserMapper::entityToDto).toList();
    }
}