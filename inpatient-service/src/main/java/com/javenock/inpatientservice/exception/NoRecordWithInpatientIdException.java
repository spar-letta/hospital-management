package com.javenock.inpatientservice.exception;

public class NoRecordWithInpatientIdException extends Exception{
    public NoRecordWithInpatientIdException(String message) {
        super(message);
    }
}
