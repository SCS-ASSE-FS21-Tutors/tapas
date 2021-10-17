package ch.unisg.tapasroster;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class TapasRosterApplication {

    public static void main(String[] args) {
        SpringApplication.run(TapasRosterApplication.class, args);
    }

}
