package com.example.gpt.demo.ui;


import com.example.gpt.demo.application.OpenApiApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OpenApiController {

    private final OpenApiApplicationService openApiApplicationService;

    // 배치 생성 요청
    @PostMapping("/batch")
    public void batchUpdate() {

        openApiApplicationService.uploadBatch();
    }

    // 배치 아이디로 파일 생성되었는지 확인
    @GetMapping("/batch/{batchId}")
    public void getBatchRetrieve(@PathVariable String batchId) {

        openApiApplicationService.getBatchRetrieve(batchId);
    }

    // 배치로 생성된 파일 다운로드
    @GetMapping("/file/{fileId}")
    public void results(@PathVariable String fileId) {

        openApiApplicationService.getRetrieveResults(fileId);
    }
}
