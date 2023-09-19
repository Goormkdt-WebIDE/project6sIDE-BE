package com.project5s.IDEproject.controller;

import com.project5s.IDEproject.controller.dto.*;
import com.project5s.IDEproject.domain.Project;
import com.project5s.IDEproject.service.CodeService;
import com.project5s.IDEproject.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/projects")
@Tag(name = "프로젝트 API")
public class ProjectController {

    private final CodeService codeService;
    private final ProjectService projectService;

    @Operation(summary = "프로젝트 전체 구조 조회 API", description = "Swagger 문서 변환 Bug(@RequestBody 적용안됨) BODY JSON 으로 요청시 정상 작동")
    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getProject(@RequestBody ProjectGetReqDto dto) {
        Project project = projectService.getProject(dto);
        return ResponseEntity.ok().body(project);
    }

    @Operation(summary = "프로젝트 저장 & 업데이트 API")
    @PostMapping
    //TODO if front ready to use token, Use Authentication -> @AuthenticationPrincipal
    public ResponseEntity<?> saveProject(@RequestBody ProjectSaveReqDto dto) {
        projectService.save(dto);
        return ResponseEntity.ok().body("ok");
    }

    @Operation(summary = "디렉토리 저장 & 업데이트 API", description = "최상단 디렉토리 저장 & 업데이트")
    @PostMapping("/v2/directories/{projectId}")
    //TODO if front ready to use token, Use Authentication -> @AuthenticationPrincipal
    public ResponseEntity<?> saveDirectory(@PathVariable String projectId,
                                           @RequestBody DirectorySaveReqDto dto) {
        projectService.saveDirectory(projectId, dto);
        return ResponseEntity.ok().body("ok");
    }

    @Operation(summary = "하위 디렉토리 저장 & 업데이트 API", description = "하위 디렉토리 저장 & 업데이트")
    @PostMapping("/v2/directories/{directoryId}")
    public ResponseEntity<?> saveDirectoryInDirectory(@PathVariable String directoryId,
                                                      @RequestBody DirectorySaveReqDto dto) {
        projectService.saveDirectoryInDirectory(directoryId, dto);
        return ResponseEntity.ok().body("ok");
    }

    @Operation(summary = "디렉토리 안에 New 코드 저장 & 업데이트 API")
    @PostMapping("/v2/directories/{directoryId}/code")
    public ResponseEntity<?> saveCodeInDirectory(@PathVariable String directoryId,
                                                 @RequestBody CodeSaveReqDto dto) {
        codeService.saveCodeInDirectory(directoryId, dto);
        return ResponseEntity.ok().body("ok");
    }
}