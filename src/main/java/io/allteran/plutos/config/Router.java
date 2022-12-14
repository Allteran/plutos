package io.allteran.plutos.config;

import io.allteran.plutos.domain.Country;
import io.allteran.plutos.dto.CompanyDTO;
import io.allteran.plutos.dto.CountryDTO;
import io.allteran.plutos.dto.PrivilegeDTO;
import io.allteran.plutos.dto.SalaryDTO;
import io.allteran.plutos.handler.CompanyHandler;
import io.allteran.plutos.handler.CountryHandler;
import io.allteran.plutos.handler.PrivilegeHandler;
import io.allteran.plutos.handler.SalaryHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;


@Configuration
public class Router {

    @Bean
    @RouterOperations(
            {
                    @RouterOperation(
                            path = "/route/countries/",
                            produces = {
                                    APPLICATION_JSON_VALUE
                            },
                            method = RequestMethod.GET,
                            beanClass = CountryHandler.class,
                            beanMethod = "findAll",
                            operation = @Operation(
                                    operationId = "findAllCountries",
                                    responses = {
                                            @ApiResponse(
                                                    responseCode = "200",
                                                    description = "successful operation",
                                                    content = @Content(schema = @Schema(
                                                            implementation = CountryDTO.class
                                                    ))
                                            )
                                    }
                            )
                    ),
                    @RouterOperation(
                            path = "/route/countries",
                            produces = {
                                    APPLICATION_JSON_VALUE
                            },
                            method = RequestMethod.GET,
                            beanClass = CountryHandler.class,
                            beanMethod = "findAll",
                            operation = @Operation(
                                    operationId = "findAllCountries",
                                    responses = {
                                            @ApiResponse(
                                                    responseCode = "200",
                                                    description = "successful operation",
                                                    content = @Content(schema = @Schema(
                                                            implementation = CountryDTO.class
                                                    ))
                                            )
                                    }
                            )
                    ),
                    @RouterOperation(
                            path = "/route/countries/{id}",
                            produces = {
                                    APPLICATION_JSON_VALUE
                            },
                            method = RequestMethod.GET,
                            beanClass = CountryHandler.class,
                            beanMethod = "findById",
                            operation = @Operation(
                                    operationId = "findCountryById",
                                    responses = {
                                            @ApiResponse(
                                                    responseCode = "200",
                                                    description = "successful operation",
                                                    content = @Content(schema = @Schema(
                                                            implementation = CountryDTO.class
                                                    ))
                                            ),
                                            @ApiResponse(
                                                    responseCode = "404",
                                                    description = "country not found with given ID"
                                            )
                                    },
                                    parameters = {
                                            @Parameter(in = ParameterIn.PATH, name = "id", description = "Country ID")
                                    }
                            )
                    ),
                    @RouterOperation(
                            path = "/route/countries/new",
                            produces = {
                                    APPLICATION_JSON_VALUE
                            },
                            method = RequestMethod.POST,
                            beanClass = CountryHandler.class,
                            beanMethod = "create",
                            operation = @Operation(
                                    operationId = "createCountry",
                                    responses = {
                                            @ApiResponse(
                                                    responseCode = "200",
                                                    description = "successful operation",
                                                    content = @Content(schema = @Schema(
                                                            implementation = CountryDTO.class
                                                    ))
                                            )
                                    },
                                    requestBody = @RequestBody(
                                            content = @Content(schema = @Schema(
                                                    implementation = CountryDTO.class
                                            ))
                                    )
                            )
                    ),
                    @RouterOperation(
                            path = "/route/countries/update/{id}",
                            produces = {
                                    APPLICATION_JSON_VALUE
                            },
                            method = RequestMethod.PUT,
                            beanClass = CountryHandler.class,
                            beanMethod = "update",
                            operation = @Operation(
                                    operationId = "updateCountry",
                                    responses = {
                                            @ApiResponse(
                                                    responseCode = "200",
                                                    description = "successful operation",
                                                    content = @Content(schema = @Schema(
                                                            implementation = CountryDTO.class
                                                    ))
                                            ),
                                            @ApiResponse(
                                                    responseCode = "404",
                                                    description = "Country was not found by given ID"
                                            )
                                    },
                                    parameters = {
                                      @Parameter(in = ParameterIn.PATH, name = "id", description = "Country ID")
                                    },
                                    requestBody = @RequestBody(
                                            content = @Content(schema = @Schema(
                                                    implementation = CountryDTO.class
                                            ))
                                    )
                            )
                    ),
                    @RouterOperation(
                            path = "/route/countries/delete/{id}",
                            produces = {
                                    APPLICATION_JSON_VALUE
                            },
                            method = RequestMethod.DELETE,
                            beanClass = CountryHandler.class,
                            beanMethod = "delete",
                            operation = @Operation(
                                    operationId = "deleteCountry",
                                    responses = {
                                            @ApiResponse(
                                                    responseCode = "200",
                                                    description = "successful operation"
                                            ),
                                            @ApiResponse(
                                                    responseCode = "404",
                                                    description = "Country was not found by given ID"
                                            )
                                    },
                                    parameters = {
                                            @Parameter(in = ParameterIn.PATH, name = "id", description = "Country ID")
                                    }
                            )
                    ),
            }
    )
    public RouterFunction<ServerResponse> countryRoute(CountryHandler handler) {
        return RouterFunctions
                .route()
                .path("/route/countries", builder -> builder
                        .GET("", accept(APPLICATION_JSON), handler::findAll)
                        .GET("/", accept(APPLICATION_JSON), handler::findAll)
                        .GET("/{id}", accept(APPLICATION_JSON), handler::findById)
                        .POST("/new", accept(APPLICATION_JSON), handler::create)
                        .PUT("/update/{id}", accept(APPLICATION_JSON), handler::update)
                        .DELETE("/delete/{id}", accept(APPLICATION_JSON), handler::delete))
                .build();
    }

