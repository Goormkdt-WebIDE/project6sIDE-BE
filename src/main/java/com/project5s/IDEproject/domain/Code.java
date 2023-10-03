package com.project5s.IDEproject.domain;

import com.project5s.IDEproject.controller.dto.CodeSaveReqDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "codes")
public class Code {

    @Id
    private String id;

    private String name;

    private String extension;

    private String text;

    public Code(CodeSaveReqDto dto) {
        if(!dto.name().contains(".")) {
            throw new IllegalArgumentException("확장자 정보가 없습니다.");
        }
        this.name = dto.name();
        this.extension = dto.name().split("\\.")[1];
        this.text = dto.text();
    }
}
