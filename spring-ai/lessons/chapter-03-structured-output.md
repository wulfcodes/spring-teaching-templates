# Chapter 3: Structured Output

In the previous chapters, we received plain text from the AI. However, if you are building a real application, you often need data in a structured format like JSON so your code can process it (e.g., saving it to a database or displaying it in a UI table).

Spring AI makes this incredibly easy with **Structured Output Converters**.

## The Problem
If you just ask an AI to "return JSON", it might return Markdown-wrapped JSON, or it might change the keys, or it might add conversational text like "Here is your JSON: ...". This breaks your code.

## The Spring AI Solution
You define a Java **Record** or **Class**, and Spring AI:
1. Automatically adds instructions to the prompt telling the AI exactly what JSON schema to follow.
2. Automatically parses the JSON response into your Java object.

## How it works

### 1. Define your data structure
```java
public record MovieReview(String title, int rating, String summary) {}
```

### 2. Use the `.entity()` method
In the fluent `ChatClient` API, you just use the `.entity()` method instead of `.content()`:

```java
MovieReview review = chatClient.prompt()
    .user("Review the movie Interstellar")
    .call()
    .entity(MovieReview.class); // Magic! Returns a Java object.
```

## Task: Let's extract Author Stats

We are going to ask the AI to give us structured information about a famous author.

### Step 1: Create a record `AuthorStats`
We'll create this in a new package `io.wulfcodes.ai.model`.

### Step 2: Update ChatService.java
Add a method `getAuthorStats(String authorName)`.

### Step 3: Update CLI
Call this new method and print the fields of the object.

---
*Ready to see the AI act like a structured API? I'll update your code now!*
