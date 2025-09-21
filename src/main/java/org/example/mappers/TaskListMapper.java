package org.example.mappers;

import org.example.dto.TaskListDto;
import org.example.entities.TaskList;

public interface TaskListMapper {
    TaskList fromDto(TaskListDto taskListDto);
    TaskListDto toDto(TaskList taskList);

}