    @RouterOperations(
            {
                    @RouterOperation(
                            path = "/route/companies/",
                            produces = {
                                    APPLICATION_JSON_VALUE
                            },
                            method = RequestMethod.GET,
                            beanClass = CompanyHandler.class,
                            beanMethod = "findAll",
                            operation = @Operation(
                                    operationId = "findAllCompanies",
                                    responses = {
                                            @ApiResponse(
                                                    responseCode = "200",
                                                    description = "successful operation",
                                                    content = @Content(schema = @Schema(
                                                            implementation = CompanyDTO.class
                                                    ))
                                            )
                                    }
                            )
                    ),
                    @RouterOperation(
                            path = "/route/companies",
                            produces = {
                                    APPLICATION_JSON_VALUE
                            },
                            method = RequestMethod.GET,
                            beanClass = CompanyHandler.class,
                            beanMethod = "findAll",
                            operation = @Operation(
                                    operationId = "findAllCompanies",
                                    responses = {
                                            @ApiResponse(
                                                    responseCode = "200",
                                                    description = "successful operation",
                                                    content = @Content(schema = @Schema(
                                                            implementation = CompanyDTO.class
                                                    ))
                                            )
                                    }
                            )
                    ),
                    @RouterOperation(
                            path = "/route/companies/{id}",
                            produces = {
                                    APPLICATION_JSON_VALUE
                            },
                            method = RequestMethod.GET,
                            beanClass = CompanyHandler.class,
                            beanMethod = "findById",
                            operation = @Operation(
                                    operationId = "findCompanyById",
                                    responses = {
                                            @ApiResponse(
                                                    responseCode = "200",
                                                    description = "successful operation",
                                                    content = @Content(schema = @Schema(
                                                            implementation = CompanyDTO.class
                                                    ))
                                            ),
                                            @ApiResponse(
                                                    responseCode = "404",
                                                    description = "Company not found with given ID"
                                            )
                                    },
                                    parameters = {
                                            @Parameter(in = ParameterIn.PATH, name = "id", description = "Company ID")
                                    }
                            )
                    ),
                    @RouterOperation(
                            path = "/route/companies/new",
                            produces = {
                                    APPLICATION_JSON_VALUE
                            },
                            method = RequestMethod.POST,
                            beanClass = CompanyHandler.class,
                            beanMethod = "create",
                            operation = @Operation(
                                    operationId = "createCompany",
                                    responses = {
                                            @ApiResponse(
                                                    responseCode = "200",
                                                    description = "successful operation",
                                                    content = @Content(schema = @Schema(
                                                            implementation = CompanyDTO.class
                                                    ))
                                            )
                                    },
                                    requestBody = @RequestBody(
                                            content = @Content(schema = @Schema(
                                                    implementation = CompanyDTO.class
                                            ))
                                    )
                            )
                    ),
                    @RouterOperation(
                            path = "/route/companies/update/{id}",
                            produces = {
                                    APPLICATION_JSON_VALUE
                            },
                            method = RequestMethod.PUT,
                            beanClass = CompanyHandler.class,
                            beanMethod = "update",
                            operation = @Operation(
                                    operationId = "updateCompany",
                                    responses = {
                                            @ApiResponse(
                                                    responseCode = "200",
                                                    description = "successful operation",
                                                    content = @Content(schema = @Schema(
                                                            implementation = CompanyDTO.class
                                                    ))
                                            ),
                                            @ApiResponse(
                                                    responseCode = "404",
                                                    description = "Company was not found by given ID"
                                            )
                                    },
                                    parameters = {
                                            @Parameter(in = ParameterIn.PATH, name = "id", description = "Company ID")
                                    },
                                    requestBody = @RequestBody(
                                            content = @Content(schema = @Schema(
                                                    implementation = CompanyDTO.class
                                            ))
                                    )
                            )
                    ),
                    @RouterOperation(
                            path = "/route/companies/delete/{id}",
                            produces = {
                                    APPLICATION_JSON_VALUE
                            },
                            method = RequestMethod.DELETE,
                            beanClass = CompanyHandler.class,
                            beanMethod = "delete",
                            operation = @Operation(
                                    operationId = "deleteCompany",
                                    responses = {
                                            @ApiResponse(
                                                    responseCode = "200",
                                                    description = "successful operation"
                                            ),
                                            @ApiResponse(
                                                    responseCode = "404",
                                                    description = "Company was not found by given ID"
                                            )
                                    },
                                    parameters = {
                                            @Parameter(in = ParameterIn.PATH, name = "id", description = "Company ID")
                                    }
                            )
                    ),
            }
    )
    @Bean
    public RouterFunction<ServerResponse> companyRoute(CompanyHandler handler) {
        return RouterFunctions
                .route()
                .path("/route/companies", builder -> builder
                        .GET("", accept(APPLICATION_JSON), handler::findAll)
                        .GET("/", accept(APPLICATION_JSON), handler::findAll)
                        .GET("/{id}", accept(APPLICATION_JSON), handler::findById)
                        .POST("/new", accept(APPLICATION_JSON), handler::create)
                        .PUT("/update/{id}", accept(APPLICATION_JSON), handler::update)
                        .DELETE("/delete/{id}", accept(APPLICATION_JSON), handler::delete))
                .build();
    }
    @RouterOperations({
            @RouterOperation(
                    path = "/route/privileges",
                    produces = {
                            APPLICATION_JSON_VALUE
                    },
                    method = RequestMethod.GET,
                    beanClass = PrivilegeHandler.class,
                    beanMethod = "findAll",
                    operation = @Operation(
                            operationId = "findAllPrivileges",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "successful operation",
                                            content = @Content(schema = @Schema(
                                                    implementation = PrivilegeDTO.class
                                            ))
                                    )
                            }
                    )
            ),
            @RouterOperation(
                    path = "/route/privileges/",
                    produces = {
                            APPLICATION_JSON_VALUE
                    },
                    method = RequestMethod.GET,
                    beanClass = PrivilegeHandler.class,
                    beanMethod = "findAll",
                    operation = @Operation(
                            operationId = "findAllPrivileges",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "successful operation",
                                            content = @Content(schema = @Schema(
                                                    implementation = PrivilegeDTO.class
                                            ))
                                    )
                            }
                    )
            ),
            @RouterOperation(
                    path = "/route/privileges/{id}",
                    produces = {
                            APPLICATION_JSON_VALUE
                    },
                    method = RequestMethod.GET,
                    beanClass = PrivilegeHandler.class,
                    beanMethod = "findById",
                    operation = @Operation(
                            operationId = "findPrivilegeById",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "successful operation",
                                            content = @Content(schema = @Schema(
                                                    implementation = PrivilegeDTO.class
                                            ))
                                    ),
                                    @ApiResponse(
                                            responseCode = "404",
                                            description = "Privilege was not found by given ID"
                                    )
                            },
                            parameters = {
                                    @Parameter(in = ParameterIn.PATH, name = "id", description = "Privilege ID")
                            }
                    )
            ),
            @RouterOperation(
                    path = "/route/privilege/new",
                    produces = {
                            APPLICATION_JSON_VALUE
                    },
                    method = RequestMethod.POST,
                    beanClass = PrivilegeHandler.class,
                    beanMethod = "create",
                    operation = @Operation(
                            operationId = "createPrivilege",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "successful operation",
                                            content = @Content(schema = @Schema(
                                                    implementation = PrivilegeDTO.class
                                            ))
                                    ),
                                    @ApiResponse(
                                            responseCode = "404",
                                            description = "Privilege was not found by given ID"
                                    )
                            },
                            requestBody = @RequestBody(
                                    content = @Content(schema = @Schema(
                                            implementation = PrivilegeDTO.class
                                    ))
                            )
                    )
            ),
            @RouterOperation(
                    path = "/route/privileges/update/{id}",
                    produces = {
                            APPLICATION_JSON_VALUE
                    },
                    method = RequestMethod.PUT,
                    beanClass = PrivilegeHandler.class,
                    beanMethod = "update",
                    operation = @Operation(
                            operationId = "updatePrivilege",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "successful operation",
                                            content = @Content(schema = @Schema(
                                                    implementation = PrivilegeDTO.class
                                            ))
                                    ),
                                    @ApiResponse(
                                            responseCode = "404",
                                            description = "Privilege was not found by given ID"
                                    )
                            },
                            parameters = {
                                    @Parameter(in = ParameterIn.PATH, name = "id", description = "Privilege ID")
                            }
                    )
            ),
            @RouterOperation(
                    path = "/route/privileges/delete/{id}",
                    produces = {
                            APPLICATION_JSON_VALUE
                    },
                    method = RequestMethod.DELETE,
                    beanClass = PrivilegeHandler.class,
                    beanMethod = "delete",
                    operation = @Operation(
                            operationId = "deletePrivilege",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "successful operation"
                                    ),
                                    @ApiResponse(
                                            responseCode = "404",
                                            description = "Privilege was not found by given ID"
                                    )
                            },
                            parameters = {
                                    @Parameter(in = ParameterIn.PATH, name = "id", description = "Privilege ID")
                            }
                    )
            )

    })
    @Bean
    public RouterFunction<ServerResponse> privilegeRoute(PrivilegeHandler handler) {
        return RouterFunctions
                .route()
                .path("/route/privileges", builder -> builder
                        .GET("", accept(APPLICATION_JSON), handler::findAll)
                        .GET("/", accept(APPLICATION_JSON), handler::findAll)
                        .GET("/{id}", accept(APPLICATION_JSON), handler::findById)
                        .POST("/new", accept(APPLICATION_JSON), handler::create)
                        .PUT("/update/{id}", accept(APPLICATION_JSON), handler::update)
                        .DELETE("/delete/{id}", accept(APPLICATION_JSON), handler::delete))
                .build();
    }

    @RouterOperations({
            @RouterOperation(
                    path = "/route/salary",
                    produces = {
                            APPLICATION_JSON_VALUE
                    },
                    method = RequestMethod.GET,
                    beanClass = SalaryHandler.class,
                    beanMethod = "findAll",
                    operation = @Operation(
                            operationId = "findAllSalary",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "successful operation",
                                            content = @Content(schema = @Schema(
                                                    implementation = SalaryDTO.class
                                            ))
                                    )
                            }
                    )
            ),
            @RouterOperation(
                    path = "/route/salary/",
                    produces = {
                            APPLICATION_JSON_VALUE
                    },
                    method = RequestMethod.GET,
                    beanClass = SalaryHandler.class,
                    beanMethod = "findAll",
                    operation = @Operation(
                            operationId = "findAllSalary",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "successful operation",
                                            content = @Content(schema = @Schema(
                                                    implementation = SalaryDTO.class
                                            ))
                                    )
                            }
                    )
            ),
            @RouterOperation(
                    path = "/route/salary/{id}",
                    produces = {
                            APPLICATION_JSON_VALUE
                    },
                    method = RequestMethod.GET,
                    beanClass = SalaryHandler.class,
                    beanMethod = "findById",
                    operation = @Operation(
                            operationId = "findSalaryById",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "successful operation",
                                            content = @Content(schema = @Schema(
                                                    implementation = SalaryDTO.class
                                            ))
                                    ),
                                    @ApiResponse(
                                            responseCode = "404",
                                            description = "Can't find Salary with given ID"
                                    )
                            },
                            parameters = {
                                    @Parameter(in = ParameterIn.PATH, name = "id", description = "Salary ID")
                            }
                    )
            ),
            @RouterOperation(
                    path = "/route/salary/search/user",
                    produces = {
                            APPLICATION_JSON_VALUE
                    },
                    method = RequestMethod.GET,
                    beanClass = SalaryHandler.class,
                    beanMethod = "findByUser",
                    operation = @Operation(
                            operationId = "findSalaryByUserId",
                            description = "Find list of Salary based on given UserID",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "successful operation",
                                            content = @Content(schema = @Schema(
                                                    implementation = SalaryDTO.class
                                            ))
                                    ),
                                    @ApiResponse(
                                            responseCode = "404",
                                            description = "Can't find Salary with given UserID"
                                    )
                            },
                            parameters = {
                                    @Parameter(in = ParameterIn.QUERY, name = "userId", description = "User ID")
                            }
                    )
            ),
            @RouterOperation(
                    path = "/route/salary/search/user-date",
                    produces = {
                            APPLICATION_JSON_VALUE
                    },
                    method = RequestMethod.GET,
                    beanClass = SalaryHandler.class,
                    beanMethod = "findByUserDate",
                    operation = @Operation(
                            operationId = "findSalaryByUserIdAndDateRange",
                            description = "Find list of Salary based on given UserID and date range (for shiftStart)",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "successful operation",
                                            content = @Content(schema = @Schema(
                                                    implementation = SalaryDTO.class
                                            ))
                                    ),
                                    @ApiResponse(
                                            responseCode = "404"
                                    )
                            },
                            parameters = {
                                    @Parameter(in = ParameterIn.QUERY, name = "userId", description = "User ID"),
                                    @Parameter(in = ParameterIn.QUERY, name = "from", description = "Time from you need to filter salary by User"),
                                    @Parameter(in = ParameterIn.QUERY, name = "to", description = "Time to you need to filter salary by User")
                            }
                    )

            ),
            @RouterOperation(
                    path = "/route/salary/search/user-company",
                    produces = {
                            APPLICATION_JSON_VALUE
                    },
                    method = RequestMethod.GET,
                    beanClass = SalaryHandler.class,
                    beanMethod = "findByUserCompany",
                    operation = @Operation(
                            operationId = "findSalaryByUserIdAndCompanyId",
                            description = "Find list of Salary based on given UserID and CompanyID",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "successful operation",
                                            content = @Content(schema = @Schema(
                                                    implementation = SalaryDTO.class
                                            ))
                                    )
                            },
                            parameters = {
                                    @Parameter(in = ParameterIn.QUERY, name = "userId", description = "User ID"),
                                    @Parameter(in = ParameterIn.QUERY, name = "companyId", description = "Company ID"),

                            }
                    )
            ),
            @RouterOperation(
                    path = "/route/salary/new",
                    produces = {
                            APPLICATION_JSON_VALUE
                    },
                    method = RequestMethod.POST,
                    beanClass = SalaryHandler.class,
                    beanMethod = "create",
                    operation = @Operation(
                            operationId = "createSalary",
                            description = "Creates new Salary entity for certain User",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "successful operation",
                                            content = @Content(schema = @Schema(
                                                    implementation = SalaryDTO.class
                                            ))
                                    )
                            },
                            requestBody = @RequestBody(
                                    content = @Content(schema = @Schema(
                                            implementation = SalaryDTO.class
                                    ))
                            )
                    )
            ),
            @RouterOperation(
                    path = "/route/salary/update/{id}",
                    produces = {
                            APPLICATION_JSON_VALUE
                    },
                    method = RequestMethod.PUT,
                    beanClass = SalaryHandler.class,
                    beanMethod = "update",
                    operation = @Operation(
                            operationId = "updateSalary",
                            description = "Updates existing Salary entity",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "successful operation",
                                            content = @Content(schema = @Schema(
                                                    implementation = SalaryDTO.class
                                            ))
                                    ),
                                    @ApiResponse(
                                            responseCode = "404",
                                            description = "Can't find Salary with given ID"
                                    )
                            },
                            parameters = {
                                    @Parameter(in = ParameterIn.PATH, name = "id", description = "Salary ID")
                            },
                            requestBody = @RequestBody(
                                    content = @Content(schema = @Schema(
                                            implementation = SalaryDTO.class
                                    ))
                            )
                    )
            ),
            @RouterOperation(
                    path = "/route/salary/delete/{id}",
                    produces = {
                            APPLICATION_JSON_VALUE
                    },
                    method = RequestMethod.DELETE,
                    beanClass = SalaryHandler.class,
                    beanMethod = "delete",
                    operation = @Operation(
                            operationId = "deleteSalaryById",
                            description = "Deletes Salary entity by given ID as path variable",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "successful operation"
                                    ),
                                    @ApiResponse(
                                            responseCode = "404",
                                            description = "Can't find Salary to delete with given ID"
                                    )
                            },
                            parameters = {
                                    @Parameter(in = ParameterIn.PATH, name = "id", description = "Salary ID")
                            }
                    )
            )
    })
    @Bean
    public RouterFunction<ServerResponse> salaryRoute(SalaryHandler handler) {
        RouterFunction<ServerResponse> router = RouterFunctions
                .route()
                .path("/route/salary", builder -> builder
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
