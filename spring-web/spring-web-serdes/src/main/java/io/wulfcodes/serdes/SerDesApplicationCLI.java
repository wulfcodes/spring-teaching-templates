package io.wulfcodes.serdes;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.BooleanNode;
import com.fasterxml.jackson.databind.node.DoubleNode;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.LongNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import io.wulfcodes.serdes.model.Employee;
import io.wulfcodes.serdes.model.User;

@Component
public class SerDesApplicationCLI implements CommandLineRunner {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void run(String... args) throws Exception {
        deserializeJsonObject();
        serializeJsonObject();
        deserializeJsonArray();
        serializeJsonArray();
        deserializeJsonFull();
        serializeJsonFull();
        deserializeJsonFullToPojo();
    }


    private void deserializeJson() throws JsonProcessingException {
        String json = """
            {
                "name": "Swayam",
                "age": 30,
            }
            """;

        JsonNode node = objectMapper.readTree(json);
    }

    private void deserializeJsonObject() throws JsonProcessingException {
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

    private void serializeJsonObject() throws JsonProcessingException {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("name", "Swayam");
        objectNode.put("age", 30);
        objectNode.put("marks", "78.90");
        objectNode.put("isDeveloper", true);

        objectNode.remove("marks");

        String json = objectMapper.writeValueAsString(objectNode);
        System.out.println(json);
    }

    private void deserializeJsonArray() throws JsonProcessingException {
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

    private void serializeJsonArray() throws JsonProcessingException {
        ArrayNode arrayNode = objectMapper.createArrayNode();
        arrayNode.add("Swayam");
        arrayNode.add(30);
        arrayNode.add(78.9);

        arrayNode.remove(2);

        String json = objectMapper.writeValueAsString(arrayNode);
        System.out.println(json);
    }

    private void deserializeJsonFull() throws JsonProcessingException {
        String json = """
            {
              "userId": 1024,
              "username": "coder_jane",
              "isActive": true,
              "balance": 450.75,
              "nickname": null,
              "tags": ["developer", "admin"],
              "verifications": {
                "email": true,
                "phone": false
              },
              "preferences": {
                "theme": "dark",
                "notifications": false
              },
              "loginHistories": [
                {
                  "ip": "192.168.1.5",
                  "timestamp": 169877600032144
                },
                {
                  "ip": "10.0.1.42",
                  "proxies": {
                    "ipA": "10.0.1.43",
                    "ipB": "10.0.1.42"
                  },
                  "timestamp": 169866400032155
                }
              ]
            }
            """;

        System.out.println("------------");
        ObjectNode user = (ObjectNode)objectMapper.readTree(json);
        IntNode userId = (IntNode)user.get("userId");
        TextNode username = (TextNode)user.get("username");
        BooleanNode isActive = (BooleanNode)user.get("isActive");
        DoubleNode balance = (DoubleNode)user.get("balance");
        NullNode nickname = (NullNode)user.get("nickname");
        System.out.println(userId.asInt() + " " + username.asText() + " " + isActive.asBoolean() + " " + balance.asDouble() + " " + nickname.isNull());

        ArrayNode tags = (ArrayNode)user.get("tags");
        TextNode tags0 = (TextNode)tags.get(0);
        TextNode tags1 = (TextNode)tags.get(1);
        System.out.println(tags0.asText() + " " + tags1.asText());

        ObjectNode preferences = (ObjectNode)user.get("preferences");
        TextNode theme = (TextNode)preferences.get("theme");
        BooleanNode notifications = (BooleanNode)preferences.get("notifications");
        System.out.println(theme.asText() + " " + notifications.asBoolean());

        ArrayNode loginHistories = (ArrayNode)user.get("loginHistories");
        ObjectNode loginHistories0 = (ObjectNode)loginHistories.get(0);
        TextNode ip0 = (TextNode)loginHistories0.get("ip");
        LongNode timestamp0 = (LongNode)loginHistories0.get("timestamp");
        ObjectNode loginHistories1 = (ObjectNode)loginHistories.get(1);
        TextNode ip1 = (TextNode)loginHistories1.get("ip");
        ObjectNode proxies1 = (ObjectNode)loginHistories1.get("proxies");
        TextNode ip1A1 = (TextNode)proxies1.get("ipA");
        TextNode ip1B1 = (TextNode)proxies1.get("ipB");
        LongNode timestamp1 = (LongNode)loginHistories1.get("timestamp");
        System.out.println(ip0.asText() + " " + ip1.asText() + " " + ip1A1.asText() + " " + ip1B1.asText() + " " + timestamp0.asLong() + " " + timestamp1.asLong());
    }

    public void serializeJsonFull() throws JsonProcessingException {
        ObjectNode user = objectMapper.createObjectNode();
        user.put("userId", 1024);
        user.put("username", "coder_jane");
        user.put("isActive", true);
        user.put("balance", 450.75);
        user.putNull("nickname");

        ArrayNode tags = objectMapper.createArrayNode();
        tags.add("developer");
        tags.add("admin");
        user.set("tags", tags);

        ObjectNode preferences = objectMapper.createObjectNode();
        preferences.put("theme", "dark");
        preferences.put("notifications", false);
        user.set("preferences", preferences);

        ArrayNode loginHistories = objectMapper.createArrayNode();
        ObjectNode loginEntry1 = objectMapper.createObjectNode();
        loginEntry1.put("ip", "192.168.1.5");
        loginEntry1.put("timestamp", 1698776000L);
        loginHistories.add(loginEntry1);

        ObjectNode loginEntry2 = objectMapper.createObjectNode();
        loginEntry2.put("ip", "10.0.1.42");
        ObjectNode proxies = objectMapper.createObjectNode();
        proxies.put("ipA", "10.0.1.43");
        proxies.put("ipB", "10.0.1.42");
        loginEntry2.set("proxies", proxies);
        loginEntry2.put("timestamp", 1698664000L);
        loginHistories.add(loginEntry2);

        user.set("loginHistories", loginHistories);

        String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(user);
        System.out.println(json);
    }

    public void deserializeJsonFullToPojo() throws JsonProcessingException {
        String json = """
            {
              "userId": 1024,
              "username": "coder_jane",
              "isActive": true,
              "balance": 450.75,
              "nickname": null,
              "tags": ["developer", "admin"],
              "verifications": {
                "email": true,
                "phone": false
              },
              "preferences": {
                "theme": "dark",
                "notifications": false
              },
              "loginHistories": [
                {
                  "ip": "192.168.1.5",
                  "timestamp": 169877600032144
                },
                {
                  "ip": "10.0.1.42",
                  "proxies": {
                    "ipA": "10.0.1.43",
                    "ipB": "10.0.1.42"
                  },
                  "timestamp": 169866400032155
                }
              ]
            }
            """;

        User user = objectMapper.readValue(json, User.class);
        System.out.println(user);
    }

    public void deserializeJsonArrayToPojo() throws JsonProcessingException {
        String json = """
            [
                {
                    "name": "Swayam",
                    "age": 30,
                    "marks": "78.90",
                    "isDeveloper": false
                },
                {
                    "name": "DJ",
                    "age": 23,
                    "marks": "56.90",
                    "isDeveloper": true
                }
            ]
            """;

        List<Employee> employees = objectMapper.readValue(json, new TypeReference<List<Employee>>() {});
    }

    public void deserializeJsonFullToPojoWithAnnotations() throws JsonProcessingException {
        String json = """
            {
              "order_id": "ORD-999",
              "placed_at": "2023-11-25 10:30:00",
              "status": "confirmed",
              "total_amount": 150.50,
              "internal_code": "SECRET_123",
              "payment": {
                "type": "credit_card",
                "card_number": "4111-xxxx-xxxx-1111",
                "cvv": 123
              },
              "ship_street": "123 Tech Lane",
              "ship_city": "Metropolis",
              "campaign_id": "black_friday_2023",
              "referer": "google_ads",
              "username": "Master Blaster Sachin Tendulkar",
              "email": "sosouser@gmail.com"
            }
            """;
    }
}
