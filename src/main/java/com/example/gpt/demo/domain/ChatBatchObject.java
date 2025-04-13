package com.example.gpt.demo.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Map;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ChatBatchObject{

        private String id;

        private String object;

        private String endpoint;

        private Object errors;

        @JsonProperty("input_file_id")
        private String inputFileId;

        @JsonProperty("completion_window")
        private String completionWindow;

        private String status;

        @JsonProperty("output_file_id")
        private String outputFileId;

        @JsonProperty("error_file_id")
        private String errorFileId;

        @JsonProperty("created_at")
        private Long createdAt;

        @JsonProperty("in_progress_at")
        private Long inProgressAt;

        @JsonProperty("expires_at")
        private Long expiresAt;

        @JsonProperty("expired_at")
        private Long expiredAt;

        @JsonProperty("finalizing_at")
        private Long finalizingAt;

        @JsonProperty("completed_at")
        private Long completedAt;

        @JsonProperty("failed_at")
        private Long failedAt;

        @JsonProperty("cancelling_at")
        private Long cancellingAt;

        @JsonProperty("cancelled_at")
        private Long cancelledAt;

        @JsonProperty("request_counts")
        private Map<String, Integer> requestCounts;
}

