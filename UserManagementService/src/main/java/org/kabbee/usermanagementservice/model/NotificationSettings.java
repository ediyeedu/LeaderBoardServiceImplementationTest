package org.kabbee.usermanagementservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class NotificationSettings {
    private boolean emailNotification;
    private boolean smsNotifications;
    private boolean pushNotification;


}
