package io.wulfcodes.library.persistence.dao;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import io.wulfcodes.library.model.po.User;
import io.wulfcodes.library.persistence.repo.UserRepository;

@Repository
public class UserDao {

    private final RowMapper<User> userRowMapper = (resultSet, rowNum) -> new User(resultSet.getLong("id"),
            resultSet.getString("first_name"), resultSet.getString("last_name"), resultSet.getString("email"),
            resultSet.getString("password"), resultSet.getString("phone_no"), resultSet.getString("address"));

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private JdbcClient jdbcClient;

    @Autowired
    private UserRepository userRepository;

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public Iterable<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User findByEmailAndPassword(String email, String password) {
        String query = "SELECT * FROM users WHERE email = ? AND password = ?";
        try {
            return jdbcTemplate.queryForObject(query, userRowMapper, email, password);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    public long getUsersCount() {
        return userRepository.count();
    }

    public User save(User user) {
        return userRepository.save(user);
    }

}
