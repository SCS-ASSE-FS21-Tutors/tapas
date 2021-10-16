package ch.unisg.tapasexecutordigital;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class TapasExecutorDigitalApplication {

    public static void main(String[] args) {
        SpringApplication.run(TapasExecutorDigitalApplication.class, args);
    }

}
