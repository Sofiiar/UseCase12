/**
 * Represents a REST API endpoint for managing user resources.
 * This class extends the AbstractWebEndpoint class.
 *
 * <p>
 * The UserEndpoint class provides methods for creating, updating, and retrieving user information
 * via the REST API. It inherits common functionality from the AbstractWebEndpoint class.
 * </p>
 *
 * @see AbstractWebEndpoint
 */
package com.softserve.taf.services.placeholder.endpoints;

import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.softserve.taf.models.enums.HttpStatus;
import com.softserve.taf.models.placeholder.user.UserDto;
import com.softserve.taf.services.common.AbstractWebEndpoint;

public class UserEndpoint extends AbstractWebEndpoint {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String USERS_END = "/users";
    private static final String USERS_RESOURCE_END = "/users/{userID}";

    /**
     * Constructs a new UserEndpoint with the given RequestSpecification.
     *
     * @param specification the RequestSpecification used for API requests
     * @see RequestSpecification
     */
    public UserEndpoint(RequestSpecification specification) {
        super(specification);
    }

    /**
     * Creates a new user using the provided UserDto object.
     *
     * @param userDto the UserDto object representing the user to be created
     * @return the created UserDto object
     * @see UserDto
     */
    public UserDto create(UserDto userDto) {
        return create(userDto, HttpStatus.CREATED)
                .extract().as(UserDto.class);
    }

    /**
     * Creates a new user using the provided UserDto object and validates the response
     * against the specified HttpStatus.
     *
     * @param userDto the UserDto object representing the user to be created
     * @param status  the expected HttpStatus of the response
     * @return the ValidatableResponse object representing the API response
     * @see UserDto
     * @see HttpStatus
     * @see ValidatableResponse
     * @example
     * <pre>{@code
     * UserDto user = new UserDto("John Doe", "john.doe@example.com");
     * UserEndpoint userEndpoint = new UserEndpoint(requestSpecification);
     * UserDto createdUser = userEndpoint.create(user);
     * System.out.println("Created User ID: " + createdUser.getId());
     * }</pre>
     */
    public ValidatableResponse create(UserDto userDto, HttpStatus status) {
        LOGGER.info("Create new User");
        return post(
                this.specification,
                USERS_END,
                userDto)
                .statusCode(status.getCode());
    }

    /**
     * Updates an existing user with the provided UserDto object and user ID.
     *
     * @param id      the ID of the user to be updated
     * @param userDto the UserDto object representing the updated user
     * @return the updated UserDto object
     * @see UserDto
     */
    public UserDto update(int id, UserDto userDto) {
        return update(userDto, id, HttpStatus.OK)
                .extract().as(UserDto.class);
    }

    /**
     * Updates an existing user with the provided UserDto object, user ID, and validates the response
     * against the specified HttpStatus.
     *
     * @param userDto the UserDto object representing the updated user
     * @param id      the ID of the user to be updated
     * @param status  the expected HttpStatus of the response
     * @return the ValidatableResponse object representing the API response
     * @see UserDto
     * @see HttpStatus
     * @see ValidatableResponse
     * @example
     * <pre>{@code
     * UserDto user = new UserDto("John Doe", "john.doe@example.com");
     * UserEndpoint userEndpoint = new UserEndpoint(requestSpecification);
     * UserDto updatedUser = userEndpoint.update(1, user);
     * System.out.println("Updated User: " + updatedUser.getName());
     * }</pre>
     */
    public ValidatableResponse update(UserDto userDto, int id, HttpStatus status) {
        LOGGER.info("Update User by id [{}]", id);
        return put(
                this.specification,
                USERS_RESOURCE_END,
                userDto,
                id)
                .statusCode(status.getCode());
    }

    /**
     * Retrieves the user with the specified ID.
     *
     * @param id the ID of the user to retrieve
     * @return the UserDto object representing the retrieved user
     * @see UserDto
     */
    public UserDto getById(String id) {
        return getById(id, HttpStatus.OK)
                .extract().as(UserDto.class);
    }

    /**
     * Retrieves the user with the specified ID and validates the response
     * against the specified HttpStatus.
     *
     * @param id     the ID of the user to retrieve
     * @param status the expected HttpStatus of the response
     * @return the ValidatableResponse object representing the API response
     * @see HttpStatus
     * @see ValidatableResponse
     * @example
     * <pre>{@code
     * UserEndpoint userEndpoint = new UserEndpoint(requestSpecification);
     * UserDto user = userEndpoint.getById("1");
     * System.out.println("User Name: " + user.getName());
     * }</pre>
     */
    public ValidatableResponse getById(String id, HttpStatus status) {
        LOGGER.info("Get User by id [{}]", id);
        return get(
                this.specification,
                USERS_RESOURCE_END,
                id)
                .statusCode(status.getCode());
    }

    /**
     * Retrieves all users.
     *
     * @return a List of UserDto objects representing all users
     * @see UserDto
     */
    public List<UserDto> getAll() {
        return List.of(getAll(HttpStatus.OK).extract().as(UserDto[].class));
    }

    /**
     * Retrieves all users and validates the response against the specified HttpStatus.
     *
     * @param status the expected HttpStatus of the response
     * @return the ValidatableResponse object representing the API response
     * @see HttpStatus
     * @see ValidatableResponse
     * @example
     * <pre>{@code
     * UserEndpoint userEndpoint = new UserEndpoint(requestSpecification);
     * List<UserDto> users = userEndpoint.getAll();
     * for (UserDto user : users) {
     *     System.out.println("User ID: " + user.getId() + ", Name: " + user.getName());
     * }
     * }</pre>
     */
    public ValidatableResponse getAll(HttpStatus status) {
        LOGGER.info("Get all Users");
        ValidatableResponse response = get(this.specification, USERS_END);
        response.statusCode(status.getCode());
        return response;
    }
}
