package ch.unisg.tapasexecutorrobot.executor.adapter.out.web;

import ch.unisg.tapascommon.ServiceApiAddresses;
import ch.unisg.tapasexecutorrobot.executor.application.port.out.TaskExecutionCompletedEventPort;
import ch.unisg.tapasexecutorrobot.executor.domain.TaskExecutionCompletedEvent;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class PublishTaskExecutionCompletedEventWebAdapter implements TaskExecutionCompletedEventPort {

    private static final String URL = ServiceApiAddresses.getTaskServiceApiUrl();
    private static final String PATH = "/tasks/";

    @Override
    public void publishTaskExecutionCompletedEvent(TaskExecutionCompletedEvent event) {

    }
}
