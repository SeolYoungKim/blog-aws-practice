package toyproject.blogawspractice.web.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import toyproject.blogawspractice.exception.ErrorResult;
import toyproject.blogawspractice.exception.NullPostException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class ExceptionController {

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(NullPostException.class)
    public ErrorResult nullPostExceptionHandler(NullPostException e) {
        return ErrorResult.builder()
                .code("404")
                .message(e.getMessage())
                .build();
    }
}
