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

    /**
     * 有视图解析器
     *
     * @param model
     * @return
     */
    @RequestMapping("/hello")
    public String hello(Model model) {
        model.addAttribute("msg", "Hello SpringMVC Annotation!");
        return "hello";//会被视图解析器处理
    }

    /**
     * 有视图解析器
     *
     * @param
     * @return
     */
    @RequestMapping("/hello2")
    public String test4() {
        //重定向
        return "redirect:/hello";
    }


    /**
     * 无需视图解析器
     *
     * @return
     */
    @RequestMapping("/h/t1")
    public String test1() {
        //转发
        return "/hello";
    }

    /**
     * 无需视图解析器
     *
     * @return
     */
    @RequestMapping("/h/t2")
    public String test2() {
        //转发二
        return "forward:/hello";
    }

    /**
     * 无需视图解析器
     *
     * @return
     */
    @RequestMapping("/h/t3")
    public String test3() {
        //重定向
        return "redirect:/hello";
    }
}
