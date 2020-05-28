package com.fishdemon.sbt.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TemplateController {

    /**
     * 页面路由，当使用GET请求访问/login接口，会自动跳转到`/templates/index.html`页面
     *
     * @return login登录页面路由地址
     */
    @GetMapping("/signin")
    public String login() {
        return "signin";
    }

    /**
     * 页面路由，当使用GET请求访问/login接口，会自动跳转到`/templates/signin.html`页面
     *
     * @return index首页面路由地址
     */
    @GetMapping("/index")
    public String index() {
        return "index";
    }

}
