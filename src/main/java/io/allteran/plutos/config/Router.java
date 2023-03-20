package io.allteran.plutos.config;

import io.allteran.plutos.dto.*;
import io.allteran.plutos.handler.*;
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
                                            ),
                                            @ApiResponse(
                                                    responseCode = "401",
                                                    description = "User unauthorized"
                                            ),
                                            @ApiResponse(
                                                    responseCode = "403",
                                                    description = "Forbidden: access denied due to access politic"
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
                                            ),
                                            @ApiResponse(
                                                    responseCode = "401",
                                                    description = "User unauthorized"
                                            ),
                                            @ApiResponse(
                                                    responseCode = "403",
                                                    description = "Forbidden: access denied due to access politic"
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
                                            ),
                                            @ApiResponse(
                                                    responseCode = "401",
                                                    description = "User unauthorized"
                                            ),
                                            @ApiResponse(
                                                    responseCode = "403",
                                                    description = "Forbidden: access denied due to access politic"
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
                                            ),
                                            @ApiResponse(
                                                    responseCode = "401",
                                                    description = "User unauthorized"
                                            ),
                                            @ApiResponse(
                                                    responseCode = "403",
                                                    description = "Forbidden: access denied due to access politic"
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
                                            ),
                                            @ApiResponse(
                                                    responseCode = "401",
                                                    description = "User unauthorized"
                                            ),
                                            @ApiResponse(
                                                    responseCode = "403",
                                                    description = "Forbidden: access denied due to access politic"
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
                                            ),
                                            @ApiResponse(
                                                    responseCode = "401",
                                                    description = "User unauthorized"
                                            ),
                                            @ApiResponse(
                                                    responseCode = "403",
                                                    description = "Forbidden: access denied due to access politic"
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
                                            ),
                                            @ApiResponse(
                                                    responseCode = "401",
                                                    description = "User unauthorized"
                                            ),
                                            @ApiResponse(
                                                    responseCode = "403",
                                                    description = "Forbidden: access denied due to access politic"
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
                                            ),
                                            @ApiResponse(
                                                    responseCode = "401",
                                                    description = "User unauthorized"
                                            ),
                                            @ApiResponse(
                                                    responseCode = "403",
                                                    description = "Forbidden: access denied due to access politic"
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
                                            ),
                                            @ApiResponse(
                                                    responseCode = "401",
                                                    description = "User unauthorized"
                                            ),
                                            @ApiResponse(
                                                    responseCode = "403",
                                                    description = "Forbidden: access denied due to access politic"
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
                                            ),
                                            @ApiResponse(
                                                    responseCode = "401",
                                                    description = "User unauthorized"
                                            ),
                                            @ApiResponse(
                                                    responseCode = "403",
                                                    description = "Forbidden: access denied due to access politic"
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
                                            ),
                                            @ApiResponse(
                                                    responseCode = "401",
                                                    description = "User unauthorized"
                                            ),
                                            @ApiResponse(
                                                    responseCode = "403",
                                                    description = "Forbidden: access denied due to access politic"
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
                                    ),
                                    @ApiResponse(
                                            responseCode = "401",
                                            description = "User unauthorized"
                                    ),
                                    @ApiResponse(
                                            responseCode = "403",
                                            description = "Forbidden: access denied due to access politic"
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
                                    ),
                                    @ApiResponse(
                                            responseCode = "401",
                                            description = "User unauthorized"
                                    ),
                                    @ApiResponse(
                                            responseCode = "403",
                                            description = "Forbidden: access denied due to access politic"
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
                                    ),
                                    @ApiResponse(
                                            responseCode = "401",
                                            description = "User unauthorized"
                                    ),
                                    @ApiResponse(
                                            responseCode = "403",
                                            description = "Forbidden: access denied due to access politic"
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
                                    ),
                                    @ApiResponse(
                                            responseCode = "401",
                                            description = "User unauthorized"
                                    ),
                                    @ApiResponse(
                                            responseCode = "403",
                                            description = "Forbidden: access denied due to access politic"
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
                                    ),
                                    @ApiResponse(
                                            responseCode = "401",
                                            description = "User unauthorized"
                                    ),
                                    @ApiResponse(
                                            responseCode = "403",
                                            description = "Forbidden: access denied due to access politic"
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
                                    ),
                                    @ApiResponse(
                                            responseCode = "401",
                                            description = "User unauthorized"
                                    ),
                                    @ApiResponse(
                                            responseCode = "403",
                                            description = "Forbidden: access denied due to access politic"
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
                    path = "/route/shifts",
                    produces = {
                            APPLICATION_JSON_VALUE
                    },
                    method = RequestMethod.GET,
                    beanClass = ShiftHandler.class,
                    beanMethod = "findAll",
                    operation = @Operation(
                            operationId = "findAllShifts",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "successful operation",
                                            content = @Content(schema = @Schema(
                                                    implementation = ShiftDTO.class
                                            ))
                                    ),
                                    @ApiResponse(
                                            responseCode = "401",
                                            description = "User unauthorized"
                                    ),
                                    @ApiResponse(
                                            responseCode = "403",
                                            description = "Forbidden: access denied due to access politic"
                                    )
                            }
                    )
            ),
            @RouterOperation(
                    path = "/route/shifts/",
                    produces = {
                            APPLICATION_JSON_VALUE
                    },
                    method = RequestMethod.GET,
                    beanClass = ShiftHandler.class,
                    beanMethod = "findAll",
                    operation = @Operation(
                            operationId = "findAllShifts",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "successful operation",
                                            content = @Content(schema = @Schema(
                                                    implementation = ShiftDTO.class
                                            ))
                                    ),
                                    @ApiResponse(
                                            responseCode = "401",
                                            description = "User unauthorized"
                                    ),
                                    @ApiResponse(
                                            responseCode = "403",
                                            description = "Forbidden: access denied due to access politic"
                                    )
                            }
                    )
            ),
            @RouterOperation(
                    path = "/route/shifts/{id}",
                    produces = {
                            APPLICATION_JSON_VALUE
                    },
                    method = RequestMethod.GET,
                    beanClass = ShiftHandler.class,
                    beanMethod = "findById",
                    operation = @Operation(
                            operationId = "findShiftById",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "successful operation",
                                            content = @Content(schema = @Schema(
                                                    implementation = ShiftDTO.class
                                            ))
                                    ),
                                    @ApiResponse(
                                            responseCode = "404",
                                            description = "Can't find Shift with given ID"
                                    ),
                                    @ApiResponse(
                                            responseCode = "401",
                                            description = "User unauthorized"
                                    ),
                                    @ApiResponse(
                                            responseCode = "403",
                                            description = "Forbidden: access denied due to access politic"
                                    )
                            },
                            parameters = {
                                    @Parameter(in = ParameterIn.PATH, name = "id", description = "Shift ID")
                            }
                    )
            ),
            @RouterOperation(
                    path = "/route/shifts/search/user",
                    produces = {
                            APPLICATION_JSON_VALUE
                    },
                    method = RequestMethod.GET,
                    beanClass = ShiftHandler.class,
                    beanMethod = "findByUser",
                    operation = @Operation(
                            operationId = "findShiftByUserId",
                            description = "Find list of Shift based on given UserID",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "successful operation",
                                            content = @Content(schema = @Schema(
                                                    implementation = ShiftDTO.class
                                            ))
                                    ),
                                    @ApiResponse(
                                            responseCode = "404",
                                            description = "Can't find Shift with given UserID"
                                    ),
                                    @ApiResponse(
                                            responseCode = "401",
                                            description = "User unauthorized"
                                    ),
                                    @ApiResponse(
                                            responseCode = "403",
                                            description = "Forbidden: access denied due to access politic"
                                    )
                            },
                            parameters = {
                                    @Parameter(in = ParameterIn.QUERY, name = "userId", description = "User ID")
                            }
                    )
            ),
            @RouterOperation(
                    path = "/route/shifts/search/user-date",
                    produces = {
                            APPLICATION_JSON_VALUE
                    },
                    method = RequestMethod.GET,
                    beanClass = ShiftHandler.class,
                    beanMethod = "findByUserDate",
                    operation = @Operation(
                            operationId = "findShiftByUserIdAndDateRange",
                            description = "Find list of Shift based on given UserID and date range (for shiftStart)",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "successful operation",
                                            content = @Content(schema = @Schema(
                                                    implementation = ShiftDTO.class
                                            ))
                                    ),
                                    @ApiResponse(
                                            responseCode = "404"
                                    ),
                                    @ApiResponse(
                                            responseCode = "401",
                                            description = "User unauthorized"
                                    ),
                                    @ApiResponse(
                                            responseCode = "403",
                                            description = "Forbidden: access denied due to access politic"
                                    )
                            },
                            parameters = {
                                    @Parameter(in = ParameterIn.QUERY, name = "userId", description = "User ID"),
                                    @Parameter(in = ParameterIn.QUERY, name = "from", description = "Time from you need to filter Shift by User"),
                                    @Parameter(in = ParameterIn.QUERY, name = "to", description = "Time to you need to filter Shift by User")
                            }
                    )

            ),
            @RouterOperation(
                    path = "/route/shifts/search/user-company",
                    produces = {
                            APPLICATION_JSON_VALUE
                    },
                    method = RequestMethod.GET,
                    beanClass = ShiftHandler.class,
                    beanMethod = "findByUserCompany",
                    operation = @Operation(
                            operationId = "findShiftByUserIdAndCompanyId",
                            description = "Find list of Shifts based on given UserID and CompanyID",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "successful operation",
                                            content = @Content(schema = @Schema(
                                                    implementation = ShiftDTO.class
                                            ))
                                    ),
                                    @ApiResponse(
                                            responseCode = "401",
                                            description = "User unauthorized"
                                    ),
                                    @ApiResponse(
                                            responseCode = "403",
                                            description = "Forbidden: access denied due to access politic"
                                    )
                            },
                            parameters = {
                                    @Parameter(in = ParameterIn.QUERY, name = "userId", description = "User ID"),
                                    @Parameter(in = ParameterIn.QUERY, name = "companyId", description = "Company ID"),

                            }
                    )
            ),
            @RouterOperation(
                    path = "/route/shifts/new",
                    produces = {
                            APPLICATION_JSON_VALUE
                    },
                    method = RequestMethod.POST,
                    beanClass = ShiftHandler.class,
                    beanMethod = "create",
                    operation = @Operation(
                            operationId = "createShift",
                            description = "Creates new Shift entity for certain User",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "successful operation",
                                            content = @Content(schema = @Schema(
                                                    implementation = ShiftDTO.class
                                            ))
                                    ),
                                    @ApiResponse(
                                            responseCode = "401",
                                            description = "User unauthorized"
                                    ),
                                    @ApiResponse(
                                            responseCode = "403",
                                            description = "Forbidden: access denied due to access politic"
                                    )
                            },
                            requestBody = @RequestBody(
                                    content = @Content(schema = @Schema(
                                            implementation = ShiftDTO.class
                                    ))
                            )
                    )
            ),
            @RouterOperation(
                    path = "/route/shifts/update/{id}",
                    produces = {
                            APPLICATION_JSON_VALUE
                    },
                    method = RequestMethod.PUT,
                    beanClass = ShiftHandler.class,
                    beanMethod = "update",
                    operation = @Operation(
                            operationId = "updateShift",
                            description = "Updates existing Shift entity",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "successful operation",
                                            content = @Content(schema = @Schema(
                                                    implementation = ShiftDTO.class
                                            ))
                                    ),
                                    @ApiResponse(
                                            responseCode = "404",
                                            description = "Can't find Shift with given ID"
                                    ),
                                    @ApiResponse(
                                            responseCode = "401",
                                            description = "User unauthorized"
                                    ),
                                    @ApiResponse(
                                            responseCode = "403",
                                            description = "Forbidden: access denied due to access politic"
                                    )
                            },
                            parameters = {
                                    @Parameter(in = ParameterIn.PATH, name = "id", description = "Shift ID")
                            },
                            requestBody = @RequestBody(
                                    content = @Content(schema = @Schema(
                                            implementation = ShiftDTO.class
                                    ))
                            )
                    )
            ),
            @RouterOperation(
                    path = "/route/shifts/delete/{id}",
                    produces = {
                            APPLICATION_JSON_VALUE
                    },
                    method = RequestMethod.DELETE,
                    beanClass = ShiftHandler.class,
                    beanMethod = "delete",
                    operation = @Operation(
                            operationId = "deleteShiftById",
                            description = "Deletes Shift entity by given ID as path variable",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "successful operation"
                                    ),
                                    @ApiResponse(
                                            responseCode = "404",
                                            description = "Can't find Shift to delete with given ID"
                                    ),
                                    @ApiResponse(
                                            responseCode = "401",
                                            description = "User unauthorized"
                                    ),
                                    @ApiResponse(
                                            responseCode = "403",
                                            description = "Forbidden: access denied due to access politic"
                                    )
                            },
                            parameters = {
                                    @Parameter(in = ParameterIn.PATH, name = "id", description = "Shift ID")
                            }
                    )
            )
    })
    @Bean
    public RouterFunction<ServerResponse> shiftRoute(ShiftHandler handler) {
        RouterFunction<ServerResponse> router = RouterFunctions
                .route()
                .path("/route/shifts", builder -> builder
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
    @RouterOperations({
            @RouterOperation(
                    path = "/route/adm/users",
                    produces = {
                            APPLICATION_JSON_VALUE
                    },
                    method = RequestMethod.GET,
                    beanClass = UserHandler.class,
                    beanMethod = "findAll",
                    operation = @Operation(
                            operationId = "findAllUsers",
                            description = "SPECIAL PERMISSIONS REQUIRED! Find list of all Users",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "successful operation",
                                            content = @Content(schema = @Schema(
                                                    implementation = UserDTO.class
                                            ))
                                    ),
                                    @ApiResponse(
                                            responseCode = "401",
                                            description = "User unauthorized"
                                    ),
                                    @ApiResponse(
                                            responseCode = "403",
                                            description = "Forbidden: access denied due to access politic"
                                    )
                            }
                    )
            ),
            @RouterOperation(
                    path = "/route/adm/users/{id}",
                    produces = {
                            APPLICATION_JSON_VALUE
                    },
                    method = RequestMethod.GET,
                    beanClass = UserHandler.class,
                    beanMethod = "findById",
                    operation = @Operation(
                            operationId = "findUserById",
                            description = "SPECIAL PERMISSIONS REQUIRED! Find one USER based on given User ID",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "successful operation",
                                            content = @Content(schema = @Schema(
                                                    implementation = UserDTO.class
                                            ))
                                    ),
                                    @ApiResponse(
                                            responseCode = "401",
                                            description = "User unauthorized"
                                    ),
                                    @ApiResponse(
                                            responseCode = "403",
                                            description = "Forbidden: access denied due to access politic"
                                    ),
                                    @ApiResponse(
                                            responseCode = "404",
                                            description = "Can't find USER with given ID"
                                    )
                            },
                            parameters = {
                                    @Parameter(in = ParameterIn.PATH, name = "id", description = "User ID")
                            }
                    )
            ),
            @RouterOperation(
                    path = "/route/adm/users/search/email",
                    produces = {
                            APPLICATION_JSON_VALUE
                    },
                    method = RequestMethod.GET,
                    beanClass = UserHandler.class,
                    beanMethod = "findByEmail",
                    operation = @Operation(
                            operationId = "findUserByEmail",
                            description = "SPECIAL PERMISSIONS REQUIRED! Find one USER based on given email",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "successful operation",
                                            content = @Content(schema = @Schema(
                                                    implementation = UserDTO.class
                                            ))
                                    ),
                                    @ApiResponse(
                                            responseCode = "401",
                                            description = "User unauthorized"
                                    ),
                                    @ApiResponse(
                                            responseCode = "403",
                                            description = "Forbidden: access denied due to access politic"
                                    ),
                                    @ApiResponse(
                                            responseCode = "404",
                                            description = "Can't find USER with given EMAIL"
                                    )
                            },
                            parameters = {
                                    @Parameter(in = ParameterIn.QUERY, name = "email", description = "User EMAIL (unique)")
                            }
                    )
            ),
            @RouterOperation(
                    path = "/route/adm/users/search/fname",
                    produces = {
                            APPLICATION_JSON_VALUE
                    },
                    method = RequestMethod.GET,
                    beanClass = UserHandler.class,
                    beanMethod = "findByFirstName",
                    operation = @Operation(
                            operationId = "findUserByFirstName",
                            description = "SPECIAL PERMISSION REQUIRED! Find list of USERS based on given FIRST NAME",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "successful operation",
                                            content = @Content(schema = @Schema(
                                                    implementation = UserDTO.class
                                            ))
                                    ),
                                    @ApiResponse(
                                            responseCode = "401",
                                            description = "User unauthorized"
                                    ),
                                    @ApiResponse(
                                            responseCode = "403",
                                            description = "Forbidden: access denied due to access politic"
                                    ),
                                    @ApiResponse(
                                            responseCode = "404",
                                            description = "Can't find any User with given FIRST NAME"
                                    )
                            },
                            parameters = {
                                    @Parameter(in = ParameterIn.QUERY, name = "firstName", description = "User FIRST NAME")
                            }
                    )
            ),
            @RouterOperation(
                    path = "/route/adm/users/lname",
                    produces = {
                            APPLICATION_JSON_VALUE
                    },
                    method = RequestMethod.GET,
                    beanClass = UserHandler.class,
                    beanMethod = "findByLastName",
                    operation = @Operation(
                            operationId = "findUsersByLastName",
                            description = "SPECIAL PERMISSION REQUIRED! Find list of Users based on given LAST NAME",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "successful operation",
                                            content = @Content(schema = @Schema(
                                                    implementation = UserDTO.class
                                            ))
                                    ),
                                    @ApiResponse(
                                            responseCode = "401",
                                            description = "User unauthorized"
                                    ),
                                    @ApiResponse(
                                            responseCode = "403",
                                            description = "Forbidden: access denied due to access politic"
                                    ),
                                    @ApiResponse(
                                            responseCode = "404",
                                            description = "Can't find any User with given LAST NAME"
                                    )
                            },
                            parameters = {
                                    @Parameter(in = ParameterIn.QUERY, name = "lastName", description = "User LAST NAME")
                            }
                    )
            ),
            @RouterOperation(
                    path = "/route/adm/users/search/country",
                    produces = {
                            APPLICATION_JSON_VALUE
                    },
                    method = RequestMethod.GET,
                    beanClass = UserHandler.class,
                    beanMethod = "findByCountry",
                    operation = @Operation(
                            operationId = "findUsersByCountry",
                            description = "SPECIAL PERMISSIONS REQUIRED! Find list of Users with given COUNTRY ID",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "successful operation",
                                            content = @Content(schema = @Schema(
                                                    implementation = UserDTO.class
                                            ))
                                    ),
                                    @ApiResponse(
                                            responseCode = "401",
                                            description = "User unauthorized"
                                    ),
                                    @ApiResponse(
                                            responseCode = "403",
                                            description = "Forbidden: access denied due to access politic"
                                    ),
                                    @ApiResponse(
                                            responseCode = "404",
                                            description = "Can't find any User with given COUNTRY ID"
                                    )
                            },
                            parameters = {
                                    @Parameter(in = ParameterIn.QUERY, name = "countryId", description = "Country ID for User")
                            }
                    )
            ),
            @RouterOperation(
                    path = "/route/adm/users/new",
                    produces = {
                            APPLICATION_JSON_VALUE
                    },
                    method = RequestMethod.POST,
                    beanClass = UserHandler.class,
                    beanMethod = "create",
                    operation = @Operation(
                            operationId = "createNewUser",
                            description = "SPECIAL PERMISSIONS REQUIRED! Creates new User matched to UserDTO",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "successful operation",
                                            content = @Content(schema = @Schema(
                                                    implementation = UserDTO.class
                                            ))
                                    ),
                                    @ApiResponse(
                                            responseCode = "401",
                                            description = "User unauthorized"
                                    ),
                                    @ApiResponse(
                                            responseCode = "403",
                                            description = "Forbidden: access denied due to access politic"
                                    ),
                                    @ApiResponse(
                                            responseCode = "400",
                                            description = "Internal error: can't create USER due to an error. Please, check all the fields"
                                    )
                            },
                            requestBody = @RequestBody(
                                    content = @Content(schema = @Schema(
                                            implementation = UserDTO.class
                                    ))
                            )
                    )
            ),
            @RouterOperation(
                    path = "/route/adm/users/update",
                    produces = {
                            APPLICATION_JSON_VALUE
                    },
                    method = RequestMethod.PUT,
                    beanClass = UserHandler.class,
                    beanMethod = "update",
                    operation = @Operation(
                            operationId = "updateExistingUserForAdmins",
                            description = "SPECIAL PERMISSION REQUIRED! Updates existing User matched to UserDTO",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "successful operation",
                                            content = @Content(schema = @Schema(
                                                    implementation = UserDTO.class
                                            ))
                                    ),
                                    @ApiResponse(
                                            responseCode = "401",
                                            description = "User unauthorized"
                                    ),
                                    @ApiResponse(
                                            responseCode = "403",
                                            description = "Forbidden: access denied due to access politic"
                                    ),
                                    @ApiResponse(
                                            responseCode = "400",
                                            description = "Internal error: can't create USER due to an error. Please, check all the fields"
                                    )
                            },
                            parameters = {
                                    @Parameter(in = ParameterIn.PATH, name = "id", description = "ID of User to update")
                            },
                            requestBody = @RequestBody(
                                    content = @Content(schema = @Schema(
                                            implementation = UserDTO.class,
                                            description = "RequestBody should match with UserDTO.class, but consider put time value of 'dateOfBirth' field in classic Java form like '2000-01-14T19:30:56.000Z'"
                                    ))
                            )
                    )
            )
    })
    @Bean
    public RouterFunction<ServerResponse> userRoute(UserHandler handler) {
        RouterFunction<ServerResponse> router = RouterFunctions
                .route()
                .path("/route/adm/users", builder -> builder
                        .GET("/", accept(APPLICATION_JSON), handler::findAll)
                        .GET("", accept(APPLICATION_JSON), handler::findAll)
                        .GET("/{id}", accept(APPLICATION_JSON), handler::findById)
                        .POST("/new", accept(APPLICATION_JSON), handler::create)
                        .GET("/search/email", accept(APPLICATION_JSON), handler::findByEmail)
                        .GET("/search/fname", accept(APPLICATION_JSON), handler::findByFirstName)
                        .GET("/search/lname", accept(APPLICATION_JSON), handler::findByLastName)
                        .GET("/search/country", accept(APPLICATION_JSON), handler::findByCountry)
                        .PUT("/update/{id}", accept(APPLICATION_JSON), handler::update)
                        .PUT("/test/raw/upd/{id}", accept(APPLICATION_JSON), handler::updateUserRaw)
                        .DELETE("/delete/{id}", accept(APPLICATION_JSON), handler::delete)
                )
                .build();
        return router;
    }

    @Bean
    public RouterFunction<ServerResponse> authRoute(AuthHandler handler) {
        RouterFunction<ServerResponse> router = RouterFunctions
                .route()
                .path("/auth", builder -> builder
                        .POST("/login", accept(APPLICATION_JSON), handler::login)
                        .POST("/validateToken", accept(APPLICATION_JSON), handler::validateToken)
                )
                .build();
        return router;
    }
}
