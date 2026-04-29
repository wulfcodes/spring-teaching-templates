# Chapter 1: Introduction to Spring AI

Welcome to your journey into Spring AI! 

This chapter covers the fundamental concepts of Spring AI and walks through the basic template you've already set up.

## 1. What is Spring AI?

The main goal of Spring AI is **portability** and bringing the familiar "Spring way" (like dependency injection and POJOs) to Artificial Intelligence.

Without Spring AI, if you wanted to build an app using OpenAI, you would use OpenAI's specific APIs. If you later decided Google Gemini was cheaper or better for your use case, you would have to rewrite all your API integration code.

Spring AI provides an **abstraction layer**. You write your code once using Spring AI interfaces. To swap out the underlying AI provider (Google, OpenAI, Anthropic, Ollama, etc.), you simply:
1. Change the dependency in your `pom.xml`.
2. Update the API key in your `application.properties`.

No code rewrite required!

## 2. Your Foundational Setup

In your project, you've chosen Google GenAI.

### Dependencies
Your `pom.xml` contains the starter for Google GenAI:
```xml
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-starter-model-google-genai</artifactId>
</dependency>
```

### The ChatClient (The Core API)

The most important component you'll use day-to-day in Spring AI is the `ChatClient`. It provides a modern, fluent API to interact with AI models.

Here is what your `ChatService` looks like:

```java
package io.wulfcodes.ai.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    private final ChatClient chatClient;

    // 1. Spring auto-configures a ChatClient.Builder based on your Google GenAI dependency
    public ChatService(ChatClient.Builder builder) {
        this.chatClient = builder.build(); 
    }

    public String fireQuery(String query) {
        // 2. The fluent API in action
        return chatClient.prompt(query) // Prepare the message
                         .call()        // Send request to the AI provider
                         .content();    // Extract the text from the response
    }
}
```

## 3. The Roadmap (What's Next)

As we progress through the chapters, we will build out your application using the core capabilities of Spring AI:

1. **Chapter 2: Prompt Templates**: Moving away from hardcoded strings and using dynamic templates (like `Tell me a joke about {topic}`).
2. **Chapter 3: Structured Output**: Forcing the AI to return JSON and having Spring AI automatically map it to Java Records/Classes.
3. **Chapter 4: Streaming Responses**: Implementing chunk-by-chunk streaming for long responses so the user doesn't have to wait.
4. **Chapter 5: Function Calling (Tools)**: Giving the AI the ability to execute your Java methods to fetch real-time data.
5. **Chapter 6: RAG (Retrieval-Augmented Generation)**: Letting the AI "read" your own documents or database to answer questions.

---
*Ready to move on? Let me know which chapter you want to tackle next, and we'll write the code and the lesson!*
