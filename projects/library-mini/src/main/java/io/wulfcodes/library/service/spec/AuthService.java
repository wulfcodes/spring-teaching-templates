package io.wulfcodes.library.service.spec;

import io.wulfcodes.library.model.dto.UserData;

public interface AuthService {
    UserData loginUser(String email, String password);
}
