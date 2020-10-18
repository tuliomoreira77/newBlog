package com.mmoreira.blog.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.mmoreira.blog.exception.ErrorMessage;
import com.mmoreira.blog.exception.InvalidFieldsException;
import com.mmoreira.blog.exception.NotOwnerException;
import com.mmoreira.blog.exception.ResourceNotFoundExeception;

@ControllerAdvice
public class GlobalControllerExceptionHandler {
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {ResourceNotFoundExeception.class, InvalidFieldsException.class})
    @ResponseBody
    public ErrorMessage handleNotFound(Exception ex) {
        return new ErrorMessage(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }
    
    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    @ExceptionHandler(NotOwnerException.class)
    @ResponseBody
    public ErrorMessage handleNotOwner(Exception ex) {
        return new ErrorMessage(HttpStatus.FORBIDDEN.value(), ex.getMessage());
    } 
}