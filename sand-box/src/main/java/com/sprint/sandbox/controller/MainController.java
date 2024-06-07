package com.sprint.sandbox.controller;

import com.sprint.sandbox.NativeSandBox;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.Native;

@RestController("/")
public class MainController {
    @GetMapping("/test")
    public String test() {
        NativeSandBox sandBox=new NativeSandBox();
        return "hello";
    }
}
