package org.example.dto;

import java.util.List;
import java.util.UUID;
import org.example.entities.TaskList;
public record TaskListDto(
    UUID id,
    String title,
    String description,
    Integer count,
    Double progress,
    List<TaskDto> tasks
){

}
