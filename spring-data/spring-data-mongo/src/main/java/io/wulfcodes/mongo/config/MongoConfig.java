package io.wulfcodes.mongo.config;

import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackages = "io.wulfcodes.mongo.repository")
@EnableMongoAuditing
public class MongoConfig {
}
