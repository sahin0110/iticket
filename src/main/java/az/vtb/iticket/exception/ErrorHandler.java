package az.vtb.iticket.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static az.vtb.iticket.exception.ErrorMessage.UNEXPECTED_ERROR;
import static az.vtb.iticket.util.LocalizationUtil.LOCALIZATION_UTIL;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

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

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(CONFLICT)
    public ErrorResponse handle(ConflictException ex) {
        log.error("ConflictException: ", ex);
        var message = LOCALIZATION_UTIL.getMessageByKey(ex.getMessage());
        return new ErrorResponse(message);
    }

    @ExceptionHandler(UnprocessableException.class)
    @ResponseStatus(UNPROCESSABLE_ENTITY)
    public ErrorResponse handle(UnprocessableException ex) {
        log.error("UnprocessableException: ", ex);
        var message = LOCALIZATION_UTIL.getMessageByKey(ex.getMessage());
        return new ErrorResponse(message);
    }

    @ExceptionHandler(TelegramException.class)
    @ResponseStatus(FORBIDDEN)
    public ErrorResponse handle(TelegramException ex) {
        log.error("TelegramException: ", ex);
        var message = LOCALIZATION_UTIL.getMessageByKey(ex.getMessage());
        return new ErrorResponse(message);
    }
}
