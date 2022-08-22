package com.take_out.demo.controller;

import com.itheima.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * @author YAO
 * @create 2022-08-21 14:17
 * 处理文件上传下载
 */
@RestController
@Slf4j
@RequestMapping("/common")
public class CommonController {

    @Value("${reggie.path}")
    private String reggiePath;

    @PostMapping("/upload")
    public R<String> upload(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
//        后缀
        String substring = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileName = UUID.randomUUID().toString() + substring;
        File dir = new File(reggiePath);
        if(!dir.exists()){
            dir.mkdir();
        }
        file.transferTo(new File(reggiePath+ fileName));
        return R.success(fileName);
    }

    /**
     * 处理文件下载
     */
    @PostMapping("/download")
    public void download(String name, HttpServletResponse response) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(new File(reggiePath + name));
        ServletOutputStream outputStream = response.getOutputStream();
        response.setContentType("image/jpeg");
        int len = 0;
        byte[] bytes = new byte[1024];
        while ((len = fileInputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, len);
            outputStream.flush();
        }
        fileInputStream.close();
        outputStream.close();
    }
}

