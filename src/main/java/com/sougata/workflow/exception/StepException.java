package com.sougata.workflow.exception;

public class StepException extends RuntimeException {
    public StepException(String message, Throwable cause) {
        super(message, cause);
    }
}