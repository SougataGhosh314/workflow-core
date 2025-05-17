package com.sougata.workflow.api;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultErrorHandler implements ErrorHandler {

    @Override
    public void handle(String stepName, Throwable t, StepContext context) {
        log.error("Error occurred during step '{}': {}", stepName, t.getMessage(), t);
    }
}
