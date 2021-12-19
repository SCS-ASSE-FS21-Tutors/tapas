package ch.unisg.tapas.roster.adapter.in.web;

import ch.unisg.tapas.common.formats.TaskJsonRepresentation;
import ch.unisg.tapas.roster.entities.Task;
import ch.unisg.tapas.roster.application.port.in.RostNewTaskUseCase;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RequestMapping("/roster")
@RestController
@Log4j2
public class RosterController {

    private RostNewTaskUseCase rostNewTaskUseCase;

    @Autowired
    public RosterController(RostNewTaskUseCase rostNewTaskUseCase) {
        this.rostNewTaskUseCase = rostNewTaskUseCase;
    }

    @PostMapping(path="/newtask/", consumes = TaskJsonRepresentation.MEDIA_TYPE)
    public void addNewTask(@RequestBody TaskJsonRepresentation taskJsonRepresentation){
        Task task = TaskJsonRepresentation.toTask(taskJsonRepresentation);
        log.info("Received new task at roster: {}", task.getTaskId().getValue());
        if(task.getTaskId() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "TaskId missing");
        if(task.getTaskName()== null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "TaskName missing");
        if(task.getTaskType() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "TaskType missing");

        rostNewTaskUseCase.rostTask(task);
    }

}
