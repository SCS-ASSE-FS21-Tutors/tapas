package ch.unisg.tapas.roster.services;

import ch.unisg.tapas.roster.entities.Task;

public interface RosterService {
    void sendTaskToExecutor(Task newTask);
}
