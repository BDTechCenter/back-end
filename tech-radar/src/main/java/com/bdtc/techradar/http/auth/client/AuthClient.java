package com.bdtc.techradar.http.auth.client;

import com.bdtc.techradar.dto.user.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("auth-service")
public interface AuthClient {

    @RequestMapping(method = RequestMethod.GET, value = "/auth")
    UserDto getUserDetails(@RequestHeader("Authorization") String tokenJWT);
}
