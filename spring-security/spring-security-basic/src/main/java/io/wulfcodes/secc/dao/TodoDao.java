package io.wulfcodes.secc.dao;

import io.wulfcodes.secc.model.po.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TodoDao {

    @Autowired
    private JdbcClient jdbcClient;

    private final RowMapper<Todo> rowMapper = (resultSet, rowNumber) -> new Todo(
            resultSet.getLong("id"),
            resultSet.getString("username"),
            resultSet.getString("title"),
            resultSet.getBoolean("is_completed"),
            resultSet.getDate("date_targeted").toLocalDate()
    );

    public List<Todo> findAllByUsername(String username) {
        return jdbcClient.sql("SELECT * FROM todos WHERE username = :username")
                .param("username", username)
                .query(rowMapper)
                .list();
    }

    public Optional<Todo> findById(Long id, String username) {
        return jdbcClient.sql("SELECT * FROM todos WHERE id = :id")
                .param("id", id)
                .query(rowMapper)
                .optional();
    }

    public Todo create(Todo todo) {
        String sql = "INSERT INTO todos (username, title, is_completed, date_targeted) VALUES (:username, :title, :isCompleted, :dateTargeted)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcClient.sql(sql)
                .param("username", todo.getUsername())
                .param("title", todo.getTitle())
                .param("isCompleted", todo.isCompleted())
                .param("dateTargeted", todo.getDateTargeted())
                .update(keyHolder);

        todo.setId(keyHolder.getKey().longValue());
        return todo;
    }

    public int delete(Long id, String username) {
        return jdbcClient.sql("DELETE FROM todos WHERE id = :id AND username = :username")
                .param("id", id)
                .param("username", username)
                .update();
    }
}