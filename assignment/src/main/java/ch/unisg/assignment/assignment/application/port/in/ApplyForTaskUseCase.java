package ch.unisg.assignment.assignment.application.port.in;

import ch.unisg.assignment.assignment.domain.Task;

public interface ApplyForTaskUseCase {
    Task applyForTask(ApplyForTaskCommand applyForTaskCommand);
}
