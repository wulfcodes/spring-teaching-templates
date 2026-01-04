package io.wulfcodes.serdes;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.wulfcodes.serdes.util.JsonUtils;

@Component
public class SerDesApplicationCLI implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        ObjectMapper objectMapper = JsonUtils.getMapper();

        deserializeJsonObject(objectMapper);
        serializeJsonObject(objectMapper);
        deserializeJsonArray(objectMapper);
        serializeJsonArray(objectMapper);
    }


    private void deserializeJson(ObjectMapper objectMapper) throws JsonProcessingException {
        String json = """
            {
                "name": "Swayam",
                "age": 30,
            }
            """;

        JsonNode node = objectMapper.readTree(json);
    }

    private void deserializeJsonObject(ObjectMapper objectMapper) throws JsonProcessingException {
        String json = """
            {
                "name": "Swayam",
                "age": 30,
                "marks": "78.90",
                "isDeveloper": true
            }
            """;

        JsonNode node = objectMapper.readTree(json);

        if (node.isObject()) {
            ObjectNode objectNode = (ObjectNode) node;
            String name = objectNode.get("name").asText();
            int age = objectNode.get("age").asInt();
            double marks = objectNode.get("marks").asDouble(0.0);
            boolean isDeveloper = objectNode.get("isDeveloper").asBoolean(false);
            System.out.println(name + " " + age + " " + marks + " " + isDeveloper);
        }
    }

    private void serializeJsonObject(ObjectMapper objectMapper) throws JsonProcessingException {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("name", "Swayam");
        objectNode.put("age", 30);
        objectNode.put("marks", "78.90");
        objectNode.put("isDeveloper", true);

        objectNode.remove("marks");

        String json = objectMapper.writeValueAsString(objectNode);
        System.out.println(json);
    }

    private void deserializeJsonArray(ObjectMapper objectMapper) throws JsonProcessingException {
        String json = """
            [
                "Swayam",
                30,
                78.9
            ]
            """;

        JsonNode node = objectMapper.readTree(json);
        if (node.isArray()) {
            ArrayNode arrayNode = (ArrayNode) node;
            for (JsonNode element : arrayNode)
                System.out.println(element.asText());

            int age = arrayNode.get(1).asInt();
            System.out.println(age);
        }
    }

    private void serializeJsonArray(ObjectMapper objectMapper) throws JsonProcessingException {
        ArrayNode arrayNode = objectMapper.createArrayNode();
        arrayNode.add("Swayam");
        arrayNode.add(30);
        arrayNode.add(78.9);

        arrayNode.remove(2);

        String json = objectMapper.writeValueAsString(arrayNode);
        System.out.println(json);
    }
}
