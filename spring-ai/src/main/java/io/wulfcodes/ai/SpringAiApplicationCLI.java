package io.wulfcodes.ai;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import io.wulfcodes.ai.service.ChatService;

@Component
public class SpringAiApplicationCLI implements CommandLineRunner {

    @Autowired
    private ChatService chatService;


    @Override
    public void run(String... args) throws Exception {
        String query = "How are you?";
        System.out.println(chatService.fireQuery(query));
    }
}
