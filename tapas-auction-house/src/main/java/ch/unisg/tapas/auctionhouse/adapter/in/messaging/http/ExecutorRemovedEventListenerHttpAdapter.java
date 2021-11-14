package ch.unisg.tapas.auctionhouse.adapter.in.messaging.http;

import ch.unisg.tapas.auctionhouse.application.handler.ExecutorRemovedHandler;
import ch.unisg.tapas.auctionhouse.application.port.in.ExecutorRemovedEvent;
import ch.unisg.tapas.auctionhouse.domain.ExecutorRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Handles an executor removed event received via an HTTP request
 */
@RestController
public class ExecutorRemovedEventListenerHttpAdapter {

    @DeleteMapping(path = "/executors/{executorId}")
    public ResponseEntity<String> handleExecutorRemovedEvent(
        @PathVariable("executorId") String executorId)
    {
        var event = new ExecutorRemovedEvent(
            new ExecutorRegistry.ExecutorIdentifier(executorId)
        );

        var handler = new ExecutorRemovedHandler();
        handler.handleExecutorRemovedEvent(event);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
