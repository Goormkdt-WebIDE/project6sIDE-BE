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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/projects")
@Tag(name = "프로젝트 API")
public class ProjectController {

    private final CodeService codeService;
    private final ProjectService projectService;

    @Operation(summary = "프로젝트 전체 구조 조회 API")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getProject(@RequestBody ProjectGetReqDto dto) {
        String loginUserEmail = getLoginUserEmail();
        Project project = projectService.getProject(loginUserEmail, dto);
        return ResponseEntity.ok().body(project);
    }

    @Operation(summary = "사용자 프로젝트 전체 조회 API")
    @GetMapping("/user")
    public ResponseEntity<?> getProjects() {
        String loginUserEmail = getLoginUserEmail();
        List<ProjectByUserGetResDto> projects = projectService.getProjects(loginUserEmail);
        return ResponseEntity.ok().body(projects);
    }

    @Operation(summary = "프로젝트 저장 & 업데이트 API")
    @PostMapping("/save")
    public ResponseEntity<?> saveProject(@RequestBody ProjectSaveReqDto dto) {
        String loginUserEmail = getLoginUserEmail();
        projectService.save(loginUserEmail, dto);
        return ResponseEntity.ok().body("ok");
    }

    @Operation(summary = "프로젝트 삭제 API", description = "프로젝트 삭제")
    @DeleteMapping("/{projectId}")
    public ResponseEntity<?> deleteProject(@PathVariable String projectId) {
        String loginUserEmail = getLoginUserEmail();
        projectService.deleteProject(loginUserEmail, projectId);
        return ResponseEntity.ok().body("ok");
    }

    @Operation(summary = "최상단 디렉토리 저장 & 업데이트 API", description = "최상단 디렉토리 저장 & 업데이트")
    @PostMapping("/{projectId}/directories")
    public ResponseEntity<?> saveDirectory(@PathVariable String projectId,
                                           @RequestBody DirectoryReqDto dto) {
        String loginUserEmail = getLoginUserEmail();
        projectService.saveDirectory(loginUserEmail, projectId, dto);
        return ResponseEntity.ok().body("ok");
    }

    @Operation(summary = "디렉토리 업데이트 API", description = "디렉토리 업데이트")
    @PatchMapping("/{projectId}/{directoryId}/directories")
    public ResponseEntity<?> updateDirectory(@PathVariable String projectId,
                                             @PathVariable String directoryId,
                                             @RequestBody DirectoryReqDto dto) {
        String loginUserEmail = getLoginUserEmail();
        projectService.updateDirectory(loginUserEmail, projectId, directoryId, dto);
        return ResponseEntity.ok().body("ok");
    }

    @Operation(summary = "최상단 코드 저장 & 업데이트 API", description = "최상단 코드 저장 & 업데이트")
    @PostMapping("/{projectId}/code")
    public ResponseEntity<?> saveCode(@PathVariable String projectId,
                                      @RequestBody CodeSaveReqDto dto) {
        String loginUserEmail = getLoginUserEmail();
        codeService.saveCode(loginUserEmail, projectId, dto);
        return ResponseEntity.ok().body("ok");
    }

    @Operation(summary = "하위 디렉토리 저장 & 업데이트 API", description = "하위 디렉토리 저장 & 업데이트")
    @PostMapping("/directories/{projectId}/{directoryId}")
    public ResponseEntity<?> saveDirectoryInDirectory(@PathVariable String projectId,
                                                      @PathVariable String directoryId,
                                                      @RequestBody DirectoryReqDto dto) {
        String loginUserEmail = getLoginUserEmail();
        projectService.saveDirectoryInDirectory(loginUserEmail, projectId, directoryId, dto);
        return ResponseEntity.ok().body("ok");
    }

    @Operation(summary = "디렉토리 삭제 API", description = "디렉토리 삭제")
    @DeleteMapping("/directories/{projectId}/{directoryId}")
    public ResponseEntity<?> deleteDirectory(@PathVariable String projectId,
                                             @PathVariable String directoryId) {
        String loginUserEmail = getLoginUserEmail();
        projectService.deleteDirectory(loginUserEmail, projectId, directoryId);
        return ResponseEntity.ok().body("ok");
    }

    @Operation(summary = "디렉토리 안에 New 코드 저장 & 업데이트 API")
    @PostMapping("/directories/{projectId}/{directoryId}/code")
    public ResponseEntity<?> saveCodeInDirectory(@PathVariable String projectId,
                                                 @PathVariable String directoryId,
                                                 @RequestBody CodeSaveReqDto dto) {
        String loginUserEmail = getLoginUserEmail();
        codeService.saveCodeInDirectory(loginUserEmail, projectId, directoryId, dto);
        return ResponseEntity.ok().body("ok");
    }

    @Operation(summary = "프로젝트 & 디렉토리 코드 삭제 API")
    @DeleteMapping("/{projectId}/{codeId}/code")
    public ResponseEntity<?> deleteCode(@PathVariable String projectId,
                                        @PathVariable String codeId) {
        String loginUserEmail = getLoginUserEmail();
        codeService.deleteCode(loginUserEmail, projectId, codeId);
        return ResponseEntity.ok().body("ok");
    }

    @Operation(summary = "프로젝트 & 디렉토리 코드 업데이트 API")
    @PatchMapping("/{projectId}/{codeId}/code")
    public ResponseEntity<?> updateCode(@PathVariable String projectId,
                                        @PathVariable String codeId,
                                        @RequestBody CodeSaveReqDto dto) {
        String loginUserEmail = getLoginUserEmail();
        codeService.updateCode(loginUserEmail, projectId, codeId, dto);
        return ResponseEntity.ok().body("ok");
    }

    private String getLoginUserEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}