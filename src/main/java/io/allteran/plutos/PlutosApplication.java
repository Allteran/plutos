package io.allteran.plutos;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
        title = "Plutos: shift calculator",
        version = "0.1",
        description = "Simple CRUD application built on Spring WebFlux and reactive stack"
))
public class PlutosApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlutosApplication.class, args);
    }

}
