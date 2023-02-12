package com.example.ruiji.controller;

import com.example.ruiji.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

@RequestMapping("common")
@RestController
@Slf4j
public class CommonController {

    @Value("${ruiji.images.path}")
    private String path;

    /**
     * 文件上传
     *
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public R<String> upload(MultipartFile file) {
        log.info("接收到文件{}", file.toString());
        File dir = new File(path);
        String fileName;
        if (!dir.exists()) {
            dir.mkdirs();
        }
        try {
            // 获取原始文件名，不建议这么做，因为可能会有重名文件造成文件覆盖
            // file.transferTo(new File(path + file.getOriginalFilename()));
            String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            //使用UUID生成新的文件名，防止文件名称重复造成文件覆盖
            fileName = UUID.randomUUID().toString() + suffix;
            file.transferTo(new File(path + fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return R.success(fileName);
    }

    /**
     * 文件下载
     *
     * @param name
     * @param response
     */
    @GetMapping("/download")
    public void download(String name, HttpServletResponse response) {
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(path + name));
            ServletOutputStream outputStream = response.getOutputStream();
            response.setContentType("image/jpeg");

            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = fileInputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
            }
            outputStream.flush();
            outputStream.close();
            fileInputStream.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
