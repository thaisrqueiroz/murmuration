package project.murmuration.security.jwt;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import project.murmuration.security.CustomUserDetail;
import project.murmuration.user.User;

import javax.crypto.SecretKey;
import java.util.Base64;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static project.murmuration.security.Role.USER;

@ExtendWith(MockitoExtension.class)
public class JwtServiceTest {
    @InjectMocks
    private JwtService jwtService;

    private CustomUserDetail customUserDetail;
    private String testSecretKey;

    @BeforeEach
    void setUp() {
        SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        testSecretKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());

        ReflectionTestUtils.setField(jwtService, "JWT_SECRET_KEY", testSecretKey);
        ReflectionTestUtils.setField(jwtService, "JWT_EXPIRATION_TIME", 3600000L);

        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setName("Test User");
        user.setEmail("test@email.com");
        user.setRole(USER);

        customUserDetail = new CustomUserDetail(user);
    }

    @Test
    @DisplayName("Should generate a valid JWT token")
    void generateToken_whenValidUser_returnsValidToken() {
        String token = jwtService.generateToken(customUserDetail);

        assertThat(token).isNotNull();
        assertThat(token).isNotEmpty();
        assertThat(token.split("\\.")).hasSize(3);
    }

    @Test
    @DisplayName("Should extract username from token")
    void extractUsername_whenValidToken_returnsUsername() {
        String token = jwtService.generateToken(customUserDetail);
        String username = jwtService.extractUsername(token);

        assertThat(username).isEqualTo(customUserDetail.getUsername());
    }

    @Test
    @DisplayName("Should validate valid token")
    void isValidToken_whenValidToken_returnsTrue() {
        String token = jwtService.generateToken(customUserDetail);
        boolean isValid = jwtService.isValidToken(token);

        assertThat(isValid).isTrue();
    }
}