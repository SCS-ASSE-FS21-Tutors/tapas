package ch.unisg.tapasbase;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Enables some shared operational for all tapas microservices.
 * This includes:
 *
 * - Request logging
 * - OpenAPI Specification
 * - Hello Controller
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({MicroserviceConfiguration.class})
public @interface TapasMicroservice {

}
