package io.wulfcodes.robot.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import io.wulfcodes.robot.model.DatabaseRecommendation;

@Service
public class AiService {

    private final ChatClient chatClient;

    public AiService(ChatClient.Builder builder) {
        // The builder automatically picks up your Groq config from application.yml
        this.chatClient = builder.build();
    }

    public void askQuestion(String question) {
        System.out.println("Prompt: " + question);

        String response = chatClient.prompt()
                                    .user(question)
                                    .call()
                                    .content();

        System.out.println("Response: \n" + response);
    }

    public DatabaseRecommendation getDatabaseAdvice(String useCase) {
        System.out.println("--- Analyzing use case for database selection ---");

        return chatClient.prompt()
                         .user(u -> u.text("Recommend the absolute best database technology for this specific use case: {useCase}. Be highly technical.")
                                     .param("useCase", useCase))
                         .call()
                         .entity(DatabaseRecommendation.class); // This is where the magic happens
    }

    public void askQuestiontoGrumpy(String question) {
        String response = chatClient.prompt()
                                    // The System prompt sets the rules
                                    .system("You are a grumpy, cynical senior developer who hates modern frameworks. Keep your answer to exactly one sentence.")
                                    // The User prompt asks the question
                                    .user(question)
                                    .call()
                                    .content();

        System.out.println(response);
    }
}