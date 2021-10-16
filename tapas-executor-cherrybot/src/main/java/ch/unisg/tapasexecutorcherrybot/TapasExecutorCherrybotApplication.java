package ch.unisg.tapasexecutorcherrybot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class TapasExecutorCherrybotApplication {

    public static void main(String[] args) {
        SpringApplication.run(TapasExecutorCherrybotApplication.class, args);
    }

}
