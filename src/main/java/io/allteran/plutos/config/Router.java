package io.allteran.plutos.config;

import io.allteran.plutos.handler.CompanyHandler;
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
                .andRoute(RequestPredicates.GET("/countries")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), handler::findAll)
                .andRoute(RequestPredicates.GET("/countries/{id}")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), handler::findById)
                .andRoute(RequestPredicates.POST("/countries/new")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), handler::create)
                .andRoute(RequestPredicates.PUT("countries/update/{id}")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), handler::update)
                .andRoute(RequestPredicates.DELETE("countries/delete/{id}"), handler::delete);
    }

    @Bean
    public RouterFunction<ServerResponse> companyRoute(CompanyHandler handler) {
        return RouterFunctions
                .route(RequestPredicates.GET("/companies")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), handler::findAll)
                .andRoute(RequestPredicates.GET("/companies/{id}")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), handler::findById)
                .andRoute(RequestPredicates.POST("/companies/new")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), handler::create)
                .andRoute(RequestPredicates.PUT("companies/update/{id}")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), handler::update)
                .andRoute(RequestPredicates.DELETE("companies/delete/{id}"), handler::delete);
    }
}
