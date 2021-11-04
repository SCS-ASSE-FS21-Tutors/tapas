package ch.unisg.tapas.auctionhouse.adapter.in.web;

import ch.unisg.tapas.auctionhouse.adapter.common.formats.AuctionJsonRepresentation;
import ch.unisg.tapas.auctionhouse.adapter.common.formats.TaskJsonRepresentation;
import ch.unisg.tapas.auctionhouse.application.port.in.TaskWonCommand;
import ch.unisg.tapas.auctionhouse.application.port.in.TaskWonUseCase;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TaskWonWebController {
    private final TaskWonUseCase taskWonUseCase;

    public TaskWonWebController(TaskWonUseCase taskWonUseCase) {
        this.taskWonUseCase = taskWonUseCase;
    }


    @PostMapping(path = "/taskwinner/",consumes = TaskJsonRepresentation.MEDIA_TYPE)
    public ResponseEntity<String> winTask(@RequestBody TaskJsonRepresentation payload) {
        TaskWonCommand command = new TaskWonCommand(payload);
        boolean success = taskWonUseCase.addWonTaskToTaskList(command);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(HttpHeaders.CONTENT_TYPE, TaskJsonRepresentation.MEDIA_TYPE);
        String taskRepresentation = payload.toJSON();
        if(success){
            return new ResponseEntity<>(taskRepresentation,responseHeaders,HttpStatus.ACCEPTED);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }


    }
}
