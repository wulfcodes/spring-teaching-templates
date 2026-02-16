package io.wulfcodes.secc.resource;

import io.wulfcodes.secc.common.context.UserContext;
import io.wulfcodes.secc.model.po.Todo;
import io.wulfcodes.secc.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/todos")
public class TodoResource {

    @Autowired
    private UserContext userContext;

    @Autowired
    private TodoService todoService;

    public String getCurrentUsername() {    // SPeL can only call public methods
        return userContext.getUsername();
    }

    @GetMapping
    public List<Todo> getAll() {
        return todoService.getAll();
    }

    @PostMapping
    @PreAuthorize("#request.username == #this.getCurrentUsername()") // # refers to objects in the current evaluation context
    public ResponseEntity<Todo> add(@RequestBody Todo request) {
        Todo createdTodo = todoService.add(request);
        URI location = URI.create("/api/todos/" + createdTodo.getId());
        return ResponseEntity.created(location).body(createdTodo);
    }

    @GetMapping("/{todoId}")
    @PostAuthorize("returnObject.body == null or returnObject.body.username == @userContext.username")  // returnObject is a special which refers to the responseEntity
    public ResponseEntity<Todo> getById(@PathVariable Long todoId) {
        Todo todo = todoService.getById(todoId);
        return (todo != null) ? ResponseEntity.ok(todo) : ResponseEntity.notFound().build();
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("@todoService.getById(#id) != null and @todoService.getById(#id).username == @userContext.username") // @ refers to spring beans present
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