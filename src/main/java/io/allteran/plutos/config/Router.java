package io.allteran.plutos.config;

import io.allteran.plutos.handler.CompanyHandler;
import io.allteran.plutos.handler.CountryHandler;
import io.allteran.plutos.handler.PrivilegeHandler;
import io.allteran.plutos.handler.SalaryHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.*;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;


@Configuration
public class Router {

    @Bean
    public RouterFunction<ServerResponse> countryRoute(CountryHandler handler) {
        return RouterFunctions
                .route(GET("/hello")
                        .and(accept(APPLICATION_JSON)), handler::hello)
                .andRoute(GET("/countries")
                        .and(accept(APPLICATION_JSON)), handler::findAll)
                .andRoute(GET("/countries/{id}")
                        .and(accept(APPLICATION_JSON)), handler::findById)
                .andRoute(POST("/countries/new")
                        .and(accept(APPLICATION_JSON)), handler::create)
                .andRoute(PUT("countries/update/{id}")
                        .and(accept(APPLICATION_JSON)), handler::update)
                .andRoute(DELETE("countries/delete/{id}"), handler::delete);
    }

    @Bean
    public RouterFunction<ServerResponse> companyRoute(CompanyHandler handler) {
        return RouterFunctions
                .route(GET("/companies")
                        .and(accept(APPLICATION_JSON)), handler::findAll)
                .andRoute(GET("/companies/{id}")
                        .and(accept(APPLICATION_JSON)), handler::findById)
                .andRoute(POST("/companies/new")
                        .and(accept(APPLICATION_JSON)), handler::create)
                .andRoute(PUT("companies/update/{id}")
                        .and(accept(APPLICATION_JSON)), handler::update)
                .andRoute(DELETE("companies/delete/{id}"), handler::delete);
    }
    @Bean
    public RouterFunction<ServerResponse> privilegeRoute(PrivilegeHandler handler) {
        return RouterFunctions
                .route(GET("/privileges")
                        .and(accept(APPLICATION_JSON)), handler::findAll)
                .andRoute(GET("/privileges/{id}")
                        .and(accept(APPLICATION_JSON)), handler::findById)
                .andRoute(POST("/privileges/new")
                        .and(accept(APPLICATION_JSON)), handler::create)
                .andRoute(PUT("privileges/update/{id}")
                        .and(accept(APPLICATION_JSON)), handler::update)
                .andRoute(DELETE("privileges/delete/{id}"), handler::delete);
    }

    @Bean
    public RouterFunction<ServerResponse> salaryRoute(SalaryHandler handler) {
        RouterFunction<ServerResponse> router = RouterFunctions
                .route()
                .path("/salary", builder -> builder
                        .GET("/", accept(APPLICATION_JSON), handler::findAll)
                        .GET("", accept(APPLICATION_JSON), handler::findAll)
                        .GET("/{id}", accept(APPLICATION_JSON), handler::findById)
                        .GET("/search/user", accept(APPLICATION_JSON), handler::findByUser)
                        .GET("/search/user-date", accept(APPLICATION_JSON), handler::findByUserDate)
                        .GET("/search/user-company", accept(APPLICATION_JSON), handler::findByUserCompany)
                        .POST("/new", accept(APPLICATION_JSON), handler::create)
                        .PUT("/update/{id}", accept(APPLICATION_JSON), handler::update)
                        .DELETE("/delete/{id}", accept(APPLICATION_JSON), handler::delete))
                .build();
        return router;
    }

}
