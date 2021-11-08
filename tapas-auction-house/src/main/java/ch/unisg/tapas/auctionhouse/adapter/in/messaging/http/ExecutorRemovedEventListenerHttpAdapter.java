package ch.unisg.tapas.auctionhouse.adapter.in.messaging.http;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Template for handling an executor removed event received via an HTTP request
 */
@RestController
public class ExecutorRemovedEventListenerHttpAdapter {

    // TODO: add annotations for request method, request URI, etc.
    public void handleExecutorRemovedEvent(@PathVariable("executorId") String executorId) {
        // TODO: implement logic
    }
}
