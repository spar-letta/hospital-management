package com.javenock.inpatientservice.advice;

import com.javenock.inpatientservice.exception.NoRecordWithInpatientIdException;
import com.javenock.inpatientservice.exception.NoRecoredsFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class AdviceController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception){
        Map<String, String> errorMap = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach((er) -> {
            errorMap.put(er.getField(), er.getDefaultMessage());
        });
        return errorMap;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NoRecoredsFoundException.class)
    public Map<String, String> handleNoRecoredsFoundException(NoRecoredsFoundException exception){
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("System message... ",exception.getMessage());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NoRecordWithInpatientIdException.class)
    public Map<String, String> handleNoRecoredsFoundException(NoRecordWithInpatientIdException exception){
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("System message... ",exception.getMessage());
        return errorMap;
    }
}
