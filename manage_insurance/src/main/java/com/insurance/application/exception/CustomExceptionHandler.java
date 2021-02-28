package com.insurance.application.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CustomExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(CustomExceptionHandler.class);

    @ExceptionHandler(value = {NotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public String handlerNotFoundException(NotFoundException ex, Model model) {
        ErrorResponse err = new ErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
        logger.error(ex.getMessage(), err);
        model.addAttribute("err", err);
        return "error";
    }

    @ExceptionHandler(value = {AccessDeniedException.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public String deniedException(AccessDeniedException ex, Model model) {
        ErrorResponse err = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getMessage());
        logger.error(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        model.addAttribute("err", err);
        return "error";
    }

    // Xử lý tất cả các exception chưa được khai báo
    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public String handlerException(Exception ex, Model model) {
        ErrorResponse err = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getMessage());
        logger.error(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        model.addAttribute("err", err);
        return "error";
    }

}

