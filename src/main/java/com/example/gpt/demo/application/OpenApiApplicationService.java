package com.example.gpt.demo.application;

import com.example.gpt.demo.domain.BatchRetrieveResult;
import com.example.gpt.demo.domain.ChatBatchObject;
import com.example.gpt.demo.domain.ChatCompletionBatchRequest;
import com.example.gpt.demo.domain.ChatCompletionRequest;
import com.example.gpt.demo.domain.ChatFileObject;
import com.example.gpt.demo.infra.client.OpenAiRequestRepository;
import com.example.gpt.demo.infra.config.FileToObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OpenApiApplicationService {

    private final ObjectMapper objectMapper;
    private final OpenAiRequestRepository openAiRequestRepository;

    public void uploadBatch() {

        List<ChatCompletionRequest> toRequests = new ArrayList<>();

        // TODO 요청 객체로 변환
        try {
            toRequests = FileToObject.fileToList("/data/request.json", ChatCompletionRequest.class);
        } catch (Exception ex) {
            log.error("에러발생 ", ex);
        }

        // 배치 생성 요청시 jsonl 타입으로 요청해야한다.
        String jsonL = toRequests.stream()
                .map(ChatCompletionBatchRequest::of)
                .map(this::toJson)
                .filter(Strings::isNotBlank)
                .collect(Collectors.joining("\n"));

        // TODO 임의 파일이름으로 생성
        Path filePath = Paths.get("output.jsonl");

        try {
            Files.write(filePath, jsonL.getBytes(StandardCharsets.UTF_8));
            log.info("파일 생성 : " + filePath.toAbsolutePath());
        } catch (IOException e) {
            log.error("파일 생성 에러 : ", e.getMessage());
        }

        // 배치 아이디 파일 요청
        ChatFileObject chatFileObject = openAiRequestRepository.requestOpenApiFile(filePath.toString());

        // 배치 생성 요청
        ChatBatchObject chatBatchObject = openAiRequestRepository.requestOpenApiBatch(chatFileObject.id());
    }

    public void getBatchRetrieve(String batchId) {

        // 배치 생성 조회 (batch-id로 조회) -> 배치 완료 후 output_file_id 생성됨으로 해당 값이 존재해야지 file_id 로 다운로드 받을 수 있다.
        ChatBatchObject retrieveBatchObject = openAiRequestRepository.retrieveOpenApiBatch(batchId);

        System.out.println(retrieveBatchObject);
    }

    public void getRetrieveResults(String inputFileId) {

        String retrieveResults = openAiRequestRepository.getRetrieveResults(inputFileId);
        List<BatchRetrieveResult> results = new ArrayList<>();

        try {
            ObjectMapper mapper = new ObjectMapper();
            for (String line : retrieveResults.split("\n")) {
                if (line.isBlank()) continue;
                BatchRetrieveResult record = mapper.readValue(line, BatchRetrieveResult.class);
                results.add(record);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        results.forEach(
                result -> {
                    BatchRetrieveResult.Choice choices = result.response().body().choices().get(0);
                    String content = choices.message().content();
                    System.out.println(content);
                }
        );
    }

    private String toJson(ChatCompletionBatchRequest request) {

        try {
            return objectMapper.writeValueAsString(request);
        }
        catch (Exception e) {
            return "";
        }
    }
}
