package com.bdtc.technews.service.news.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class ImageHandler {

    @Value("${upload.dir}")
    private String uploadDir;

    @Value("${gateway.url}")
    private String gatewayUrl;

    public String saveImageToUploadDir(MultipartFile image) {
        String fileName = null;
        if(image != null && !image.isEmpty()) {
            try {
                fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
                Path filePath = Paths.get(uploadDir, fileName);
                Files.write(filePath, image.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return String.format("%s/images/%s", gatewayUrl, fileName);
    }
}
