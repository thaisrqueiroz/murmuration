package project.murmuration.user.dto;

import project.murmuration.security.Role;
import project.murmuration.user.User;

public class UserMapper {
    public static User dtoToEntity(UserRequest userRequest){
        Role role = Role.USER;

        if (userRequest.role() != null && !userRequest.role().isEmpty()) {
            String roleName = userRequest.role();

            if (roleName.startsWith("ROLE_")) {
                roleName = roleName.substring(5);
            }

            try {
                role = Role.valueOf(roleName.toUpperCase());
            } catch (IllegalArgumentException exception) {
                role = Role.USER;
            }
        }

        return new User(userRequest.username(), userRequest.name(), userRequest.email(), userRequest.password(), role, userRequest.location());
    }

    public static UserResponse entityToDto(User user){
        return new UserResponse (user.getId(), user.getUsername(), user.getName(), user.getEmail(), user.getRole());
    }
}