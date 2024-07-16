package org.kabbee.usermanagementservice.controller;

import lombok.RequiredArgsConstructor;
import org.kabbee.usermanagementservice.service.dto.UserDtoResponse;
import org.kabbee.usermanagementservice.service.UserService;
import org.kabbee.usermanagementservice.service.dto.UserDto;
import org.kabbee.usermanagementservice.service.dto.UserRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user-service/api/users")
public class Controllers {
    private final UserService userService;
    
    @GetMapping("/healthcheck")
    public String healthCheck(){
        return "User-service working Fine";
    }
    
    
    @GetMapping("all")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
            UserDto userDto = userService.getUser(id);
            if (userDto == null) {
                return new ResponseEntity<>("User Not Found", HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok().body(userDto);
    }

    @PostMapping("create")
    public ResponseEntity<?> createUser(@RequestBody UserRequestDto userRequestDto) {
        return ResponseEntity.ok().body(userService.createUser(userRequestDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserRequestDto userRequestDto) {
        UserDto userDto = userService.updateUser(id, userRequestDto);
        if (userDto == null) {
            return new ResponseEntity<>("User Not Found", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok().body(userDto);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().body("User deleted successfully");
    }

    @GetMapping("ext/{id}")
    public ResponseEntity<?> findUserById(@PathVariable Long id) {
        System.out.println ("88888888888888*********888888888888************888888888**************8888" + id);
        UserDtoResponse userDto = userService.findUserById(id);
        System.out.println ("88888888888888*********888888888888************888888888**************8888" + userDto);
        return ResponseEntity.ok( userDto );
    }
}
