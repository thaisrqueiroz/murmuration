package project.murmuration.user.dto;

import project.murmuration.security.Role;
import project.murmuration.user.User;

public class UserMapper {
    public static User dtoToEntity(UserRequest dto, Role role){
        return new User(dto.username(), dto.name(), dto.password(), role, dto.location());
    }

    public static UserResponse entityToDto(User user){
        return new UserResponse (user.getId(), user.getUsername(), user.getName(), user.getEmail(), user.getRole());
    }
}