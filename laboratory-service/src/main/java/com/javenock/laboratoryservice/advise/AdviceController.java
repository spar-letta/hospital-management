package com.javenock.laboratoryservice.advise;

import com.javenock.laboratoryservice.exception.NoLabDetailsWithPatientIdException;
import com.javenock.laboratoryservice.exception.NoSuchRecordFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class AdviceController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NoLabDetailsWithPatientIdException.class)
    public Map<String, String> handleNoLabDetailsWithPatientIdException(NoLabDetailsWithPatientIdException exception){
        Map<String, String> mapError = new HashMap<>();
        mapError.put("System message :", exception.getMessage());
        return mapError;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NoSuchRecordFoundException.class)
    public Map<String, String> handleNoSuchRecordFoundException(NoSuchRecordFoundException exception){
        Map<String, String> mapError = new HashMap<>();
        mapError.put("System message :", exception.getMessage());
        return mapError;
    }
}
