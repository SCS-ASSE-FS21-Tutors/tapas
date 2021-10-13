package ch.unisg.tapasexecutors.executors.adapter.in.web;

import ch.unisg.tapasexecutors.executors.application.port.in.AddExecutorToExecutorPoolUseCase;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AddNewExecutorToExecutorPoolController {
    private final AddExecutorToExecutorPoolUseCase addExecutorToExecutorPoolUseCase;

    public AddNewExecutorToExecutorPoolController(AddExecutorToExecutorPoolUseCase addExecutorToExecutorPoolUseCase) {
        this.addExecutorToExecutorPoolUseCase = addExecutorToExecutorPoolUseCase;
    }

}