package ch.unisg.tapas.roster.application.port.in;

import ch.unisg.tapas.roster.entities.Task;

public interface RostNewTaskUseCase {
    void rostTask(Task newTask);
}
