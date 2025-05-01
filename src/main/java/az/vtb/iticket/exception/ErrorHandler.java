package az.vtb.iticket.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static az.vtb.iticket.exception.ErrorMessage.UNEXPECTED_ERROR;
import static az.vtb.iticket.util.LocalizationUtil.LOCALIZATION_UTIL;
import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(Exception.class)
    public ErrorResponse handle(Exception ex) {
        log.error("Exception: ", ex);
        var message = LOCALIZATION_UTIL.getMessageByKey(UNEXPECTED_ERROR.getCode());
        return new ErrorResponse(message);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ErrorResponse handle(NotFoundException ex) {
        log.error("NotFoundException: ", ex);
        var message = LOCALIZATION_UTIL.getMessageByKey(ex.getMessage());
        return new ErrorResponse(message);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    @ResponseStatus(CONFLICT)
    public ErrorResponse handle(AlreadyExistsException ex) {
        log.error("AlreadyExistsException: ", ex);
        var message = LOCALIZATION_UTIL.getMessageByKey(ex.getMessage());
        return new ErrorResponse(message);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(UNPROCESSABLE_ENTITY)
    public ErrorResponse handle(UnprocessableException ex) {
        log.error("UnprocessableException: ", ex);
        var message = LOCALIZATION_UTIL.getMessageByKey(ex.getMessage());
        return new ErrorResponse(message);
    }

}
