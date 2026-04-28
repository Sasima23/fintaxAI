package com.fintax.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.*;

@Service
public class ChatbotService {
    
    @Value("${openai.api.key}")
    private String apiKey;
    
    @Value("${openai.api.url}")
    private String apiUrl;
    
    @Value("${openai.model}")
    private String model;
    
    public String getChatResponse(String userMessage) {
        if (apiKey == null || apiKey.isEmpty()) {
            return "OpenAI API key is not configured. Please set the OPENAI_API_KEY environment variable.";
        }
        
        try {
            RestTemplate restTemplate = new RestTemplate();
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(apiKey);
            
            Map<String, Object> message = new HashMap<>();
            message.put("role", "user");
            message.put("content", "You are a helpful tax advisor assistant for India. Answer the following tax-related question concisely: " + userMessage);
            
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", model);
            requestBody.put("messages", List.of(
                Map.of("role", "system", "content", "You are a helpful tax advisor assistant specializing in Indian tax laws. Provide concise, accurate information about income tax, corporate tax, GST, and property tax."),
                message
            ));
            requestBody.put("max_tokens", 500);
            requestBody.put("temperature", 0.7);
            
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
            
            ResponseEntity<Map> response = restTemplate.postForEntity(apiUrl, request, Map.class);
            
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                Map<String, Object> responseBody = response.getBody();
                List<Map<String, Object>> choices = (List<Map<String, Object>>) responseBody.get("choices");
                if (choices != null && !choices.isEmpty()) {
                    Map<String, Object> firstChoice = choices.get(0);
                    Map<String, String> messageContent = (Map<String, String>) firstChoice.get("message");
                    return messageContent.get("content");
                }
            }
            
            return "Sorry, I couldn't process your request. Please try again.";
            
        } catch (Exception e) {
            return "Error communicating with AI service: " + e.getMessage();
        }
    }
}
