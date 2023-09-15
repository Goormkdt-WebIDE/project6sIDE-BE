package com.project5s.IDEproject.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeployTestController {
    @GetMapping("/deployTest")
    public String deployTest(){
        return "Success deploy";
    }
}
