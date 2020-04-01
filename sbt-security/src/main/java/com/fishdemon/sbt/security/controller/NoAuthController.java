package com.fishdemon.sbt.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * no auth
 */
@RestController
@RequestMapping("noauth")
public class NoAuthController {

    @GetMapping
    public String getInfo() {
        return "no auth info";
    }

}
