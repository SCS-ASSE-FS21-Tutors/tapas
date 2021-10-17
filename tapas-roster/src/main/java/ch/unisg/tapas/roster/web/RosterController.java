package ch.unisg.tapas.roster.web;

import ch.unisg.tapas.roster.entities.Task;
import ch.unisg.tapas.roster.services.RosterService;
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
    public void addNewTask(@RequestBody Task newTask){

        if(newTask.getTaskId() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "TaskId missing");
        if(newTask.getTaskName()== null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "TaskName missing");
        if(newTask.getTaskType() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "TaskType missing");

        rosterService.rostTask(newTask);
    }

}
