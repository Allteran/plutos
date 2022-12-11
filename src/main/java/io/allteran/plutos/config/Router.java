package io.allteran.plutos.config;

import io.allteran.plutos.handler.CountryHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.*;

@Configuration
public class Router {

    @Bean
    public RouterFunction<ServerResponse> countryRoute(CountryHandler handler) {
        return RouterFunctions
                .route(RequestPredicates.GET("/hello")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), handler::hello)
                .andRoute(RequestPredicates.GET("/list")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), handler::getList)
                .andRoute(RequestPredicates.POST("/countries/new")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), handler::create)
                .andRoute(RequestPredicates.PUT("countries/update/{id}")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), handler::update)
                .andRoute(RequestPredicates.DELETE("countries/delete/{id}"), handler::delete);
    }
}
