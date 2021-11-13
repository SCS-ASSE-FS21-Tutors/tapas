package ch.unisg.tapas.roster.application.services;

import ch.unisg.tapas.roster.application.port.in.RostNewTaskUseCase;
import ch.unisg.tapas.roster.application.port.out.AuctionHousePort;
import ch.unisg.tapas.roster.application.port.out.ExecutorPoolPort;
import ch.unisg.tapas.roster.entities.Task;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
@RequiredArgsConstructor
public class BasicRosterService implements RostNewTaskUseCase {

    private final AuctionHousePort auctionHousePort;
    private final ExecutorPoolPort executorPoolPort;

    @Override
    public void rostTask(Task newTask){

        boolean canExecuteInternally = executorPoolPort.canExecuteInternally(newTask);

        if(canExecuteInternally)
            executorPoolPort.executeInternally(newTask);
        else
            auctionHousePort.executeExternally(newTask);
    }
}
