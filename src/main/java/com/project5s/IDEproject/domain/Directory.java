package com.project5s.IDEproject.domain;

import com.project5s.IDEproject.controller.dto.DirectorySaveReqDto;
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
@Document(collection = "directories")
public class Directory {

    @Id
    private String id;

    private String name;

    @DBRef
    private List<Directory> directories = new ArrayList<>();

    @DBRef
    private List<Code> codes = new ArrayList<>();

    public Directory(DirectorySaveReqDto dto) {
        this.name = dto.name();
    }

    public void saveCode(Code code) {
        this.codes.add(code);
    }

    public void update(DirectorySaveReqDto dto) {
        this.name = dto.name();
    }
}
