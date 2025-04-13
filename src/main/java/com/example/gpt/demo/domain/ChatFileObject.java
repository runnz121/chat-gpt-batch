package com.example.gpt.demo.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ChatFileObject(

        String id,
        String object,

        Long bytes,

        @JsonProperty("created_at")
        Long createdAt,

        @JsonProperty("expires_at")
        Long expiresAt,

        String filename,

        String purpose,

        String status,

        @JsonProperty("status_details")
        String statusDetails

) {
}

