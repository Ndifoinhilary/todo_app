package com.hilaryd.springboottodoapp.repository;

import com.hilaryd.springboottodoapp.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}
