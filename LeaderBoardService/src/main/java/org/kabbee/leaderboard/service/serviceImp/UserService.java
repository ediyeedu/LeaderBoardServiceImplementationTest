package org.kabbee.leaderboard.service.serviceImp;


import jakarta.annotation.PostConstruct;
import org.kabbee.leaderboard.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    private final Map<Long, UserDto> users = new HashMap <>();

    @PostConstruct
    public void init() {

    }

    public UserDto getUserById(Long userId) {
        return users.get(userId);
    }

}
