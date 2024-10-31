package com.test.web;

import com.test.modal.Todo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping({ "/api/todos" })
public class TodoController {

    @Autowired
    private RestClient restClient;
    private Logger logger = LoggerFactory.getLogger(TodoController.class);
//  https://jsonplaceholder.typicode.com

    @GetMapping()
    public List<Todo> getTodoList() {
        List<Todo> todos = restClient.get().uri("/todos").retrieve()
                .body(new ParameterizedTypeReference<>() {});
        return todos.stream().limit(10).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public Todo getTodoList(@PathVariable Integer id) {
        return restClient.get().uri("/todos/{id}", id).retrieve().body(Todo.class);
    }

    @PostMapping
    public Todo addTodo(@RequestBody Todo todo) {
        return restClient.post().uri("/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .body(todo)
                .retrieve().body(Todo.class);
    }

}
