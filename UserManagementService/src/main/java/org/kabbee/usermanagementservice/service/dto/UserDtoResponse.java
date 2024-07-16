package org.kabbee.usermanagementservice.service.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserDtoResponse {

    private Long userId;
    private String firstname;
    private String lastName;
    private String email;
}
