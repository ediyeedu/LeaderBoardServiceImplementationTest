package org.kabbee.usermanagementservice.service.dto;

import org.kabbee.usermanagementservice.model.Role;

import java.time.LocalDate;

public record UserDto(
        Long id,
        String username,
        String email,
        String firstName,
        String lastName,
        String nickName,
        String country,
        LocalDate joinedDate,
        LocalDate lastLoginDate,
        Role role,
        NotificationSettingsDto notificationSettingsDto
) {
}