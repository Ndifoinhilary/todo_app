package com.hilaryd.springboottodoapp.services;

import com.hilaryd.springboottodoapp.dto.TodoDto;

import java.util.List;

public interface TodoServices {
    TodoDto add(TodoDto todoDto);
    List<TodoDto> getAll( );

    TodoDto getTodo(Long id);

    TodoDto updateTodos(TodoDto todoDto, Long id);
    void deleteTodo(Long id);

    TodoDto completedTodo(Long id);
    TodoDto inCompletedTodo(Long id);
}
