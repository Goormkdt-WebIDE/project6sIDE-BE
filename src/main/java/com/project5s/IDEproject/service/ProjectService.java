package com.project5s.IDEproject.service;

import com.project5s.IDEproject.controller.dto.DirectoryReqDto;
import com.project5s.IDEproject.controller.dto.ProjectByUserGetResDto;
import com.project5s.IDEproject.controller.dto.ProjectGetReqDto;
import com.project5s.IDEproject.controller.dto.ProjectSaveReqDto;
import com.project5s.IDEproject.domain.Directory;
import com.project5s.IDEproject.domain.Project;
import com.project5s.IDEproject.exception.AppException;
import com.project5s.IDEproject.exception.ErrorCode;
import com.project5s.IDEproject.repository.DirectoryRepository;
import com.project5s.IDEproject.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final DirectoryRepository directoryRepository;

    public Project getProject(String email, ProjectGetReqDto dto) {
        return projectRepository.findProjectByEmailAndName(email, dto.projectName())
                .orElseThrow(() -> new AppException(ErrorCode.PROJECT_NOT_FOUND_BY_EMAIL, ErrorCode.PROJECT_NOT_FOUND_BY_EMAIL.getMessage()));
    }

    public List<ProjectByUserGetResDto> getProjects(String email) {
        return projectRepository.findProjectsByEmail(email)
                .stream()
                .map(Project::ofResponse)
                .collect(Collectors.toList());
    }

    public void save(String email, ProjectSaveReqDto dto) {
        if(projectRepository.existsProjectByEmailAndName(email, dto.name())) {
            throw new AppException(ErrorCode.PROJECT_NAME_DUPLICATED, ErrorCode.PROJECT_NAME_DUPLICATED.getMessage());
        }
        Project project = new Project(email, dto);
        projectRepository.save(project);
    }

    public void saveDirectory(String email, String projectId, DirectoryReqDto dto) {
        Project project = projectRepository.findProjectByEmailAndId(email, projectId)
                .orElseThrow(() -> new AppException(ErrorCode.PROJECT_NOT_FOUND_BY_EMAIL, ErrorCode.PROJECT_NOT_FOUND_BY_EMAIL.getMessage()));
        Directory directory = new Directory(dto);
        directoryRepository.save(directory);
        project.saveDirectory(directory);
        projectRepository.save(project);
    }

    public void saveDirectoryInDirectory(String email, String projectId, String directoryId, DirectoryReqDto dto) {
        if(!projectRepository.existsProjectByEmailAndId(email, projectId)) {
            throw new AppException(ErrorCode.PROJECT_NOT_FOUND_BY_EMAIL, ErrorCode.PROJECT_NOT_FOUND.getMessage());
        }
        Directory targetDirectory = directoryRepository.findById(directoryId)
                .orElseThrow(() -> new AppException(ErrorCode.DIRECTORY_NOT_FOUND, ErrorCode.DIRECTORY_NOT_FOUND.getMessage()));

        Directory directory = new Directory(dto);
        directoryRepository.save(directory);
        targetDirectory.getDirectories().add(directory);
        directoryRepository.save(targetDirectory);
    }

    public void deleteDirectory(String email, String projectId, String directoryId) {
        if(!projectRepository.existsProjectByEmailAndId(email, projectId)) {
            throw new AppException(ErrorCode.PROJECT_NOT_FOUND_BY_EMAIL, ErrorCode.PROJECT_NOT_FOUND.getMessage());
        }
        directoryRepository.deleteById(directoryId);
    }

    public void deleteProject(String email, String projectId) {
        Project project = projectRepository.findProjectByEmailAndId(email, projectId)
                .orElseThrow(() -> new AppException(ErrorCode.PROJECT_NOT_FOUND_BY_EMAIL, ErrorCode.PROJECT_NOT_FOUND.getMessage()));
        projectRepository.delete(project);
    }

    public void updateDirectory(String email, String projectId, String directoryId, DirectoryReqDto dto) {
        if(!projectRepository.existsProjectByEmailAndId(email, projectId)) {
            throw new AppException(ErrorCode.PROJECT_NOT_FOUND_BY_EMAIL, ErrorCode.PROJECT_NOT_FOUND.getMessage());
        }
        Directory targetDirectory = directoryRepository.findById(directoryId)
                .orElseThrow(() -> new AppException(ErrorCode.DIRECTORY_NOT_FOUND, ErrorCode.DIRECTORY_NOT_FOUND.getMessage()));
        targetDirectory.update(dto);
        directoryRepository.save(targetDirectory);
    }
}
