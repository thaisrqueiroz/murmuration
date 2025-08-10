package project.murmuration.user.dto;

import project.murmuration.security.Role;

public record UserResponse(
        Long id,
        String username,
        String name,
        String email,
        Role role
) {
}