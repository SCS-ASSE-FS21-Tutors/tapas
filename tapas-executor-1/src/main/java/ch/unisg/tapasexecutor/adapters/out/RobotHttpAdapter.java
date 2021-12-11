package ch.unisg.tapasexecutor.adapters.out;

import ch.unisg.tapasexecutor.adapters.out.robot.RobotApi;
import ch.unisg.tapasexecutor.application.ports.out.RobotPort;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@Log4j2
@Component
public class RobotHttpAdapter implements RobotPort {


    /**
     * Receives and input string (from the task object) executes the task
     * and returns the output string.
     *
     * @param inputValue
     * @return
     */
    @Override
    public String executeTask(String inputValue, String robotTDUri) {

        var start = Instant.now();

        try (var api = RobotApi.open(robotTDUri)) {

            api.dance();

        } catch (Exception exception) {

            log.warn("Exception while executing task: " + exception);
        }

        var duration = Duration.between(start, Instant.now());
        return "Executed in " + duration.toMillis() + "ms";

    }

}
