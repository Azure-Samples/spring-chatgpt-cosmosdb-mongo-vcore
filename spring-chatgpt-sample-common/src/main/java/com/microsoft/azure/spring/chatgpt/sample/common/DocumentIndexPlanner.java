package com.microsoft.azure.spring.chatgpt.sample.common;

import com.microsoft.azure.spring.chatgpt.sample.common.reader.SimpleFolderReader;
import com.microsoft.azure.spring.chatgpt.sample.common.vectorstore.CosmosDBVectorStore;
import com.microsoft.azure.spring.chatgpt.sample.common.vectorstore.DocEntry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
public class DocumentIndexPlanner {
    private final AzureOpenAIClient client;
    private final CosmosDBVectorStore vectorStore;
    public void buildFromFolder(String folderPath) throws IOException {
        if (folderPath == null) {
            throw new IllegalArgumentException("folderPath shouldn't be empty.");
        }
        final int[] dimensions = {0};
        SimpleFolderReader reader = new SimpleFolderReader(folderPath);
        TextSplitter splitter = new TextSplitter();

        reader.run((fileName, content) -> {

            log.info("String to process {}...", fileName);
            var textChunks = splitter.split(content);
            for (var chunk: textChunks) {
                var response = client.getEmbeddings(List.of(chunk));
                var embedding = response.getData().get(0).getEmbedding();
                if (dimensions[0] == 0) {
                    dimensions[0] = embedding.size();
                } else if (dimensions[0] != embedding.size()) {
                    throw new IllegalStateException("Embedding size is not consistent.");
                }
                String key = UUID.randomUUID().toString();
                vectorStore.saveDocument(key, DocEntry.builder()
                                .id(key)
                                .hash("")
                                .embedding(embedding)
                                .text(chunk)
                                .build());
            }
            return null;
        });
        vectorStore.createVectorIndex(100, dimensions[0], "COS");
        log.info("All documents are loaded to Cosmos DB vCore vector store.");
    }
}
