package org.kabbee.usermanagementservice.service.adapter;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.kabbee.usermanagementservice.model.Role;
import org.kabbee.usermanagementservice.model.User;
import org.kabbee.usermanagementservice.service.dto.UserRequestDto;
import org.kabbee.usermanagementservice.service.dto.UserDto;

public class UserAdapter {

    public static UserDto toDto(User user) {
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getNickName(),
                user.getCountry(),
                user.getJoinedDate(),
                user.getLastLoginDate(),
                user.getRole(),
                NotificationSettingsAdapter.toDto(user.getNotificationSettings())
        );
    }

    public static User toEntity(UserRequestDto userRequestDto) {
        return new User(
                null,
                userRequestDto.username(),
                userRequestDto.email(),
                userRequestDto.firstName(),
                userRequestDto.lastName(),
                userRequestDto.nickName(),
                userRequestDto.country(),
                userRequestDto.joinedDate(),
                userRequestDto.lastLoginDate(),
                Role.valueOf(userRequestDto.role().toUpperCase()),
                NotificationSettingsAdapter.toEntity(userRequestDto.notificationSettingsDto())
        );
    }
}
