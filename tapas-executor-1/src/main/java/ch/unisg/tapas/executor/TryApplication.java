package ch.unisg.tapas.executor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class TryApplication {

	public static void main(String[] args) {
		SpringApplication.run(TryApplication.class, args);
	}

}
