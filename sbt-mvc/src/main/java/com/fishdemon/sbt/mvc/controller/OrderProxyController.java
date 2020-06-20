package com.fishdemon.sbt.mvc.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * spring mvc 中的转发， redirect 与 forward 用例
 * forward: 一次请求，一次响应，只是后台转发了个 controller 来处理而已，共享同一个 request
 * redirect: 两次请求，两次响应，第一次返回 302 ，由前端进行重定向转发
 */
@Slf4j
//@RestController
@Controller
@RequestMapping("proxy/order")
public class OrderProxyController {

    // 转发
    @GetMapping
    public String getOrder() {
        log.info("proxy: get order");
        // 这种写法，不可以使用 @RestController，否则会误以为是结果直接返回
        return "forward:/order";
    }

    // 重定向写法1
    @GetMapping("/redirect")
    public String redirectOrder() {
        // 这种写法，不可以使用 @RestController，否则会误以为是结果直接返回
        return  "redirect:/order";
    }

    // 重定向写法2
    @GetMapping("/redirect1")
    public ModelAndView redirectOrder1() {
        return new ModelAndView("redirect:/order");
    }

    // 重定向写法3
    @GetMapping("/redirect2")
    public void redirectOrder2(HttpServletResponse response) throws IOException {
        response.sendRedirect("/order");
    }

    // 重定向带请求参数
    @GetMapping("/redirect/param")
    public String redirectOrderWithParam(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        // 加入源请求的所有的参数
        redirectAttributes.addAttribute(request.getParameterMap());
        // 自定义新的参数
        redirectAttributes.addAttribute("name", "allen");
        // 重定向后，所有 RedirectAttributes 的内容将会以 url 参数的形式传递
        // /order?name=allen
        return  "redirect:/order";
        // 或者直接将参数写在 url 后面，不过参数值无法动态生成
        // return "redirect:/order?name=allen";
    }

}
