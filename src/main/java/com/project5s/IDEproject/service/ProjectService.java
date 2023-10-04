package com.project5s.IDEproject.service;

import com.project5s.IDEproject.controller.dto.DirectorySaveReqDto;
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

    public Project getProject(ProjectGetReqDto dto) {
        return projectRepository.findProjectByEmailAndName(dto.email(), dto.projectName())
                .orElseThrow();
    }

    public List<ProjectByUserGetResDto> getProjects(String email) {
        return projectRepository.findProjectsByEmail(email)
                .stream()
                .map(Project::ofResponse)
                .collect(Collectors.toList());
    }

    public void save(ProjectSaveReqDto dto) {
        Project project = new Project(dto);
        projectRepository.save(project);
    }

    public void saveDirectory(String projectId, DirectorySaveReqDto dto) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new AppException(ErrorCode.PROJECT_NOT_FOUND, ""));
        Directory directory = new Directory(dto);
        directoryRepository.save(directory);
        project.saveDirectory(directory);
        projectRepository.save(project);
    }

    public void saveDirectoryInDirectory(String directoryId, DirectorySaveReqDto dto) {
        Directory targetDirectory = directoryRepository.findById(directoryId)
                .orElseThrow();

        Directory directory = new Directory(dto);
        directoryRepository.save(directory);
        targetDirectory.getDirectories().add(directory);
        directoryRepository.save(targetDirectory);
    }

    public void deleteDirectory(String directoryId) {
        directoryRepository.deleteById(directoryId);
    }

    public void deleteProject(String projectId) {
        projectRepository.deleteById(projectId);
    }

    public void updateDirectory(String directoryId, DirectorySaveReqDto dto) {
        Directory targetDirectory = directoryRepository.findById(directoryId)
                .orElseThrow();

        targetDirectory.update(dto);
        directoryRepository.save(targetDirectory);
    }
}
