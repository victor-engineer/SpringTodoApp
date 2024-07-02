package com.system.todolist.controller;

import com.system.todolist.entity.Task;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.system.todolist.service.TaskService;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    public List<Task> getAllTask(){
        return taskService.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id){
        Optional<Task> task = taskService.findById(id);
        if (task.isPresent()){
            return ResponseEntity.ok(task.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Task createTask(@RequestBody Task task){
        return taskService.save(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task taskDetails){
        Optional<Task> taskOptional = taskService.findById(id);
        if (taskOptional.isPresent()){
            Task task1 = taskOptional.get();
            task1.setName(taskDetails.getName());
            task1.setDescription(taskDetails.getDescription());
            task1.setRealized(taskDetails.getRealized());
            return ResponseEntity.ok(taskService.save(task1));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
