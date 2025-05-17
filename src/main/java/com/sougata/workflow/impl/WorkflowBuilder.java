package com.sougata.workflow.impl;

import com.sougata.workflow.api.ErrorHandler;
import com.sougata.workflow.api.Step;
import com.sougata.workflow.api.Workflow;

import java.util.LinkedHashMap;
import java.util.Map;

public class WorkflowBuilder<I, O> {
    private final Map<String, Step<?, ?>> steps = new LinkedHashMap<>();
    private ErrorHandler errorHandler;

    public static <I, O> WorkflowBuilder<I, O> newBuilder() {
        return new WorkflowBuilder<>();
    }

    public WorkflowBuilder<I, O> step(String name, Step<?, ?> step) {
        steps.put(name, step);
        return this;
    }

    public WorkflowBuilder<I, O> onError(ErrorHandler handler) {
        this.errorHandler = handler;
        return this;
    }

    public Workflow<I, O> build() {
        return new DefaultWorkflow<>(steps, errorHandler);
    }
}