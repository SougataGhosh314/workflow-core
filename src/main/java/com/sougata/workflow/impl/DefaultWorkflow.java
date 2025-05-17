package com.sougata.workflow.impl;

import com.sougata.workflow.api.*;
import com.sougata.workflow.exception.StepException;

import java.util.LinkedHashMap;
import java.util.Map;

public class DefaultWorkflow<I, O> implements Workflow<I, O> {
    private final Map<String, Step<?, ?>> steps;
    private final ErrorHandler errorHandler;

    public DefaultWorkflow(Map<String, Step<?, ?>> steps, ErrorHandler errorHandler) {
        this.steps = steps;
        this.errorHandler = errorHandler;
    }

    @Override
    @SuppressWarnings("unchecked")
    public WorkflowResult<O> run(I input) {
        StepContext context = new DefaultStepContext();
        Object current = input;
        String currentStepName = null;

        ErrorHandler handlerToUse = errorHandler != null ? errorHandler : new DefaultErrorHandler();

        try {
            for (Map.Entry<String, Step<?, ?>> entry : steps.entrySet()) {
                currentStepName = entry.getKey();
                Step<Object, Object> step = (Step<Object, Object>) entry.getValue();
                current = step.execute(current, context);
            }
            return WorkflowResult.success((O) current);
        } catch (Exception e) {
            handlerToUse.handle(currentStepName, e, context);

            // Optional: allow fallback result on failure
            return WorkflowResult.failure(
                    new StepException("Workflow execution failed at step: " + currentStepName, e),
                    getDefaultFallback()
            );
        }
    }

    // Provide a fallback object – override this if needed
    protected O getDefaultFallback() {
        return null; // subclasses can return a default/fake object instead of null
    }

}