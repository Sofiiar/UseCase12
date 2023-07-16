/**
 * Represents a REST API endpoint for managing comment resources.
 * This class extends the AbstractWebEndpoint class.
 *
 * <p>
 * The CommentEndpoint class provides methods for creating, updating, and retrieving comment information
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
import com.softserve.taf.models.placeholder.comment.CommentDto;
import com.softserve.taf.services.common.AbstractWebEndpoint;

public class CommentEndpoint extends AbstractWebEndpoint {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String COMMENTS_END = "/comments";
    private static final String COMMENTS_RESOURCE_END = "/comments/{commentID}";

    /**
     * Constructs a new CommentEndpoint with the given RequestSpecification.
     *
     * @param specification the RequestSpecification used for API requests
     * @see RequestSpecification
     */
    public CommentEndpoint(RequestSpecification specification) {
        super(specification);
    }

    /**
     * Creates a new comment using the provided CommentDto object.
     *
     * @param commentDto the CommentDto object representing the comment to be created
     * @return the created CommentDto object
     * @see CommentDto
     */
    public CommentDto create(CommentDto commentDto) {
        return create(commentDto, HttpStatus.CREATED)
                .extract().as(CommentDto.class);
    }

    /**
     * Creates a new comment using the provided CommentDto object and validates the response
     * against the specified HttpStatus.
     *
     * @param commentDto the CommentDto object representing the comment to be created
     * @param status     the expected HttpStatus of the response
     * @return the ValidatableResponse object representing the API response
     * @see CommentDto
     * @see HttpStatus
     * @see ValidatableResponse
     * @example
     * <pre>{@code
     * CommentDto comment = new CommentDto("Hello, nice post!");
     * CommentEndpoint commentEndpoint = new CommentEndpoint(requestSpecification);
     * CommentDto createdComment = commentEndpoint.create(comment);
     * System.out.println("Created Comment ID: " + createdComment.getId());
     * }</pre>
     */
    public ValidatableResponse create(CommentDto commentDto, HttpStatus status) {
        LOGGER.info("Create new Comment");
        return post(
                this.specification,
                COMMENTS_END,
                commentDto)
                .statusCode(status.getCode());
    }

    /**
     * Updates an existing comment with the provided CommentDto object and comment ID.
     *
     * @param id         the ID of the comment to be updated
     * @param commentDto the CommentDto object representing the updated comment
     * @return the updated CommentDto object
     * @see CommentDto
     */
    public CommentDto update(int id, CommentDto commentDto) {
        return update(commentDto, id, HttpStatus.OK)
                .extract().as(CommentDto.class);
    }

    /**
     * Updates an existing comment with the provided CommentDto object, comment ID, and validates the response
     * against the specified HttpStatus.
     *
     * @param commentDto the CommentDto object representing the updated comment
     * @param id         the ID of the comment to be updated
     * @param status     the expected HttpStatus of the response
     * @return the ValidatableResponse object representing the API response
     * @see CommentDto
     * @see HttpStatus
     * @see ValidatableResponse
     * @example
     * <pre>{@code
     * CommentDto comment = new CommentDto("Updated comment");
     * CommentEndpoint commentEndpoint = new CommentEndpoint(requestSpecification);
     * CommentDto updatedComment = commentEndpoint.update(1, comment);
     * System.out.println("Updated Comment: " + updatedComment.getContent());
     * }</pre>
     */
    public ValidatableResponse update(CommentDto commentDto, int id, HttpStatus status) {
        LOGGER.info("Update Comment by id [{}]", id);
        return put(
                this.specification,
                COMMENTS_RESOURCE_END,
                commentDto,
                id)
                .statusCode(status.getCode());
    }

    /**
     * Retrieves the comment with the specified ID.
     *
     * @param id the ID of the comment to retrieve
     * @return the CommentDto object representing the retrieved comment
     * @see CommentDto
     */
    public CommentDto getById(int id) {
        return getById(id, HttpStatus.OK)
                .extract().as(CommentDto.class);
    }

    /**
     * Retrieves the comment with the specified ID and validates the response
     * against the specified HttpStatus.
     *
     * @param id     the ID of the comment to retrieve
     * @param status the expected HttpStatus of the response
     * @return the ValidatableResponse object representing the API response
     * @see HttpStatus
     * @see ValidatableResponse
     * @example
     * <pre>{@code
     * CommentEndpoint commentEndpoint = new CommentEndpoint(requestSpecification);
     * CommentDto comment = commentEndpoint.getById(1);
     * System.out.println("Comment Content: " + comment.getContent());
     * }</pre>
     */
    public ValidatableResponse getById(int id, HttpStatus status) {
        LOGGER.info("Get Comment by id [{}]", id);
        return get(
                this.specification,
                COMMENTS_RESOURCE_END,
                String.valueOf(id))
                .statusCode(status.getCode());
    }

    /**
     * Retrieves all comments.
     *
     * @return a List of CommentDto objects representing all comments
     * @see CommentDto
     */
    public List<CommentDto> getAll() {
        return List.of(getAll(HttpStatus.OK).extract().as(CommentDto[].class));
    }

    /**
     * Retrieves all comments and validates the response against the specified HttpStatus.
     *
     * @param status the expected HttpStatus of the response
     * @return the ValidatableResponse object representing the API response
     * @see HttpStatus
     * @see ValidatableResponse
     * @example
     * <pre>{@code
     * CommentEndpoint commentEndpoint = new CommentEndpoint(requestSpecification);
     * List<CommentDto> comments = commentEndpoint.getAll();
     * for (CommentDto comment : comments) {
     *     System.out.println("Comment ID: " + comment.getId() + ", Content: " + comment.getContent());
     * }
     * }</pre>
     */
    public ValidatableResponse getAll(HttpStatus status) {
        LOGGER.info("Get all Comments");
        ValidatableResponse response = get(this.specification, COMMENTS_END);
        response.statusCode(status.getCode());
        return response;
    }
}
