# Chapter 4: Streaming Responses

When you ask an AI a long question, it can take several seconds to generate the full answer. In a web browser, users hate waiting for a blank screen. **Streaming** allows you to show the response piece-by-piece as it's being generated (like ChatGPT does).

In Spring AI, streaming is handled using **Reactive Programming** with Project Reactor's `Flux`.

## Why use Streaming?
1. **Perceived Performance**: The user sees the first word almost instantly.
2. **Efficiency**: You don't have to wait for the entire multi-paragraph response to be finished before sending it to the client.

## How it works in Spring AI

Instead of calling `.call().content()`, you call `.stream().content()`. This returns a `Flux<String>`.

### 1. Update ChatService.java
```java
public Flux<String> streamChat(String query) {
    return chatClient.prompt(query)
            .stream()
            .content();
}
```

### 2. Update ChatController.java (For Web)
In a REST controller, Spring Boot can automatically handle `Flux<String>` as a "Server-Sent Events" (SSE) stream.

```java
@GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
public Flux<String> stream(@RequestParam String query) {
    return chatService.streamChat(query);
}
```

### 3. Testing in CLI
Since we are using the CLI, we can't just return a `Flux`. We need to "subscribe" to it and print each chunk as it arrives.

```java
chatService.streamChat("Write a long poem about Spring Boot")
    .subscribe(System.out::print);
```

---

## Task: Let's implement a Streaming Poem Generator

### Step 1: Update application.yml
I'll switch us to `llama-3.3-70b-versatile` as it is extremely fast and perfect for streaming demos.

### Step 2: Update ChatService.java
Add the `streamChat` method.

### Step 3: Update CLI
Add a section to test the streaming output.

---
*Ready to see the AI "type" in real-time? I'll update your code now!*
