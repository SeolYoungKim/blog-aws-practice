package toyproject.blogawspractice.web.controller.api;

import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import toyproject.blogawspractice.exception.ErrorResult;
import toyproject.blogawspractice.exception.NullPostException;

import java.util.List;
import java.util.stream.Collectors;

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

    // validation 예외 처리 - MethodArgumentNotValidException 발생
    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ErrorResult> fieldErrorHandler(MethodArgumentNotValidException e) {

        List<FieldError> fieldErrors = e.getFieldErrors();

        return fieldErrors.stream()
                .map(fieldError -> ErrorResult.builder()
                        .code("404")
                        .message("잘못된 요청입니다.")
                        .field(fieldError.getField())
                        .filedMessage(fieldError.getDefaultMessage())
                        .build())
                .collect(Collectors.toList());

    }
}