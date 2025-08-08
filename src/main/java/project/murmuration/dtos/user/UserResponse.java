package project.murmuration.dtos.user;

public record UserResponse(
        Long id,
        String username,
        String name,
        String email
) {
}