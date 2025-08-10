package project.murmuration.user.dto;

import project.murmuration.user.User;

public class UserMapper {
    public static User dtoToEntity(UserRequest dto){
        return new User(dto.username(), dto.name(), dto.password(), dto.location());
    }

    public static UserResponse entityToDto(User user){
        return new UserResponse (user.getId(), user.getUsername(), user.getName(), user.getEmail());
    }
}