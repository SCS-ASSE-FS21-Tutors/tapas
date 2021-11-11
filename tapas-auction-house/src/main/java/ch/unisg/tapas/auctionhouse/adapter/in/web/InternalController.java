package ch.unisg.tapas.auctionhouse.adapter.in.web;

import ch.unisg.tapas.auctionhouse.domain.Task;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal")
public class InternalController {

    @Operation(description = "Should only be called by services of the TAPAS 3 group. Creates a new auction for a service that cannot be executed internally")
    @PostMapping("/create-auction-for-task/")
    public ResponseEntity createAuctionForTask(@RequestBody Task task){

        // TODO Implement

        if (task!= null && task.getTaskId() != null)
            return new ResponseEntity(HttpStatus.ACCEPTED);

        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}
