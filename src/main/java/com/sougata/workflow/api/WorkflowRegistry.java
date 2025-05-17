package com.sougata.workflow.api;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class WorkflowRegistry {

    private final Map<String, Workflow<?, ?>> workflows = new ConcurrentHashMap<>();

    public <I, O> void register(String name, Workflow<I, O> workflow) {
        workflows.put(name, workflow);
    }

    @SuppressWarnings("unchecked")
    public <I, O> Optional<Workflow<I, O>> getWorkflow(String name) {
        return Optional.ofNullable((Workflow<I, O>) workflows.get(name));
    }

    public Set<String> getRegisteredNames() {
        return workflows.keySet();
    }
}
