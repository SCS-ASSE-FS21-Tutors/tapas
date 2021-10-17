package ch.unisg.tapas.roster;

import ch.unisg.tapas.roster.services.BasicRosterService;
import ch.unisg.tapas.roster.services.RosterService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@OpenAPIDefinition(info = @Info(title = "Roster-API", version = "1"))
@SpringBootApplication
public class Application {

    @Value( "${ch.unisg.tapas.executor-pool-url}" )
    private String executorPoolUrl;

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);
        
    }

    @Bean
    public RosterService rosterService(){
        return new BasicRosterService(executorPoolUrl);
    }

}
