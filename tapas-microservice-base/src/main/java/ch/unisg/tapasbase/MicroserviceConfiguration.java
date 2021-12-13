package ch.unisg.tapasbase;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.filter.ForwardedHeaderFilter;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.util.List;

/**
 * This class provides a basic configuration for all microservices
 */
public class MicroserviceConfiguration {

    @Value("${spring.application.name:Please set spring.application.name}")
    private String appName;

    /**
     * Configures Spring to log each request
     * @return
     */
    @Bean
    public CommonsRequestLoggingFilter logFilter() {

        CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
        filter.setIncludeQueryString(true);
        filter.setIncludePayload(true);
        filter.setMaxPayloadLength(10000);
        filter.setIncludeHeaders(false);
        filter.setAfterMessagePrefix("REQUEST DATA : ");
        return filter;
    }

    /**
     * Adds the OpenAPI specification
     * @return
     */
    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI()
                .components(new Components())
                .info(new Info().title(appName + " Group 3"));
    }

    /**
     * Adds a controller that serves a hello page on the root
     * of the webservice
     * @return
     */
    @Bean
    public HelloController helloController(@Autowired SpringTemplateEngine templateEngine){

        return new HelloController(appName, templateEngine);
    }

    @Bean
    ForwardedHeaderFilter forwardedHeaderFilter() {
        return new ForwardedHeaderFilter();
    }


}
