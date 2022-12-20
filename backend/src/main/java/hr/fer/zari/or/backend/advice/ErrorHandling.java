package hr.fer.zari.or.backend.advice;

import hr.fer.zari.or.backend.model.dto.ResponseWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;
import java.util.NoSuchElementException;

@ControllerAdvice
public class ErrorHandling {

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseWrapper<Object> notFound(NoSuchElementException e) {
        return new ResponseWrapper<>(HttpStatus.NOT_FOUND.getReasonPhrase(),
                e.getMessage() != null && !e.getMessage().isEmpty() ? e.getMessage() : "Resource not found",
                null);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseWrapper<Object> badRequest(IllegalArgumentException e) {
        return new ResponseWrapper<>(HttpStatus.BAD_REQUEST.getReasonPhrase(),
                e.getMessage() != null && !e.getMessage().isEmpty() ? e.getMessage() : "Cannot process request with provided parameters",
                null);
    }

    @ExceptionHandler(IOException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseWrapper<Object> serverError(IOException e) {
        return new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                "An error has occured on the server.", null);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseWrapper<Object> genericErrorHandler(Exception e) {
        return new ResponseWrapper<>(HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Error processing request",
                null);
    }
}
