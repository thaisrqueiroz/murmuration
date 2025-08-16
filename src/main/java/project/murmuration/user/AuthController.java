package project.murmuration.user;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.murmuration.security.CustomUserDetail;
import project.murmuration.security.jwt.JwtService;
import project.murmuration.user.dto.JwtResponse;
import project.murmuration.user.dto.UserRequest;
import project.murmuration.user.dto.UserResponse;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody UserRequest userRequest) {
        UserRequest userRequestWithRoleByDefault = new UserRequest(userRequest.username(), userRequest.name(), userRequest.email(), userRequest.password(), userRequest.location(), "USER");
        UserResponse userResponse = userService.addUser(userRequestWithRoleByDefault);
        return new ResponseEntity<>(userResponse,HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody UserRequest userRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userRequest.username(), userRequest.password()));

        CustomUserDetail userDetail = (CustomUserDetail) authentication.getPrincipal();
        String token = jwtService.generateToken(userDetail);

        JwtResponse jwtResponse = new JwtResponse(token);
        return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
    }
}