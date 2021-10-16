package ch.unisg.tapas.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

public class CalculatorApplication {
    public static void main(String[] args) {

        SpringApplication CalculatorApp = new SpringApplication(CalculatorApplication.class);
        CalculatorApp.run(args);
    }

}
