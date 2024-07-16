package org.kabbee.usermanagementservice.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.kabbee.usermanagementservice.service.dto.UserDtoResponse;
import org.kabbee.usermanagementservice.model.User;
import org.kabbee.usermanagementservice.repository.UserRepository;
import org.kabbee.usermanagementservice.service.UserService;
import org.kabbee.usermanagementservice.service.adapter.UserAdapter;
import org.kabbee.usermanagementservice.service.dto.UserRequestDto;
import org.kabbee.usermanagementservice.service.dto.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserRequestDto userRequestDto) {
        User user = UserAdapter.toEntity(userRequestDto);
        return UserAdapter.toDto(userRepository.save(user));
    }

    @Override
    public UserDto getUser(Long id) {
        User user = userRepository.findById(id)
                .orElse(null);
        if (user == null) {
            return null;
        }
        return UserAdapter.toDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserAdapter::toDto)
                .toList();
    }

    @Override
    public UserDto updateUser(Long id, UserRequestDto userRequestDto) {
        User user = userRepository.findById(id)
                .orElse(null);
        if (user == null) {
            return null;
        }
        User updatedUser = UserAdapter.toEntity(userRequestDto);
        user.setId(id);
        return UserAdapter.toDto(userRepository.save(updatedUser));
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDtoResponse findUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

            return UserDtoResponse.builder ()
                .userId(user.getId())
                .firstname(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();
    }
}
