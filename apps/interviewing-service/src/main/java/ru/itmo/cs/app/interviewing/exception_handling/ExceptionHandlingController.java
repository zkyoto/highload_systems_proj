package ru.itmo.cs.app.interviewing.exception_handling;

import java.util.Arrays;
import java.util.NoSuchElementException;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.itmo.cs.app.interviewing.exception_handling.dto.ErrorResponseBodyDto;

@ControllerAdvice
public class ExceptionHandlingController {

    @ResponseStatus(code = HttpStatus.NOT_FOUND, value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    @ResponseBody
    ErrorResponseBodyDto
    handleNoSuchElementException(HttpServletRequest req, Exception ex) {
        return new ErrorResponseBodyDto(HttpStatus.BAD_REQUEST,
                                        ex.getClass(),
                                        ex.getMessage(),
                                        stackTraceToString(ex.getStackTrace()));
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST, value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    @ResponseBody ErrorResponseBodyDto
    handleIllegalsException(HttpServletRequest req, Exception ex) {
        return new ErrorResponseBodyDto(HttpStatus.BAD_REQUEST,
                                        ex.getClass(),
                                        ex.getMessage(),
                                        stackTraceToString(ex.getStackTrace()));
    }

    private String stackTraceToString(StackTraceElement[] stackTraceElements) {
        StringBuilder stackTrace = new StringBuilder();
        Arrays.stream(stackTraceElements).forEach(s -> stackTrace.append(s).append("\n"));
        return stackTrace.toString();
    }

}
