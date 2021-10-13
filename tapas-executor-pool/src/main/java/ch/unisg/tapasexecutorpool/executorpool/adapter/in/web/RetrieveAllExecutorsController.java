package ch.unisg.tapasexecutorpool.executorpool.adapter.in.web;

import ch.unisg.tapasexecutorpool.executorpool.application.port.in.RetrieveAllExecutorsUseCase;
import ch.unisg.tapasexecutorpool.executorpool.domain.Executor;
import ch.unisg.tapasexecutorpool.executorpool.domain.ExecutorPool;
import org.json.JSONException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class RetrieveAllExecutorsController {
    private final RetrieveAllExecutorsUseCase retrieveAllExecutorsUseCase;

    public RetrieveAllExecutorsController(RetrieveAllExecutorsUseCase retrieveAllExecutorsUseCase) {
        this.retrieveAllExecutorsUseCase = retrieveAllExecutorsUseCase;
    }

    @GetMapping(path = "/executors/")
    public ResponseEntity<String> getAllExecutors() {
        List<Executor> executorList = retrieveAllExecutorsUseCase.getAllExecutors();

        // Add the content type as a response header
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(HttpHeaders.CONTENT_TYPE, ExecutorListMediaType.EXECUTOR_LIST_MEDIA_TYPE);

        try {
            return new ResponseEntity<>(ExecutorListMediaType.serialize(executorList), responseHeaders,
                    HttpStatus.OK);
        } catch (JSONException e) {
            System.out.println("There was an error retrieving the Executor");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
