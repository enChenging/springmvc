package com.release.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author yancheng
 * @since 2022/7/5
 */
@Controller
public class HelloController {

    @RequestMapping("/h/t1")
    public String hello(String name, Model model) {
        model.addAttribute("msg", name);
        return "test";
    }
}
