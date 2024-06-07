package com.bdtc.technews.service.image.service;

import com.bdtc.technews.dto.image.ImageUrlDto;
import com.bdtc.technews.service.image.client.ImageClient;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {
    @Autowired
    private ImageClient imageClient;

    public ImageUrlDto uploadImage(MultipartFile file) {
        return imageClient.uploadImage(file);
    }
}
