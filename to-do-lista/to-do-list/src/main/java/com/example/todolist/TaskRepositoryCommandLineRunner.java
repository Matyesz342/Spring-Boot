package com.example.todolist;

import com.example.todolist.entity.Task;
import com.example.todolist.service.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class TaskRepositoryCommandLineRunner implements CommandLineRunner {


    private static final Logger log = LoggerFactory.getLogger(TaskRepositoryCommandLineRunner.class);
    @Autowired
    private TaskRepository taskRepository;

    @Override
    public void run(String... args) throws Exception {
        Task task = new Task("task 1", "Completed", "Gyakorló feladat");
        taskRepository.save(task);
        Task task2 = new Task("task 2", "In Progress", "Gyakorló feladat folytatása");
        taskRepository.save(task2);
    }
}
