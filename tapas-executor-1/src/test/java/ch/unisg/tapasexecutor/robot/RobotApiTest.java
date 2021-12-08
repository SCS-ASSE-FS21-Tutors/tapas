package ch.unisg.tapasexecutor.robot;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RobotApiTest {

    @Disabled
    @Test
    public void moveRobotTest() throws Exception{

        try(var api = RobotApi.open("https://api.interactions.ics.unisg.ch/leubot1/v1.3.4")) {

            api.dance();
        }
    }

}