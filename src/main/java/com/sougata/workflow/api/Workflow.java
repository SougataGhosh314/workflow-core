package com.sougata.workflow.api;

public interface Workflow<I, O> {
    WorkflowResult<O> run(I input);
}