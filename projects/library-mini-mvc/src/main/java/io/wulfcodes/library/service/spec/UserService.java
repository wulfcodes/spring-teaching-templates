package io.wulfcodes.library.service.spec;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import io.wulfcodes.library.model.dto.UserData;

public interface UserService {
    UserData getUserById(Long userId);

    List<UserData> getAllUsers();

    UserData loginUser(String email, String password);

    CompletableFuture<Long> getUsersCountAsync();

    UserData registerUser(UserData userData);
}
