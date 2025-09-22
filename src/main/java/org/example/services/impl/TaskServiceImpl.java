package org.example.services.impl;

import org.example.entities.Task;
import org.example.entities.TaskList;
import org.example.entities.TaskPriority;
import org.example.entities.TaskStatus;
import org.example.repositories.TaskListRepository;
import org.example.repositories.TaskRepository;
import org.example.services.TaskService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskListRepository taskListRepository;

    public TaskServiceImpl(TaskRepository taskRepository, TaskListRepository taskListRepository){
        this.taskRepository = taskRepository;
        this.taskListRepository = taskListRepository;
    }

    @Override
    public List<Task> listTasks(UUID taskListId) {
        if(taskListId == null){
            throw new IllegalArgumentException("The taskListId you have given is null");
        }
        try{
            return taskRepository.findByTaskListId(taskListId);
        } catch (Exception e) {
            throw new RuntimeException("Couldn't find the taskListId given by the user");
        }
    }

    @Override
    public Task createTask(UUID taskListId, Task task) {
        if(null!=task.getId()){
            throw new IllegalArgumentException("The task already has an ID!");
        }
        if(task.getTitle()==null || task.getTitle().isBlank()){
            throw new IllegalArgumentException("The task must have a title!");
        }
        TaskPriority taskPriority = Optional.ofNullable(task.getPriority()).orElse(TaskPriority.MEDIUM);

        TaskStatus taskStatus = TaskStatus.OPEN;

        TaskList taskList = taskListRepository
                .findById(taskListId)
                .orElseThrow(()->new IllegalArgumentException("Invalid Task List ID provided!"));

        LocalDateTime now = LocalDateTime.now();
        Task taskToSave = new Task(
                null,
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                taskStatus,
                taskPriority,
                taskList,
                now,
                now
        );
        return taskRepository.save(taskToSave);
    }

    @Override
    public Optional<Task> getTask(UUID taskListId, UUID taskId) {
        return taskRepository.findByTaskListIdAndId(taskListId,taskId);
    }
}
