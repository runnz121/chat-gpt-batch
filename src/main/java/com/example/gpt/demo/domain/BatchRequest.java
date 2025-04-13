package com.example.gpt.demo.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record BatchRequest(

        @JsonProperty("input_file_id")
        String inputFileId,

        String endpoint,

        @JsonProperty("completion_window")
        String completionWindow

) {

    private static final String END_POINT = "/v1/chat/completions";

    public static BatchRequest of(String inputFileId) {

        return BatchRequest.builder()
                .inputFileId(inputFileId)
                .endpoint(END_POINT)
                .completionWindow("24h")
                .build();
    }
}
