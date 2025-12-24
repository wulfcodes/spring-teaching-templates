package io.wulfcodes.secc.resource.v1;

import io.wulfcodes.secc.model.Todo;
import io.wulfcodes.secc.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/todos")
public class TodoResource {

    @Autowired
    private TodoService todoService;
    
    @GetMapping("/{todoId}")
    public ResponseEntity<Todo> getById(@PathVariable Long todoId) {
        Todo todo = todos.get(todoId);
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
