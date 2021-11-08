package ch.unisg.roster.roster.application.port.in;

import ch.unisg.roster.roster.domain.Task;

public interface ApplyForTaskUseCase {
    Task applyForTask(ApplyForTaskCommand applyForTaskCommand);
}
