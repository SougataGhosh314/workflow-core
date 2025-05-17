package com.sougata.workflow.api;

public interface Step<I, O> {
    O execute(I input, StepContext context) throws Exception;
}
