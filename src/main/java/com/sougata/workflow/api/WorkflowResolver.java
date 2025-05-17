package com.sougata.workflow.api;

public interface WorkflowResolver<I, O> {
    Workflow<I, O> resolve(I input);
}
