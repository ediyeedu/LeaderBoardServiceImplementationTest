package org.kabbee.usermanagementservice.service.adapter;

import org.kabbee.usermanagementservice.model.NotificationSettings;
import org.kabbee.usermanagementservice.service.dto.NotificationSettingsDto;

public class NotificationSettingsAdapter {

    public static NotificationSettings toEntity(NotificationSettingsDto notificationSettingsDto) {
        if (notificationSettingsDto == null) {
            return new NotificationSettings(false, false, false); // default values or handle appropriately
        }
        return new NotificationSettings(
                notificationSettingsDto.emailNotification(),
                notificationSettingsDto.smsNotifications(),
                notificationSettingsDto.pushNotification()
        );
    }

    public static NotificationSettingsDto toDto(NotificationSettings notificationSettings) {
        if (notificationSettings == null) {
            return new NotificationSettingsDto(false, false, false); // default values or handle appropriately
        }
        return new NotificationSettingsDto(
                notificationSettings.isEmailNotification(),
                notificationSettings.isSmsNotifications(),
                notificationSettings.isPushNotification()
        );
    }
}


