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

    private String text;

    public Code(CodeSaveReqDto dto) {
        this.text = dto.text();
    }
}
