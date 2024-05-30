package com.example.todolist.controller;

import com.example.todolist.entity.Task;
import com.example.todolist.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
@Tag(name = "Task Management System", description = "Operations pertaining to tasks in To-Do List")
public class TaskController {

    private final TaskService taskService;


    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    @Operation(summary = "Create a new task")
    public ResponseEntity<Task> createTask(@Valid @RequestBody Task task) throws URISyntaxException {
        Task createdTask = taskService.createTask(task);
        return ResponseEntity.created(new URI("/tasks/" + createdTask.getId())).body(createdTask);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a task by Id")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @Valid @RequestBody Task updatedTask) {
        Optional<Task> existingTask = taskService.getTaskById(id);
        if (existingTask.isPresent()) {
            Task task = existingTask.get();
            if (!updatedTask.getTitle().equals("string")) {
                task.setTitle(updatedTask.getTitle());
            }
            if (!updatedTask.getDescription().equals("string")) {
                task.setDescription(updatedTask.getDescription());
            }
            if (!updatedTask.getStatus().equals("string")) {
                task.setStatus(updatedTask.getStatus());
            }
            taskService.updateTask(task);
            return ResponseEntity.ok(task);
        }
        else
        {
            return ResponseEntity.notFound().build();
        }
    }



    @GetMapping
    @Operation(summary = "View a list of available tasks")
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = taskService.getAllTask();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/get/{id}")
    @Operation(summary = "Get a task by Id")
    public ResponseEntity<Task> getTask(@Min(1) @PathVariable Long id) {
        Optional<Task> task = taskService.getTaskById(id);
        return task.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    @Operation(summary = "Search tasks by title")
    public ResponseEntity<List<Task>> searchTasksByTitle(@RequestParam String title) {
        List<Task> tasks = taskService.getTasksByTitle(title);
        return ResponseEntity.ok(tasks);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a task by Id")
    public ResponseEntity<Void> deleteTask(@Valid @PathVariable long id) {
        boolean isDeleted = taskService.deleteTask(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        }
        else
        {
            return ResponseEntity.notFound().build();
        }

    }

}
