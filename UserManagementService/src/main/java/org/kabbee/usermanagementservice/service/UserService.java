package org.kabbee.usermanagementservice.service;

import org.kabbee.usermanagementservice.service.dto.UserDtoResponse;
import org.kabbee.usermanagementservice.service.dto.UserRequestDto;
import org.kabbee.usermanagementservice.service.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto createUser(UserRequestDto userRequestDto);
    UserDto getUser(Long id);
    List<UserDto> getAllUsers();
    UserDto updateUser(Long id, UserRequestDto userRequestDto);
    void deleteUser(Long id);
    UserDtoResponse findUserById(Long id);



}
