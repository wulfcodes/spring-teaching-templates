package io.wulfcodes.library.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.wulfcodes.library.model.dto.UserData;
import io.wulfcodes.library.service.spec.AuthService;
import io.wulfcodes.library.service.spec.UserService;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserService userService;

    @Override
    public UserData loginUser(String email, String password) {
        return userService.loginUser(email, password);
    }
}
