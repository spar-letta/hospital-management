package com.javenock.laboratoryservice.exception;

public class NoLabDetailsWithPatientIdException extends Exception{
    public NoLabDetailsWithPatientIdException(String message) {
        super(message);
    }
}
