package com.javenock.patientservice.advice;

import com.javenock.patientservice.exception.NoPatientsException;
import com.javenock.patientservice.exception.PatientNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ControllerAdviceHandler {

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
    @ExceptionHandler(PatientNotFoundException.class)
    public Map<String, String> handlePatientNotFoundException(PatientNotFoundException exception){
        Map<String, String> mapError = new HashMap<>();
        mapError.put("System message", exception.getMessage());
        return mapError;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NoPatientsException.class)
    public Map<String, String> handlePatientNotFoundException(NoPatientsException exception){
        Map<String, String> mapError = new HashMap<>();
        mapError.put("System message", exception.getMessage());
        return mapError;
    }
}
