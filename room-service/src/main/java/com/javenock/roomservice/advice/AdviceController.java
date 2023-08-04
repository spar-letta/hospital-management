package com.javenock.roomservice.advice;

import com.javenock.roomservice.exception.NoRoomsRegisteredException;
import com.javenock.roomservice.exception.RoomNotFoundException;
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
    @ExceptionHandler(RoomNotFoundException.class)
    public Map<String, String> handleNoRecoredsFoundException(RoomNotFoundException exception){
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("System message... ",exception.getMessage());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NoRoomsRegisteredException.class)
    public Map<String, String> handleNoRoomsRegisteredException(NoRoomsRegisteredException exception){
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("System message... ",exception.getMessage());
        return errorMap;
    }
}
