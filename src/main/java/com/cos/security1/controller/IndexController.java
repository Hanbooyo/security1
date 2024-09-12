package com.cos.security1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // View 리턴
public class IndexController {

    //localhost:8080/
    //localhost:8080
    @GetMapping({"","/"})
    public String index() {
        // 머스태치 기본폴더 src/main/resources/
        // View Resolver 설정 : templates (prefix), .mustache (suffix)
        return "index";
    }

    @GetMapping("/user")
    public String user() {
        return "user";
    }

    @GetMapping("/admin")
    public String user() {
        return "admin";
    }
}
