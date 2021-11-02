package ch.unisg.assignment.assignment.application.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import ch.unisg.assignment.assignment.application.port.in.DeleteTaskCommand;
import ch.unisg.assignment.assignment.application.port.in.DeleteTaskUseCase;
import ch.unisg.assignment.assignment.domain.Roster;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
@Transactional
public class DeleteTaskService implements DeleteTaskUseCase {

    /**
    *   Check if task can get deleted
    *   @return if task can get deleted
    **/
    @Override
    public boolean deleteTask(DeleteTaskCommand command) {
        Roster roster = Roster.getInstance();
        return roster.deleteTask(command.getTaskId(), command.getTaskType());
    }

}
