package com.bdtc.technews.service.image.client;

import com.bdtc.technews.dto.image.ImageUrlDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

@FeignClient("image-service")
public interface ImageClient {

    @PostMapping(value = "/images/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ImageUrlDto uploadImage(MultipartFile multipartFile);
}
