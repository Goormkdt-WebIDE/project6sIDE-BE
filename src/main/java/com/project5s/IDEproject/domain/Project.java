package com.project5s.IDEproject.domain;

import com.project5s.IDEproject.controller.dto.ProjectSaveReqDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "projects")
public class Project {

    @Id
    private String id;

    private String name;

    private String email;

    @DBRef
    private List<Directory> directories = new ArrayList<>();

    @DBRef
    private List<Code> codes = new ArrayList<>();

    public Project(ProjectSaveReqDto dto) {
        this.name = dto.name();
        this.email = dto.email();
    }
    public void saveDirectory(Directory directory) {
        this.directories.add(directory);
    }

    public void saveCode(Code code) {
        this.codes.add(code);
    }
}
