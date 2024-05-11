package com.hilaryd.springboottodoapp.services.impl;

import com.hilaryd.springboottodoapp.dto.TodoDto;
import com.hilaryd.springboottodoapp.entity.Todo;
import com.hilaryd.springboottodoapp.exceptions.ResourceNotFoundException;
import com.hilaryd.springboottodoapp.repository.TodoRepository;
import com.hilaryd.springboottodoapp.services.TodoServices;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TodoServiceImpl implements TodoServices {
    private TodoRepository todoRepository;

    private ModelMapper modelMapper;

    @Override
    public TodoDto add(TodoDto todoDto) {
//        making use of modelmapper to automatically convert todoDto to todo entity

        var todo = modelMapper.map(todoDto, Todo.class);

//        now save the Todo jpa entity
        var savedTodo =todoRepository.save(todo);
//        convert the savedTodo jpa entity to TodoDto object


//        automatically convert the todo entity back to tododto using the modelmapper

        var saveTodoDto = modelMapper.map(savedTodo, TodoDto.class);

        return saveTodoDto;
    }

    @Override
    public List<TodoDto> getAll() {
        List<Todo> todo = todoRepository.findAll();
        var getTodoDto = todo.stream().map((todo1 -> modelMapper.map(todo1, TodoDto.class))).collect(Collectors.toList());
        return getTodoDto;
    }

    @Override
    public TodoDto getTodo(Long id) {
        var todo = todoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No Todo with the id " + id  + " Was found "));

        var getTodoDtoById  = modelMapper.map(todo, TodoDto.class);

        return getTodoDtoById;
    }

    @Override
    public TodoDto updateTodos(TodoDto todoDto, Long id) {
        var todo = todoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Todo with id " + id + " Not found"));
        todo.setTitle(todoDto.getTitle());
        todo.setDescription(todoDto.getDescription());
        todo.setCompleted(todoDto.isCompleted());
        var updateTodo  =  todoRepository.save(todo);
        return modelMapper.map(updateTodo, TodoDto.class);
    }

    @Override
    public void deleteTodo(Long id) {
        var todo = todoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Todo with id " + id + " Not found"));
        todoRepository.deleteById(id);

    }

    @Override
    public TodoDto completedTodo(Long id) {
        var todo = todoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Todo with id " + id + " Not found"));
        todo.setCompleted(Boolean.TRUE);
        var completed =  todoRepository.save(todo);
        return modelMapper.map(completed, TodoDto.class);
    }

    @Override
    public TodoDto inCompletedTodo(Long id) {
        var todo = todoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Todo with id " + id + " Not found"));
        todo.setCompleted(Boolean.FALSE);
        var isCompletedTodo = todoRepository.save(todo);
        return modelMapper.map(isCompletedTodo, TodoDto.class);
    }


}
