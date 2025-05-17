package com.sougata.workflow.impl;

import com.sougata.workflow.api.StepContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultStepContext implements StepContext {
    private final Map<String, Object> contextMap = new ConcurrentHashMap<>();

    @Override
    public <T> void set(String key, T value) {
        contextMap.put(key, value);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(String key, Class<T> type) {
        return (T) contextMap.get(key);
    }
}