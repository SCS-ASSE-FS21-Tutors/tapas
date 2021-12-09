package ch.unisg.tapasexecutor.adapters.out.robot;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class RobotApiTest {

    @Disabled
    @Test
    public void moveRobotTest() throws Exception{

        try(var api = RobotApi.open("https://api.interactions.ics.unisg.ch/leubot1/v1.3.4")) {

            api.dance();
        }
    }

}