package project.murmuration.user.dto;

public record UserResponse(
        Long id,
        String username,
        String name,
        String email
) {
}