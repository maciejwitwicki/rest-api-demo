package demo.rest.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String handleNotFound(NotFoundException e) {
        log.error("unhandled", e);
        return "Requested resource not found";
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    ErrorResponse handleAccessDenied(AccessDeniedException e) {
        log.error("unhandled", e);
        return new ErrorResponse(403, "Forbidden", LocalDateTime.now());
    }
    @ExceptionHandler(HttpClientErrorException.Unauthorized.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    ErrorResponse handleAccessDenied(HttpClientErrorException.Unauthorized e) {
        log.error("unhandled", e);
        return new ErrorResponse(401, "Unauthorized", LocalDateTime.now());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    String handleInternalServerError(Exception e) {
        log.error("unhandled", e);
        return "Internal server error";
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String handleMethodArgumentNotValidExceptionError(Exception e) {
        return "Incorrect request body " + e.getMessage();
    }

    record ErrorResponse(int status, String error, LocalDateTime timestamp){}
}
