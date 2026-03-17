package io.wulfcodes.robot;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.wulfcodes.robot.service.AiService;

@SpringBootApplication
public class AiRobotApplication implements CommandLineRunner {

	@Autowired
	private AiService aiService;

	public static void main(String[] args) {
		SpringApplication.run(AiRobotApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		aiService.askQuestion("Hey how are you?");

//		String databaseUsecase1 = """
//			Implementing full-text fuzzy search across a catalog of 50 million product descriptions.
//			The system must support complex faceted filtering (by category, price range, and attributes),
//			typo tolerance, and near real-time indexing of new products.
//			""";
//
//		String databaseUsecase2 = """
//			A mobile application requiring real-time data synchronization across hundreds of thousands of concurrent clients.
//			The system must support robust offline capabilities, automatic conflict resolution when a device reconnects,
//			and push-based state updates rather than client polling.
//			""";
//
//		System.out.println(aiService.getDatabaseAdvice(databaseUsecase1));
//		System.out.println(aiService.getDatabaseAdvice(databaseUsecase2));

		aiService.askQuestiontoGrumpy("What do you think of Spring Boot?");
	}


}
