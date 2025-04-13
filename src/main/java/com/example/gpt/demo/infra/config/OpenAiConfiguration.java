package com.example.gpt.demo.infra.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@NoArgsConstructor
@ConfigurationProperties(prefix = "openai")
public class OpenAiConfiguration {

    private String url;
    private String apiKey;
}
