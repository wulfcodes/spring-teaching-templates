package io.wulfcodes.ai.service;

import io.wulfcodes.ai.model.AuthorStats;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class ChatService {

    private ChatClient chatClient;

    @Autowired
    public ChatService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    public String fireQuery(String query) {
        return chatClient.prompt(query).call().content();
    }

    public String generateJoke(String topic) {
        return chatClient.prompt()
                .user(u -> u.text("Tell me a funny joke about {topic}")
                            .param("topic", topic))
                .call()
                .content();
    }

    public AuthorStats getAuthorStats(String authorName) {
        return chatClient.prompt()
                .user(u -> u.text("Give me stats about the author {name}.")
                            .param("name", authorName))
                .call()
                .entity(AuthorStats.class);
    }

    public Flux<String> streamChat(String query) {
        return chatClient.prompt(query)
                .stream()
                .content();
    }


}
