package com.example.gpt.demo.infra.client;

import com.example.gpt.demo.domain.BatchRequest;
import com.example.gpt.demo.domain.ChatBatchObject;
import com.example.gpt.demo.domain.ChatFileObject;
import com.example.gpt.demo.infra.config.OpenAiConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class OpenAiRequestRepository {

    private final OpenAiConfiguration config;

    public ChatFileObject requestOpenApiFile(String filePath) {

        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
        formData.add("purpose", "batch");
        formData.add("file", new FileSystemResource(filePath));

        WebClient webClient = WebClient.builder()
                .baseUrl(config.getUrl() + "/files")
                .defaultHeader("Authorization", "Bearer " + config.getApiKey())
                .build();

        return webClient.post()
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(formData))
                .retrieve()
                .bodyToMono(ChatFileObject.class)
                .block();
    }

    public ChatBatchObject requestOpenApiBatch(String fileObjectId) {

        BatchRequest batchRequest = BatchRequest.of(fileObjectId);

        WebClient webClient = WebClient.builder()
                .baseUrl(config.getUrl() + "/batches")
                .defaultHeader("Authorization", "Bearer " + config.getApiKey())
                .build();

        return webClient.post()
                .bodyValue(batchRequest)
                .retrieve()
                .bodyToMono(ChatBatchObject.class)
                .block();
    }

    public ChatBatchObject retrieveOpenApiBatch(String batchId) {

        WebClient webClient = WebClient.builder()
                .baseUrl(config.getUrl() + "/batches/" + batchId)
                .defaultHeader("Authorization", "Bearer " + config.getApiKey())
                .build();

        return webClient.get()
                .retrieve()
                .bodyToMono(ChatBatchObject.class)
                .block();
    }

    public String getRetrieveResults(String fileId) {

        WebClient webClient = WebClient.builder()
                .baseUrl(config.getUrl() + "/files/" + fileId + "/content")
                .defaultHeader("Authorization", "Bearer " + config.getApiKey())
                .build();

        return webClient.get()
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
