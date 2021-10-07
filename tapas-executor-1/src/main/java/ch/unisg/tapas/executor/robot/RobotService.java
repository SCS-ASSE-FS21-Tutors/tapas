package ch.unisg.tapas.executor.robot;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RobotService {

    public List<Robot> getRobots() {
        return List.of(
                new Robot(
                        400,
                        200,
                        0,
                        0
                )
        );
    }
}
