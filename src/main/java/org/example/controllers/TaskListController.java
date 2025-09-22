package org.example.controllers;

import org.example.dto.TaskListDto;
import org.example.entities.TaskList;
import org.example.mappers.TaskListMapper;
import org.example.services.TaskListService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/tasks-lists")

public class TaskListController {
    private final TaskListService taskListService;
    private final TaskListMapper taskListMapper;
    // the taskListService is used to actually get your task list from the db or whatever
    // the taskListMapper is used to convert it into dto to send it to the frontend


    public TaskListController(TaskListService taskListService,TaskListMapper taskListMapper){
        this.taskListService = taskListService;
        this.taskListMapper  = taskListMapper;
    }

    @GetMapping
    public List<TaskListDto> listTaskLists(){
        return taskListService.listTaskLists()
                .stream()
                .map(taskListMapper::toDto)
                .toList();
    }

    @PostMapping
    public TaskListDto createTaskList(@RequestBody TaskListDto taskListDto){
        TaskList createdTaskList = taskListService.createTaskList(
                taskListMapper.fromDto(taskListDto)
        );
        return taskListMapper.toDto(createdTaskList);
    }

    @GetMapping(path = "/{task_list_id}")
    public Optional<TaskListDto> getTaskList(@PathVariable("task_list_id") UUID task_list_id){
        return taskListService.getTaskList(task_list_id).map(taskListMapper::toDto);
    }

    @PutMapping(path = "/{task_list_id}")
    public TaskListDto updateTaskList(
            @PathVariable("task_list_id") UUID taskListId,
            @RequestBody TaskListDto taskListDto)
    {
        TaskList updatedTaskList = taskListService.updateTaskList(
                taskListId,
                taskListMapper.fromDto(taskListDto)
        );
        return taskListMapper.toDto(updatedTaskList);
    }
    @DeleteMapping(path="/{task_list_id}")
    public void deleteTaskList(@PathVariable("task_list_id") UUID taskListId){
        taskListService.deleteTaskList(taskListId);
    }

    }


