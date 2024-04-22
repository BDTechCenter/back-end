package com.bdtc.techradar.http.auth.client;

import com.bdtc.techradar.dto.user.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient("auth-service")
public interface AuthClient {

    @GetMapping("/auth")
    UserDto getUserDetails(@RequestHeader("Authorization") String tokenJWT);
}
