package com.sougata.workflow.logging;

import com.sougata.workflow.api.Step;
import com.sougata.workflow.api.StepContext;
import org.slf4j.MDC;

import java.util.Map;

public class MdcAwareStep<I, O> implements Step<I, O> {
    private final Step<I, O> delegate;
    private final Map<String, String> mdcContext;

    public MdcAwareStep(Step<I, O> delegate, Map<String, String> mdcContext) {
        this.delegate = delegate;
        this.mdcContext = mdcContext;
    }

    @Override
    public O execute(I input, StepContext context) throws Exception {
        Map<String, String> previous = MDC.getCopyOfContextMap();
        try {
            MDC.setContextMap(mdcContext);
            return delegate.execute(input, context);
        } finally {
            if (previous != null) MDC.setContextMap(previous);
            else MDC.clear();
        }
    }
}