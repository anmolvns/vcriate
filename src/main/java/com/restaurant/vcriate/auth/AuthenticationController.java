package com.restaurant.vcriate.auth;

import com.restaurant.vcriate.constants.Constants;
import com.restaurant.vcriate.exceptions.EtAuthException;
import com.restaurant.vcriate.exceptions.UserAlreadyExistsException;
import com.restaurant.vcriate.exceptions.UserNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(Constants.USER_AUTH_BASE_URL)
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Authentication", description = "Endpoints for authentication")
public class AuthenticationController {

    private final AuthenticationService service;

    @Operation(
            summary = "Register User",
            description = "Register a new user and generate JWT token.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                                            name = "Example 1",
                                            value = "{\n  \"firstName\": \"John\",\n  \"lastName\": \"Doe\",\n  \"email\": \"john.doe@example.com\",\n  \"password\": \"password123\"\n}"
                                    )
                            }
                    ),
                    description = "JSON payload with user registration details."
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful registration, returns JWT token.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Map.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request - Invalid registration data.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Map.class)
                            )
                    )
            }
    )
    @PostMapping(Constants.REGISTER)
    public ResponseEntity<AuthenticationResponse> register(
            @Parameter(description = "User registration request",
                    required = true,
                    schema = @Schema(implementation = AuthenticationRegister.class))
            @RequestBody AuthenticationRegister request
    ) {
        try {
            return ResponseEntity.ok(service.register(request));
        } catch (UserAlreadyExistsException e) {
            log.error("Error occurred during user registration: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AuthenticationResponse(e.getMessage()));
        } catch (EtAuthException e) {
            log.error("Error occurred during user registration: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new AuthenticationResponse(e.getMessage()));
        }
    }

    @Operation(
            summary = "Login User",
            description = "Authenticate and generate JWT token for the user.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                                            name = "Example 1",
                                            value = "{\n  \"email\": \"user@example.com\",\n  \"password\": \"password123\"\n}"
                                    )
                            }
                    ),
                    description = "JSON payload with user credentials."
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful login, returns JWT token.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Map.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized - Invalid credentials.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Map.class)
                            )
                    )
            }
    )
    @PostMapping(Constants.LOGIN)
    public ResponseEntity<AuthenticationResponse> authenticate(
            @Parameter(description = "User authentication request",
                    required = true,
                    schema = @Schema(implementation = AuthenticationRequest.class))
            @RequestBody AuthenticationRequest request
    ) {
        try {
            return ResponseEntity.ok(service.authenticate(request));
        } catch (UserNotFoundException e) {
            log.error("Error occurred during user authentication: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new AuthenticationResponse(e.getMessage()));
        } catch (EtAuthException e) {
            log.error("Error occurred during user authentication: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthenticationResponse(e.getMessage()));
        } catch (Exception e) {
            log.error("Error occurred during user authentication: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new AuthenticationResponse(e.getMessage()));
        }
    }

}
