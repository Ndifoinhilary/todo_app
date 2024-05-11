package com.hilaryd.springboottodoapp.controller;

import com.hilaryd.springboottodoapp.dto.TodoDto;
import com.hilaryd.springboottodoapp.services.TodoServices;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/todos")
@AllArgsConstructor
public class TodoController {
    private TodoServices todoServices;

//    Build a todo add api
    @PostMapping("create-todo")
    public ResponseEntity<TodoDto> addTodo(@RequestBody TodoDto todoDto){
        var savedTodoDto = todoServices.add(todoDto);

        return  new ResponseEntity<>(savedTodoDto, HttpStatus.CREATED);
    }

    @GetMapping("all-todos")
    public  ResponseEntity<List<TodoDto>> getAllTodo(){

        List<TodoDto> getAllTodoDto = todoServices.getAll();

    return new  ResponseEntity<>(getAllTodoDto, HttpStatus.OK);
    }

//    find todo by id
    @GetMapping("get-todo/{id}/todo")
    public  ResponseEntity<TodoDto> getTodoById(@PathVariable("id") Long todoID){
        var getTodoDto = todoServices.getTodo(todoID);
        return new ResponseEntity<>(getTodoDto, HttpStatus.OK);
    }

    @PutMapping("update/{id}/")
    public ResponseEntity<TodoDto> updateTodo(@RequestBody TodoDto todoDto,@PathVariable("id") Long todoID){
        var updatedTodo = todoServices.updateTodos(todoDto, todoID);
        return ResponseEntity.ok(updatedTodo);
    }
    @DeleteMapping("delete/{id}/todo")
    public ResponseEntity<String> deletedTodo(@PathVariable Long id){
       todoServices.deleteTodo(id);
        return ResponseEntity.ok("Todo deleted succesfully ");
    }
//    to mark a todo as completed

    @PatchMapping("todo-complete/{id}/todo")
    public  ResponseEntity<TodoDto> completedTodo(@PathVariable("id") Long todoID){
       var completed =  todoServices.completedTodo(todoID);
       return ResponseEntity.ok(completed);
    }
    @PatchMapping("todo-not-complete/{id}/todo")
    public  ResponseEntity<TodoDto> isCompletedTodo(@PathVariable("id") Long todoId){
        var notCompleted = todoServices.inCompletedTodo(todoId);
        return ResponseEntity.ok(notCompleted);
    }
}
