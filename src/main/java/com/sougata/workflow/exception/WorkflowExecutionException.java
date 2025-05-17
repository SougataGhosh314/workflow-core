package com.sougata.workflow.exception;

public class WorkflowExecutionException extends RuntimeException {
    public WorkflowExecutionException(String message, Throwable cause) {
        super(message, cause);
    }
}
