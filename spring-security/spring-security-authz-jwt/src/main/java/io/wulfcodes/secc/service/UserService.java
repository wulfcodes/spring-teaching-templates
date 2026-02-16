package io.wulfcodes.secc.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import io.wulfcodes.secc.model.po.User;

@Service
public class UserService {
    private static final List<User> USERS = new ArrayList<>();


    public User createUser(String username, String password, Boolean isAdmin) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setAdmin(isAdmin);

        USERS.add(user);
        return user;
    }

    public boolean userExists(String username) {
        for (User user : USERS)
            if (user.getUsername().equals(username))
                return true;
        return false;
    }

    public boolean userExists(String username, String password) {
        for (User user : USERS)
            if (user.getUsername().equals(username) && user.getPassword().equals(password))
                return true;
        return false;
    }


}
