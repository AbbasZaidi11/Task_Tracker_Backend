package org.example.services.impl;

import jakarta.transaction.Transactional;
import org.example.entities.TaskList;
import org.example.repositories.TaskListRepository;
import org.example.services.TaskListService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class TaskListServiceImpl implements TaskListService {
    private final TaskListRepository taskListRepository;

    public TaskListServiceImpl(TaskListRepository taskListRepository){
        this.taskListRepository = taskListRepository;
    }
    @Override
    public List<TaskList> listTaskLists(){
        return taskListRepository.findAll();
    }

    @Transactional
    @Override
    public TaskList createTaskList(TaskList taskList) {
        // this is the part where we are stuffing are shit into the db
        if(null != taskList.getId()){
            throw new IllegalArgumentException("Task List already has an ID!");
        }
        if(null == taskList.getTitle() || taskList.getTitle().isBlank()){
            throw new IllegalArgumentException("Task list title must be present!");
        }
        LocalDateTime now = LocalDateTime.now();
        return taskListRepository.save(new TaskList(
                null,
                taskList.getTitle(),
                taskList.getDescription(),
                null,
                now,
                now
        ));

    }

    @Override
    public Optional<TaskList> getTaskList(UUID id) {
         return taskListRepository.findById(id);
    }

    @Transactional
    @Override
    public TaskList updateTaskList(UUID taskListId, TaskList taskList) {
        if(null == taskList.getId()){
            throw new IllegalArgumentException("Task list must have an ID");
        }
        if(!Objects.equals(taskList.getId(),taskListId)){
            throw new IllegalArgumentException("Attempting to change task list ID, this is not permitted !");
        }
        TaskList exisitingTaskList = taskListRepository.findById(taskListId).orElseThrow(() ->
                new IllegalArgumentException("Task list not found!"));

        exisitingTaskList.setTitle(taskList.getTitle());
        exisitingTaskList.setDescription(taskList.getDescription());
        exisitingTaskList.setUpdated(LocalDateTime.now());

        return taskListRepository.save(exisitingTaskList);
    }

    @Transactional
    @Override
    public void deleteTaskList(UUID taskListId) {
        if(null == taskListId){
            throw new IllegalArgumentException("You cannot delete a tasklist without giving its taskListId!");
        }
       try{
            taskListRepository.deleteById(taskListId);
       }catch(org.springframework.dao.EmptyResultDataAccessException ex){
           throw new NoSuchElementException("Task list not found: " + taskListId);
       }
    }

}