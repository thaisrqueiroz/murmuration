package project.murmuration.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import project.murmuration.security.CustomUserDetail;
import project.murmuration.user.User;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static project.murmuration.security.Role.USER;

@ExtendWith(MockitoExtension.class)
public class JwtAuthFilterTest {
    @Mock
    private JwtService jwtService;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private JwtAuthFilter jwtAuthFilter;

    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setName("Test User");
        user.setEmail("test@email.com");
        user.setRole(USER);

        userDetails = new CustomUserDetail(user);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    @DisplayName("Should authenticate user when valid token is provided")
    void doFilterInternal_whenValidToken_authenticatesUser() throws ServletException, IOException {
        String token = "validtoken";
        String authHeader = "Bearer " + token;

        when(request.getServletPath()).thenReturn("/api/offers");
        when(request.getHeader("Authorization")).thenReturn(authHeader);
        when(jwtService.isValidToken(token)).thenReturn(true);
        when(jwtService.extractUsername(token)).thenReturn("testuser");
        when(jwtService.extractRole(token)).thenReturn("ROLE_USER");
        when(userDetailsService.loadUserByUsername("testuser")).thenReturn(userDetails);

        jwtAuthFilter.doFilterInternal(request, response, filterChain);

        verify(jwtService, times(1)).isValidToken(token);
        verify(jwtService, times(1)).extractUsername(token);
        verify(jwtService, times(1)).extractRole(token);
        verify(userDetailsService, times(1)).loadUserByUsername("testuser");
        verify(securityContext, times(1)).setAuthentication(any(Authentication.class));
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    @DisplayName("Should not authenticate user with invalid token")
    void doFilterInternal_whenInvalidToken_doesNotAuthenticateUser() throws ServletException, IOException {
        String token = "invalidtoken";
        String authHeader = "Bearer " + token;

        when(request.getServletPath()).thenReturn("/api/offers");
        when(request.getHeader("Authorization")).thenReturn(authHeader);
        when(jwtService.isValidToken(token)).thenReturn(false);

        jwtAuthFilter.doFilterInternal(request, response, filterChain);

        verify(jwtService, times(1)).isValidToken(token);
        verify(securityContext, never()).setAuthentication(any(Authentication.class));
        verify(filterChain, times(1)).doFilter(request, response);
    }
}