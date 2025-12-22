package io.wulfcodes.library.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import io.wulfcodes.library.exception.UserNotFoundException;
import io.wulfcodes.library.mapper.UserMapper;
import io.wulfcodes.library.model.dto.UserData;
import io.wulfcodes.library.model.po.User;
import io.wulfcodes.library.persistence.dao.UserDao;
import io.wulfcodes.library.service.spec.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserData getUserById(Long userId) {
        return userDao.findById(userId)
                      .map(userMapper::toData)
                      .orElseThrow(() -> new UserNotFoundException("User not found."));
    }

    @Override
    public List<UserData> getAllUsers() {
        return userMapper.toDataList(userDao.findAllUsers());
    }

    @Override
    public UserData loginUser(String email, String password) {
        Optional<User> userOptional = Optional.ofNullable(userDao.findByEmailAndPassword(email, password));
        return userOptional.map(userMapper::toData)
                           .orElseThrow(() -> new UserNotFoundException("Invalid user credentials."));
    }

    @Override
    @Async
    public CompletableFuture<Long> getUsersCountAsync() {
        return CompletableFuture.completedFuture(userDao.getUsersCount());
    }

}
