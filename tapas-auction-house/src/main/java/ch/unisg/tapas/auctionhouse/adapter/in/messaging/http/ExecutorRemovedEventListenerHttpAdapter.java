package ch.unisg.tapas.auctionhouse.adapter.in.messaging.http;

import ch.unisg.tapas.auctionhouse.application.handler.ExecutorRemovedHandler;
import ch.unisg.tapas.auctionhouse.application.port.in.ExecutorRemovedEvent;
import ch.unisg.tapas.auctionhouse.domain.Auction;
import ch.unisg.tapas.auctionhouse.domain.ExecutorRegistry;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Template for handling an executor removed event received via an HTTP request
 */
@RestController
public class ExecutorRemovedEventListenerHttpAdapter {

    // TODO: add annotations for request method, request URI, etc.
    @PostMapping(path = "/executors/{taskType}/{executorId}")
    public ResponseEntity<String> handleExecutorRemovedEvent(@PathVariable("executorId") String executorId) {
        // TODO: implement logic

        ExecutorRemovedEvent executorRemovedEvent = new ExecutorRemovedEvent(
            new ExecutorRegistry.ExecutorIdentifier(executorId)            
        );

        ExecutorRemovedHandler newExecutorHandler = new ExecutorRemovedHandler();
        newExecutorHandler.handleExecutorRemovedEvent(executorRemovedEvent);
        
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
