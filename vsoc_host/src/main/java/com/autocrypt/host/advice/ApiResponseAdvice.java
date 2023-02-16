package com.autocrypt.host.advice;

import com.autocrypt.host.exception.HostIdpsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.net.UnknownHostException;

@Slf4j
@ControllerAdvice
public class ApiResponseAdvice {

    @ExceptionHandler(value = {})
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleNotFoundException (final Exception e) {
        log.error(e.getMessage(), e);
    }


    @ExceptionHandler(value = {
            UnknownHostException.class,
            RuntimeException.class
    })
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleServerError (final Exception e) {
        log.error(e.getMessage(), e);
    }


    @ExceptionHandler(value = {
            HostIdpsException.class
    })
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleBadRequest (final Exception e) {
        log.error(e.getMessage(), e);
    }
}


