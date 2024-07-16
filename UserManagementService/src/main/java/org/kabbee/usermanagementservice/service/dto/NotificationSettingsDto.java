package org.kabbee.usermanagementservice.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record NotificationSettingsDto(
        boolean emailNotification,
        boolean smsNotifications,
        boolean pushNotification


) {
}
