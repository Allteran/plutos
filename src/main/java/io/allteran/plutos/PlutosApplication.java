package io.allteran.plutos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
public class PlutosApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlutosApplication.class, args);
    }

}
