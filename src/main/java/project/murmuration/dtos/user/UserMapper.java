package project.murmuration.dtos.user;

import project.murmuration.models.User;

public class UserMapper {
    static User dtoToEntity(UserRequest dto){
        return new User(dto.username(), dto.name(), dto.password(), dto.location());
    }

    static UserResponse entityToDto(User user){
        return new UserResponse (user.getId(), user.getUsername(), user.getName(), user.getEmail());
    }
}