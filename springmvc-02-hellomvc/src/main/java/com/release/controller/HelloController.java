package com.release.controller;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author yancheng
 * @since 2022/7/4
 */
public class HelloController implements Controller {
    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //ModelAndView 模型和视图
        ModelAndView mv = new ModelAndView();
        //封装对象，放在ModelAndView 中model
        mv.addObject("msg","HelloSpringMVC!");
        //封装要跳转视图，放在放在ModelAndView中
        mv.setViewName("hello");///WEB-INF/jsp/hello.jsp
        return mv;
    }
}
