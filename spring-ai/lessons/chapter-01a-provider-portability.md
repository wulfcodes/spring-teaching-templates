# Chapter 1.5: Provider Portability (Switching to Groq)

One of the greatest strengths of Spring AI is **portability**. You just asked to switch your provider from Google GenAI to Groq. 

In a traditional application, you would have to delete all your Google-specific API code and write new Groq-specific API code. With Spring AI, your Java code **doesn't change at all**.

Here is exactly what we did to switch to Groq.

## 1. Changing the Dependency

Groq provides an API that is 100% compatible with the OpenAI API. Because of this, Spring AI doesn't need a dedicated "Groq starter". We just use the standard **OpenAI starter**.

We removed `spring-ai-starter-model-google-genai` and added this to `pom.xml`:
```xml
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-openai-spring-boot-starter</artifactId>
</dependency>
```

## 2. Pointing OpenAI to Groq

In your `application.properties`, we tell the OpenAI starter to send its requests to Groq's URL instead of OpenAI's URL:

```properties
# Tell the OpenAI starter to use Groq's base URL
spring.ai.openai.base-url=https://api.groq.com/openai/v1

# Provide your Groq API Key
spring.ai.openai.api-key=${GROQ_API_KEY}

# Explicitly choose a model that Groq supports
spring.ai.openai.chat.options.model=llama3-8b-8192
```

## 3. The Result

If you look at your `ChatController.java` and `ChatService.java`, **no code was changed**. Your `ChatClient` automatically understands that it should now talk to Groq using the Llama 3 model!

> [!IMPORTANT]
> To run the app now, you must make sure you have an environment variable set called `GROQ_API_KEY` with your actual Groq API key!

---
*We've successfully migrated providers in just a few lines of configuration! Let me know if you want to test this, or if you are ready to jump into **Chapter 2: Prompt Templates**!*
