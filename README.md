# workflow-core

`workflow-core` is a lightweight, pluggable Java library for defining and executing multi-step business workflows in microservice or monolith environments. It allows you to compose modular processing pipelines with clear input/output flow, centralized error handling, and full context tracking.

---

## ✨ Features

* ✅ **Multi-step workflow orchestration**
* ⚙️ **Pluggable step logic via functional interfaces**
* 💿 **Named steps with ordered execution**
* 📦 **Centralized error handling**
* 🧠 **Per-step execution context**
* 🔀 **Dynamic workflow selection support**
* 🔒 **No exceptions exposed to controller layer by default**
* 🧪 **Test-friendly architecture**
* 🚀 **Easy to extend and override (fallbacks, handlers, etc.)**

---

## 📦 Installation

Currently local use only. To use in your own project:

```xml
<!-- In your local pom.xml -->
<dependency>
  <groupId>com.example</groupId>
  <artifactId>workflow-core</artifactId>
  <version>1.0.0</version>
</dependency>
```

---

## 🧹 Basic Concepts

### `Workflow<I, O>`

Interface for workflows that transform input of type `I` into output of type `O`.

### `Step<I, O>`

Represents a single processing unit. Each step receives input, performs logic, and returns output.

### `StepContext`

Holds metadata and transient state shared between steps in a workflow.

### `ErrorHandler`

Handles exceptions thrown during step execution. Can log, recover, or perform custom logic.

### `WorkflowResult<O>`

Encapsulates workflow execution result:

* `isSuccess()`
* `getResult()` – Optional
* `getError()` – Optional
* `getOrDefault()` – Safe fallback access

---

## 🚀 Usage

### 1. Define your steps

```java
Step<UserInput, UserProfile> enrichStep = (input, ctx) -> {
    // business logic here
    return new UserProfile(input.getId(), "Enriched Name");
};

Step<UserProfile, UserProfile> validateStep = (profile, ctx) -> {
    if (profile.getName() == null) throw new IllegalArgumentException("Name required");
    return profile;
};
```

### 2. Create a workflow

```java
Map<String, Step<?, ?>> steps = new LinkedHashMap<>();
steps.put("enrich", enrichStep);
steps.put("validate", validateStep);

Workflow<UserInput, UserProfile> workflow = new DefaultWorkflow<>(steps, null); // or custom ErrorHandler
```

### 3. Execute it

```java
WorkflowResult<UserProfile> result = workflow.run(new UserInput(123));

UserProfile output = result.getOrDefault(UserProfile.empty());
```

Or with Spring REST:

```java
@PostMapping
public ResponseEntity<UserProfile> handle(@RequestBody UserInput input) {
    Workflow<UserInput, UserProfile> workflow = resolver.resolve(input);
    WorkflowResult<UserProfile> result = workflow.run(input);
    return ResponseEntity.ok(result.getOrDefault(UserProfile.empty()));
}
```

---

## 🎯 Customization

### Custom Error Handler

```java
ErrorHandler loggingHandler = (stepName, t, ctx) ->
    log.warn("Failure in step {}: {}", stepName, t.getMessage());

new DefaultWorkflow<>(steps, loggingHandler);
```

### Override Fallback Behavior

You can subclass `DefaultWorkflow` to override fallback object generation:

```java
public class SafeUserWorkflow extends DefaultWorkflow<UserInput, UserProfile> {
    public SafeUserWorkflow(Map<String, Step<?, ?>> steps, ErrorHandler handler) {
        super(steps, handler);
    }

    @Override
    protected UserProfile getDefaultFallback() {
        return UserProfile.empty();
    }
}
```

---

## 🧪 Testing

You can test workflows by providing mock steps and verifying the output of `Workflow.run()`:
Also, feel free to refer to for sample usage of this library


```java
@Test
void testWorkflowSuccess() {
    Workflow<UserInput, UserProfile> workflow = new DefaultWorkflow<>(steps, null);
    WorkflowResult<UserProfile> result = workflow.run(new UserInput(42));
    assertTrue(result.isSuccess());
}
```

---

## 🔮 Planned Features

* Step dependencies / conditional branching
* Parallel step execution support
* WorkflowBuilder DSL
* Retry / circuit-breaker integration
* Metrics & observability hooks

---

## 👥 Authors & Contributions

Built by Sougata Ghosh and contributors. PRs welcome!
