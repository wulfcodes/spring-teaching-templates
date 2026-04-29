package io.wulfcodes.ai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import io.wulfcodes.ai.service.ChatService;

@Controller
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @GetMapping
    @ResponseBody
    public ResponseEntity<String> chat(@RequestParam("query") String query) {
        String response = chatService.fireQuery(query);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/joke")
    @ResponseBody
    public ResponseEntity<String> joke(@RequestParam("topic") String topic) {
        return ResponseEntity.ok(chatService.generateJoke(topic));
    }

}
