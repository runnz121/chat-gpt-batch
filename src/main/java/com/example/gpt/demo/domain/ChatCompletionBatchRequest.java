package com.example.gpt.demo.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.UUID;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ChatCompletionBatchRequest(

        @JsonProperty("custom_id")
        String customId,

        String method,

        String url,

        ChatCompletionRequest body
) {

    public static ChatCompletionBatchRequest of(ChatCompletionRequest body) {

        return ChatCompletionBatchRequest.builder()
                .customId(UUID.randomUUID().toString())
                .method("POST")
                .url("/v1/chat/completions")
                .body(body)
                .build();
    }
}
