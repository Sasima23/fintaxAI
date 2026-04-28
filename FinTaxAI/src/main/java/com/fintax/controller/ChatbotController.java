package com.fintax.controller;

import com.fintax.service.ChatbotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/chat")
public class ChatbotController {
    
    @Autowired
    private ChatbotService chatbotService;
    
    @PostMapping
    public ResponseEntity<Map<String, String>> chat(@RequestBody Map<String, String> request) {
        String userMessage = request.get("message");
        
        if (userMessage == null || userMessage.trim().isEmpty()) {
            return ResponseEntity.badRequest()
                .body(Map.of("response", "Please provide a message."));
        }
        
        String response = chatbotService.getChatResponse(userMessage);
        return ResponseEntity.ok(Map.of("response", response));
    }
}
