package com.example.gpt.demo.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ChatCompletionRequest(

        String model,

        List<ChatMessage> messages,

        Double temperature,

        @JsonProperty("max_tokens")
        Integer maxTokens
) {

    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record ChatMessage(

            String role,

            String content

    ) {
    }

    public static ChatCompletionRequest of(String message,
                                           String role) {

        List<ChatMessage> chatMessages = List.of(ChatMessage.builder()
                .content(message)
                .role(role)
                .build());

        return ChatCompletionRequest.builder()
                .messages(chatMessages)
                .build();
    }
}
