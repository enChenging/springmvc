package com.release.controller;

import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * 文件上传
 *
 * @author yancheng
 * @since 2022/7/5
 */
@RestController
public class FileController {

    /**
     * 文件上传
     * @param file
     * @param request
     * @return
     * @RequestParam("file") 将name=file控件得到的文件封装成CommonsMultipartFile对象
     * 批量上传CommonsMultipartFile则为数组即可
     */
    @RequestMapping("/upload")
    public String fileUpload(@RequestParam("file") CommonsMultipartFile file, HttpServletRequest request) throws IOException {

        //获取文件名
        String uploadFileName = file.getOriginalFilename();

        if (ObjectUtils.isEmpty(uploadFileName)) {
            return "redirect:/index.jsp";
        }

        System.out.println("上传文件名：" + uploadFileName);

        //上传路径保存位置
        String path = request.getServletContext().getRealPath("/upload");

        File realPath = new File(path);
        if (!realPath.exists()) {
            realPath.mkdir();
        }

        System.out.println("上传文件保存地址：" + realPath);

        //文件输入流
        InputStream is = file.getInputStream();
        //文件输出流
        FileOutputStream os = new FileOutputStream(new File(realPath, uploadFileName));

        //读取写出
        int len = 0;
        byte[] buffer = new byte[1024];
        while ((len = is.read(buffer)) != -1) {
            os.write(buffer, 0, len);
            os.flush();
        }

        os.close();
        is.close();

        return "ok";

    }

    /**
     * 文件上传 方式二
     * @param file
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping("/upload2")
    public String fileUpload2(@RequestParam("file") CommonsMultipartFile file, HttpServletRequest request) throws IOException {

        //上传路径保存位置
        String path = request.getServletContext().getRealPath("/upload");
        File realPath = new File(path);
        if (!realPath.exists()) {
            realPath.mkdir();
        }

        System.out.println("上传文件保存地址：" + realPath);

        //通过CommonsMultipartFile的方法直接写文件
        file.transferTo(new File(realPath + "/" + file.getOriginalFilename()));

        return "ok";
    }

    /**
     * 文件下载
     * @param response
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping("/download")
    public String download(HttpServletResponse response, HttpServletRequest request) throws IOException {

        //要下载的图片地址
        String path = request.getServletContext().getRealPath("/upload");
        String fileName = "a.png";

        //1.设置response 响应头
        response.reset();//设置页面不缓存，清空buffer
        response.setCharacterEncoding("UTF-8");
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition",
                "attachment;fileName=" + URLEncoder.encode(fileName, "UTF-8"));

        File file = new File(path, fileName);
        //2.读取文件----输入流
        FileInputStream inputStream = new FileInputStream(file);
        //3.写出文件----输出流
        ServletOutputStream outputStream = response.getOutputStream();

        //读取写出
        int len = 0;
        byte[] buffer = new byte[1024];
        while ((len = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, len);
            outputStream.flush();
        }

        outputStream.close();
        inputStream.close();

        return "ok";
    }
}
