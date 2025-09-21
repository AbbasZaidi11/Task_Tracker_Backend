package org.example.services;

import org.example.entities.TaskList;

import java.util.*;

public interface TaskListService {
    List<TaskList> listTaskLists();
    TaskList createTaskList(TaskList taskList);
    Optional<TaskList> getTaskList(UUID id);
    TaskList updateTaskList(UUID taskListId,TaskList taskList);
}
