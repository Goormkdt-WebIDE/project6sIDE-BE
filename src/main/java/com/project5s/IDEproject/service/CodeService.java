package com.project5s.IDEproject.service;

import com.project5s.IDEproject.controller.dto.CodeSaveReqDto;
import com.project5s.IDEproject.domain.Code;
import com.project5s.IDEproject.domain.Directory;
import com.project5s.IDEproject.domain.Project;
import com.project5s.IDEproject.exception.AppException;
import com.project5s.IDEproject.exception.ErrorCode;
import com.project5s.IDEproject.repository.CodeRepository;
import com.project5s.IDEproject.repository.DirectoryRepository;
import com.project5s.IDEproject.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CodeService {

    private final CodeRepository codeRepository;
    private final DirectoryRepository directoryRepository;
    private final ProjectRepository projectRepository;

    public void saveCodeInDirectory(String directoryId, CodeSaveReqDto dto) {
        Directory targetDirectory = directoryRepository.findById(directoryId).orElseThrow();
        Code code = new Code(dto);

        codeRepository.save(code);
        targetDirectory.saveCode(code);
        directoryRepository.save(targetDirectory);
    }

    public void deleteCode(String codeId) {
        codeRepository.deleteById(codeId);
    }

    public void saveCode(String projectId, CodeSaveReqDto dto) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new AppException(ErrorCode.PROJECT_NOT_FOUND, ErrorCode.PROJECT_NOT_FOUND.getMessage()));

        Code code = new Code(dto);
        codeRepository.save(code);
        project.saveCode(code);
        projectRepository.save(project);
    }

    public void updateCode(String codeId, CodeSaveReqDto dto) {
        Code code = codeRepository.findById(codeId)
                .orElseThrow(() -> new AppException(ErrorCode.CODE_NOT_FOUND, ErrorCode.CODE_NOT_FOUND.getMessage()));

        code.update(dto);
        codeRepository.save(code);
    }
}
