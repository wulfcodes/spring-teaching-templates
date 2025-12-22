package io.wulfcodes.secc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.wulfcodes.secc.model.Todo;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

    // Fake in-memory storage for demo purposes
    private final Map<Long, Todo> todos = new HashMap<>();
    private Long idCounter = 1L;

    // 🔐 Get todo by id (protected)
    @GetMapping("/{id}")
    public ResponseEntity<Todo> getById(@PathVariable Long id) {
        Todo todo = todos.get(id);
        if (todo == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(todo);
    }

    // 🔐 Add new todo (protected)
    @PostMapping
    public ResponseEntity<Todo> add(@RequestBody Todo request) {
        Todo todo = new Todo(idCounter++, request.getTitle(), false);
        todos.put(todo.getId(), todo);
        return ResponseEntity.status(HttpStatus.CREATED).body(todo);
    }

    // 🔐 Delete todo (protected)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!todos.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }
        todos.remove(id);
        return ResponseEntity.noContent().build();
    }

    // 🌍 Suggest todos (OPEN route)
    @GetMapping("/suggest")
    public List<Todo> suggest() {
        return List.of(
            new Todo(0L, "Buy groceries", false),
            new Todo(0L, "Finish homework", false),
            new Todo(0L, "Clean your room", false)
        );
    }
}
