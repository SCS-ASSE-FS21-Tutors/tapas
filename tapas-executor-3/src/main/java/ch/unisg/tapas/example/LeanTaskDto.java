package ch.unisg.tapas.example;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LeanTaskDto {
    private String taskId;
    private String taskInput;
}