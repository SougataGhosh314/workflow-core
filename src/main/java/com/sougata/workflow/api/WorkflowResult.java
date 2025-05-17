package com.sougata.workflow.api;

import com.sougata.workflow.exception.WorkflowExecutionException;

import java.util.Optional;

public class WorkflowResult<O> {
    private final boolean success;
    private final O result;
    private final Throwable error;

    private WorkflowResult(boolean success, O result, Throwable error) {
        this.success = success;
        this.result = result;
        this.error = error;
    }

    public static <O> WorkflowResult<O> success(O result) {
        return new WorkflowResult<>(true, result, null);
    }

    public static <O> WorkflowResult<O> failure(Throwable error) {
        return new WorkflowResult<>(false, null, error);
    }

    public boolean isSuccess() { return success; }
    public Optional<O> getResult() { return Optional.ofNullable(result); }
    public Optional<Throwable> getError() { return Optional.ofNullable(error); }

    public static <O> WorkflowResult<O> failure(Throwable error, O fallback) {
        return new WorkflowResult<>(false, fallback, error);
    }


}