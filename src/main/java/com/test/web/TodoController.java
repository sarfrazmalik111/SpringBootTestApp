package com.test.web;

import com.test.common.MyUtility;
import com.test.modal.MyRecord;
import com.test.modal.Todo;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping({ "/api/todos" })
public class TodoController {

    @Autowired
    private RestClient restClient;
    @Autowired
    private MyUtility myUtility;
    private Logger logger = LoggerFactory.getLogger(TodoController.class);
//  https://jsonplaceholder.typicode.com

    @GetMapping()
    public List<Todo> getTodoList() {
        List<Todo> todos = restClient.get().uri("/todos").retrieve()
                .body(new ParameterizedTypeReference<>() {});
        return todos.stream().limit(10).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public Todo getTodoDetails(@PathVariable Integer id) {
        return restClient.get().uri("/todos/{id}", id).retrieve().body(Todo.class);
    }

    @PostMapping
    public Todo addTodo(@RequestBody Todo todo) {
        return restClient.post().uri("/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .body(todo)
                .retrieve().body(Todo.class);
    }

    @DeleteMapping("/{id}")
    public Todo deleteTodoDetails(@PathVariable Integer id) {
        return restClient.delete().uri("/todos/{id}", id).retrieve().body(Todo.class);
    }

    @PostMapping("save-todos")
    public String saveTodos(@RequestBody List<Todo> todos) {
        System.out.println(todos);
        //todoRepo.saveAll(todos);
        return "Success";
    }

//    ===============================================================
    @GetMapping("/test-xml")
    public MyRecord testXmlMethod(HttpServletRequest request) {
        System.out.println("----------testXmlMethod------------");
        var student = new MyRecord(123, "Sarfraz", "Roorkee");
        System.out.println(student);

        ProblemDetail errDetails = ProblemDetail.forStatus(HttpStatus.NOT_ACCEPTABLE);
        errDetails.setTitle("Input invalid Exception");
        errDetails.setDetail("Input can't be processed");
        errDetails.setType(URI.create(request.getRequestURL().toString()));
        errDetails.setProperty("myError", "custom error object");
    //		return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(errDetails);
        return student;
    }

    @GetMapping("/test-xml2")
    public ResponseEntity testXmlMethod2(HttpServletRequest request) {
        System.out.println("----------testXmlMethod-2------------");
        ProblemDetail errDetails = ProblemDetail.forStatus(HttpStatus.NOT_ACCEPTABLE);
        errDetails.setTitle("Input invalid Exception");
        errDetails.setDetail("Input can't be processed");
        errDetails.setType(URI.create(request.getRequestURL().toString()));
        errDetails.setProperty("myError", "custom error object");
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(errDetails);
    }

    int count = 1;
    @GetMapping("/test-aop")
    public String testAOP() {
        System.out.println("----------testAOP------------");
        System.out.println("getTimeInSeconds : " +myUtility.getTimeInSeconds());
        return "Success";
    }

    @GetMapping("/test-aop2")
    public String testAOP2() {
        System.out.println("----------testAOP-2-----------");
        return "Success";
    }

}
