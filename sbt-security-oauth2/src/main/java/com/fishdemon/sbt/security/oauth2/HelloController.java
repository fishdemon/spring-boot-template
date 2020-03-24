package com.fishdemon.sbt.security.oauth2;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello, this is test interface";
    }

    @GetMapping("/info/{name}")
    public String info(@PathVariable String name) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return "hello " + name + ", this your info";
    }
}
