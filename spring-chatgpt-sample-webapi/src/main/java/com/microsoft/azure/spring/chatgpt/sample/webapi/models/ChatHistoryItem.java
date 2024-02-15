package com.microsoft.azure.spring.chatgpt.sample.webapi.models;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.math.BigInteger;
import java.util.List;

@Data
@Builder
@Jacksonized
@Document(collection = "chathistory")
public class ChatHistoryItem {
    @Id
    private String id;
    private String title;
    private BigInteger timestamp;
    private List<HistoryMessage> messages;

    public static class HistoryMessage {
        private String sender;
        private String text;
        private BigInteger timestamp;
        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public BigInteger getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(BigInteger timestamp) {
            this.timestamp = timestamp;
        }

        public String getSender() {
            return sender;
        }

        public void setSender(String sender) {
            this.sender = sender;
        }
    }

}



