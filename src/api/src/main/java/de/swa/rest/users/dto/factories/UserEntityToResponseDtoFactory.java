package de.swa.rest.users.dto.factories;

import de.swa.infrastructure.entities.UserEntity;
import de.swa.rest.users.dto.UserResponseDto;

public class UserEntityToResponseDtoFactory {
    public static UserResponseDto map(UserEntity user) {
        var dto = new UserResponseDto();
        dto.setUserName(user.userName);
        dto.setId(user.id);
        return dto;
    }
}
