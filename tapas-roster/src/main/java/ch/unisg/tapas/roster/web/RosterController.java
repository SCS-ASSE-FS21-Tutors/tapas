package ch.unisg.tapas.roster.web;

import ch.unisg.tapas.roster.entities.Task;
import ch.unisg.tapas.roster.services.RosterService;
import ch.unisg.tapas.roster.web.dto.NewTaskDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RequestMapping("/roster")
@RestController
public class RosterController {

    private RosterService rosterService;

    @Autowired
    public RosterController(RosterService rosterService) {
        this.rosterService = rosterService;
    }

    @PostMapping("/newtask/")
    public void addNewTask(@RequestBody NewTaskDto newTaskDto){

        if(newTaskDto.getTaskListName() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "TaskListName missing");
        if(newTaskDto.getTaskName() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "TaskName missing");

        // I don't know yet how we get the task info
        // I guess we need to add that to the request, for now just invent a task

        var task = new Task(
                new Task.TaskName(newTaskDto.getTaskName()),
                new Task.TaskType("SomeType"));

        rosterService.sendTaskToExecutor(task);

        return;
    }

}
