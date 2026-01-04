package io.wulfcodes.secc.service;

import io.wulfcodes.secc.dao.TodoDao;
import io.wulfcodes.secc.model.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TodoService {

    @Autowired
    private TodoDao todoDao;

    private String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public List<Todo> getAll() {
        return todoDao.findAllByUsername(getCurrentUsername());
    }

    public Todo getById(Long id) {
        return todoDao.findById(id)
                      .orElse(null);
    }

    public Todo add(Todo todo) {
        // Auto-assign the logged-in user
        todo.setUsername(getCurrentUsername());


        return todoDao.create(todo);
    }

    public boolean delete(Long id) {
        int rows = todoDao.delete(id, getCurrentUsername());
        return rows > 0;
    }

    // Suggestions (Dummy data, no DB interaction)
    public List<Todo> suggestTodos() {
        return List.of(
            new Todo(0L, "system", "Buy groceries", false, LocalDate.now()),
            new Todo(0L, "system", "Finish homework", false, LocalDate.now().plusDays(1))
        );
    }
}