package com.microsoft.azure.spring.chatgpt.sample.cli;

import com.azure.ai.openai.OpenAIClientBuilder;
import com.azure.core.credential.AzureKeyCredential;
import com.microsoft.azure.spring.chatgpt.sample.common.AzureOpenAIClient;
import com.microsoft.azure.spring.chatgpt.sample.common.DocumentIndexPlanner;
import com.microsoft.azure.spring.chatgpt.sample.common.vectorstore.CosmosDBVectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class Config {

    @Value("${AZURE_OPENAI_EMBEDDINGDEPLOYMENTID}")
    private String embeddingDeploymentId;

    @Value("${AZURE_OPENAI_ENDPOINT}")
    private String endpoint;

    @Value("${AZURE_OPENAI_APIKEY}")
    private String apiKey;

    private MongoTemplate mongoTemplate;

    public Config(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Bean
    public DocumentIndexPlanner planner(AzureOpenAIClient openAIClient, CosmosDBVectorStore vectorStore) {
        return new DocumentIndexPlanner(openAIClient, vectorStore);
    }

    @Bean
    public AzureOpenAIClient AzureOpenAIClient() {
        var innerClient = new OpenAIClientBuilder()
                .endpoint(endpoint)
                .credential(new AzureKeyCredential(apiKey))
                .buildClient();
        return new AzureOpenAIClient(innerClient, embeddingDeploymentId, null);
    }


    @Bean
    public CosmosDBVectorStore vectorStore() {
        return new CosmosDBVectorStore( mongoTemplate);
    }
}
