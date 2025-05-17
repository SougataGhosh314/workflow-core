package com.sougata.workflow.api;

public interface ErrorHandler {
    void handle(String stepName, Throwable t, StepContext context);
}