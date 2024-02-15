package com.microsoft.azure.spring.chatgpt.sample.webapi.controllers;

import com.microsoft.azure.spring.chatgpt.sample.webapi.models.ChatHistoryItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableMongoRepositories(basePackages = "com.microsoft.azure.spring.chatgpt.sample.webapi.models")
@Component
@RequestMapping("/chathistory")
@RequiredArgsConstructor
@Slf4j
public class ChatHistoryController {

    private MongoTemplate mongoTemplate;

    @Autowired
    public ChatHistoryController(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @PostMapping("/save")
    public ChatHistoryItem chatHistory(@RequestBody ChatHistoryItem request) {
        this.mongoTemplate.save(request);
        return request;
    }
}
