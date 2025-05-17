package com.sougata.workflow.api;

public interface StepContext {
    <T> void set(String key, T value);
    <T> T get(String key, Class<T> type);
}