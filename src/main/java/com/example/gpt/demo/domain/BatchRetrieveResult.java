package com.example.gpt.demo.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record BatchRetrieveResult(

        String id,
        @JsonProperty("custom_id")
        String customId,
        Response response,
        Object error
) {

    public record Response(
            @JsonProperty("status_code")
            Integer statusCode,
            @JsonProperty("request_id")
            String requestId,
            Body body) {
    }

    public record Body(
            String id,
            String object,
            Long created,
            String model,
            List<Choice> choices,
            Usage usage,
            @JsonProperty("service_tier")
            String serviceTier,
            @JsonProperty("system_fingerprint")
            String systemFingerprint) {
    }

    public record Choice(
            Integer index,
            Message message,
            Object logprobs,
            @JsonProperty("finish_reason")
            String finishReason) {
    }

    public record Message(
            String role,
            String content,
            Object refusal,
            List<Object> annotations) {
    }

    public record Usage(
            @JsonProperty("prompt_tokens")
            Integer promptTokens,
            @JsonProperty("completion_tokens")
            Integer completionTokens,
            @JsonProperty("total_tokens")
            Integer totalTokens,
            @JsonProperty("prompt_tokens_details")
            PromptTokensDetails promptTokensDetails,
            @JsonProperty("completion_tokens_details")
            CompletionTokensDetails completionTokensDetails) {
    }

    public record PromptTokensDetails(
            @JsonProperty("cached_tokens")
            Integer cachedTokens,
            @JsonProperty("audio_tokens")
            Integer audioTokens) {
    }

    public record CompletionTokensDetails(
            @JsonProperty("reasoning_tokens")
            Integer reasoningTokens,
            @JsonProperty("audio_tokens")
            Integer audioTokens,
            @JsonProperty("accepted_prediction_tokens")
            Integer acceptedPredictionTokens,
            @JsonProperty("rejected_prediction_tokens")
            Integer rejectedPredictionTokens) {
    }
}

