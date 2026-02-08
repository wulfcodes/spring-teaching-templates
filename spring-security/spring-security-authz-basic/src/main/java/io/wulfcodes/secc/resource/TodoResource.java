package io.wulfcodes.secc.resource;

import io.wulfcodes.secc.model.po.Todo;
import io.wulfcodes.secc.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/todos")
public class TodoResource {

    @Autowired
    private TodoService todoService;

    @GetMapping
    public List<Todo> getAll() {
        return todoService.getAll();
    }

    @GetMapping("/{todoId}")
    public ResponseEntity<Todo> getById(@PathVariable Long todoId) {
        Todo todo = todoService.getById(todoId);
        return (todo != null) ? ResponseEntity.ok(todo) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Todo> add(@RequestBody Todo request) {
        Todo createdTodo = todoService.add(request);
        URI location = URI.create("/api/todos/" + createdTodo.getId());
        return ResponseEntity.created(location).body(createdTodo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (todoService.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/suggest")
    public List<Todo> suggest() {
        return todoService.suggestTodos();
    }
}