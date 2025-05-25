package com.exercise.userapi.api;


import com.exercise.userapi.model.UserDto;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

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
            summary = "Adds users",
            description = "Adds users",
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
                                                    name = "Bad Response",
                                                    externalValue = "/openapi/examples/user/responses/invalid-request.json"
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

    @PostMapping(USER_URL)
    @ResponseStatus(HttpStatus.CREATED)
    UserDto createUser(
            @Parameter(name = "UserDto")
            @RequestBody
            @Valid UserDto userDto
    );

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
                                                    name = "Bad Response",
                                                    externalValue = "/openapi/examples/internal-server-error.json"
                                            )
                                    })
                            })


            }
    )

    @GetMapping(USER_URL)
    @ResponseStatus(HttpStatus.OK)
    List<UserDto> getUsers();


}
