package com.exercise.userapi.api;


import com.exercise.userapi.model.UserDto;
import com.exercise.userapi.validator.group.OnCreate;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import jakarta.validation.groups.Default;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.UUID;

@OpenAPIDefinition(
        info = @Info(
                title = "User API",
                version = "1.0.0",
                description = "Service exposing a REST endpoint to create new users and persist them in a database."
        )
)
public interface UserApi {

    String USER_URL = "/user-api/v1/users";

    @Operation(
            operationId = "createUser",
            summary = "Add user",
            description = "Adds user",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {
                                    @ExampleObject(
                                            name = "User Request",
                                            externalValue = "/openapi/examples/user/request/user-request.json"
                                    )
                            }
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "user created successfully", content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = {
                                    @ExampleObject(
                                            name = "Success Response",
                                            externalValue = "/openapi/examples/user/responses/user-created.json"
                                    )
                            })
                    }),
                    @ApiResponse(responseCode = "400", description = "When a wrong request is sent",
                            content = {
                                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = {
                                            @ExampleObject(
                                                    name = "Bad request",
                                                    externalValue = "/openapi/examples/user/responses/invalid-request.json"
                                            )
                                    })
                            }),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error.", content =
                            {
                                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = {
                                            @ExampleObject(
                                                    name = "Internal Server Error",
                                                    externalValue = "/openapi/examples/internal-server-error.json"
                                            )
                                    })
                            })


            }
    )

    @PostMapping(USER_URL)
    @ResponseStatus(HttpStatus.CREATED)
    UserDto createUser(
            @Parameter(name = "UserDto")
            @RequestBody
            @Validated({Default.class, OnCreate.class}) UserDto userDto
    );

    @Operation(
            operationId = "deleteUser",
            summary = "Delete user",
            description = "Delete user",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Delete user successfully"),
                    @ApiResponse(responseCode = "404", description = "User not found",  content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = {
                                    @ExampleObject(
                                            name = "User not found",
                                            externalValue = "/openapi/examples/user/responses/user-not-found.json"
                                    )
                            })
                    }),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error.", content =
                            {
                                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = {
                                            @ExampleObject(
                                                    name = "Bad Response",
                                                    externalValue = "/openapi/examples/internal-server-error.json"
                                            )
                                    })
                            })


            }
    )

    @DeleteMapping(USER_URL+"/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteUser(
            @Parameter(name = "user ID")
            @PathVariable
            UUID id
    );

    @Operation(
            operationId = "getUser",
            summary = "Get user",
            description = "Get user",
            responses = {
                    @ApiResponse(responseCode = "200", description = "get users successfully", content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = {
                                    @ExampleObject(
                                            name = "Success Response",
                                            externalValue = "/openapi/examples/user/responses/user-created.json"
                                    )
                            })
                    }),
                    @ApiResponse(responseCode = "404", description = "user not found",  content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = {
                                    @ExampleObject(
                                            name = "User not found",
                                            externalValue = "/openapi/examples/user/responses/user-not-found.json"
                                    )
                            })
                    }),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error.", content =
                            {
                                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = {
                                            @ExampleObject(
                                                    name = "Internal Server Error",
                                                    externalValue = "/openapi/examples/internal-server-error.json"
                                            )
                                    })
                            })


            }
    )

    @GetMapping(USER_URL+"/{id}")
    @ResponseStatus(HttpStatus.OK)
    UserDto getUser(
            @Parameter(name = "user ID")
            @PathVariable
            UUID id);


    @Operation(
            operationId = "getUsers",
            summary = "Get users",
            description = "Get users",
            responses = {
                    @ApiResponse(responseCode = "200", description = "get users successfully", content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = {
                                    @ExampleObject(
                                            name = "Success Response",
                                            externalValue = "/openapi/examples/user/responses/user-list.json"
                                    )
                            })
                    }),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error.", content =
                            {
                                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = {
                                            @ExampleObject(
                                                    name = "Internal Server Error",
                                                    externalValue = "/openapi/examples/internal-server-error.json"
                                            )
                                    })
                            })


            }
    )

    @GetMapping(USER_URL)
    @ResponseStatus(HttpStatus.OK)
    List<UserDto> getUsers();



    @Operation(
            operationId = "updateUser",
            summary = "Update User",
            description = "Update user",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {
                                    @ExampleObject(
                                            name = "User Request",
                                            externalValue = "/openapi/examples/user/request/user-request.json"
                                    )
                            }
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "user updated successfully", content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = {
                                    @ExampleObject(
                                            name = "Success Response",
                                            externalValue = "/openapi/examples/user/responses/user-created.json"
                                    )
                            })
                    }),
                    @ApiResponse(responseCode = "400", description = "When a wrong request is sent",
                            content = {
                                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = {
                                            @ExampleObject(
                                                    name = "Bad request",
                                                    externalValue = "/openapi/examples/user/responses/invalid-request.json"
                                            )
                                    })
                            }),
                    @ApiResponse(responseCode = "404", description = "User not found",  content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = {
                                    @ExampleObject(
                                            name = "User not found",
                                            externalValue = "/openapi/examples/user/responses/user-not-found.json"
                                    )
                            })
                    }),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error.", content =
                            {
                                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = {
                                            @ExampleObject(
                                                    name = "Internal Server Error",
                                                    externalValue = "/openapi/examples/internal-server-error.json"
                                            )
                                    })
                            })


            }
    )

    @PutMapping(USER_URL+"/{id}")
    @ResponseStatus(HttpStatus.OK)
    UserDto updateUser(
            @PathVariable
            UUID id,
            @Parameter(name = "UserDto")
            @RequestBody
            @Validated UserDto userDto
    );


}
