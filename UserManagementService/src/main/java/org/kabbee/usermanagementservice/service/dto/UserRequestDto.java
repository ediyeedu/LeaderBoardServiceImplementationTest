package org.kabbee.usermanagementservice.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.kabbee.usermanagementservice.model.Role;

import java.time.LocalDate;

public record UserRequestDto(
        String username,
        String email,
        String firstName,
        String lastName,
        String nickName,
        String country,
        LocalDate joinedDate,
        LocalDate lastLoginDate,
        String role,
        @JsonProperty("notificationSettings")
        NotificationSettingsDto notificationSettingsDto
)
{
}
