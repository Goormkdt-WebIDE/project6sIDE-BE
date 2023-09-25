package com.project5s.IDEproject.service;

import com.project5s.IDEproject.controller.dto.CodeSaveReqDto;
import com.project5s.IDEproject.domain.Code;
import com.project5s.IDEproject.domain.Directory;
import com.project5s.IDEproject.repository.CodeRepository;
import com.project5s.IDEproject.repository.DirectoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CodeService {

    private final CodeRepository codeRepository;
    private final DirectoryRepository directoryRepository;

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
}
