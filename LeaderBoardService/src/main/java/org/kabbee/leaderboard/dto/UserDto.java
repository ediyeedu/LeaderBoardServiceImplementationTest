package org.kabbee.leaderboard.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserDto {
    @NotNull(message = "userId can not be null")
    private Long userId;
    @NotEmpty(message = "firstname can not be null")
    private String firstname;
    @NotEmpty(message = "lastName can not be null")
    private String lastName;
    @NotEmpty(message = "email can not be null")
    private String email;
}
