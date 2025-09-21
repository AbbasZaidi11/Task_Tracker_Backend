package org.example.mappers;

import org.example.dto.TaskDto;
import org.example.entities.Task;

public interface TaskMapper {
    Task fromDto(TaskDto taskDto);
    TaskDto toDto(Task task);
}
