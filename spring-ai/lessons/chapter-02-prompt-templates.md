# Chapter 2: Prompt Templates

In Chapter 1, we simply sent a raw string to the AI. In a real application, you rarely want to do that. You usually have a "template" and you want to swap in variables (like a user's name, a product name, or a topic).

## Why use Prompt Templates?

1. **Safety**: Just like SQL injection, you want to avoid "Prompt Injection" by separating the instructions from the user data.
2. **Reusability**: You can define a prompt once and reuse it with different data.
3. **Cleanliness**: It keeps your Java code clean and your prompt logic separate.

## How it works in Spring AI

Spring AI uses a `{variable}` syntax in strings to denote placeholders.

### 1. Simple Template

```java
String userTopic = "space exploration";
PromptTemplate template = new PromptTemplate("Tell me a fascinating fact about {topic}");
Prompt prompt = template.create(Map.of("topic", userTopic));

String response = chatClient.prompt(prompt).call().content();
```

### 2. Using the Fluent API (Modern Way)

You can actually do this directly inside the `ChatClient` fluent API without manually creating a `PromptTemplate` object:

```java
return chatClient.prompt()
    .user(u -> u.text("Tell me a joke about {topic}")
                .param("topic", topic))
    .call()
    .content();
```

## Task: Let's implement a "Joke Generator"

We are going to add a new method to your `ChatService` that uses a template to generate jokes about a specific topic.

### Step 1: Update ChatService.java
We will add a method `generateJoke(String topic)`.

### Step 2: Update ChatController.java
We will add a GET endpoint `/chat/joke?topic=dogs`.

---
*Ready? I'm going to update your code now to implement this!*
