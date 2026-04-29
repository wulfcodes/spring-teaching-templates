package io.wulfcodes.ai;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import io.wulfcodes.ai.model.AuthorStats;
import io.wulfcodes.ai.service.ChatService;

@Component
public class SpringAiApplicationCLI implements CommandLineRunner {

    @Autowired
    private ChatService chatService;


    @Override
    public void run(String... args) throws Exception {
        System.out.println("--- Spring AI CLI Demo ---");

        // // 1. Basic Query
        // System.out.println("\n[1] Basic Query: How are you?");
        // String basicResponse = chatService.fireQuery("How are you?");
        // System.out.println("AI: " + basicResponse);

        // // 2. Prompt Template Query (Joke)
        // System.out.println("\n[2] Joke Template: topic = Cats");
        // String jokeResponse = chatService.generateJoke("Cats");
        // System.out.println("AI: " + jokeResponse);

        // 3. Structured Output (Java Object)
        System.out.println("\n[3] Structured Output: author = J.R.R. Tolkien");
        AuthorStats stats = chatService.getAuthorStats("J.R.R. Tolkien");
        System.out.println("AI (AuthorStats Object): " + stats);
        System.out.println("Genre: " + stats.genre());
        System.out.println("Books Published: " + stats.booksPublished());

        // // 4. Streaming Output
        // System.out.println("\n[4] Streaming Output: Poem about Spring Boot");
        // chatService.streamChat("Write a short, enthusiastic poem about Spring Boot")
        //         .doOnNext(System.out::print)
        //         .blockLast(); // This ensures the CLI waits for the stream to finish
        
        System.out.println("\n--- Demo Complete ---");
    }
}
