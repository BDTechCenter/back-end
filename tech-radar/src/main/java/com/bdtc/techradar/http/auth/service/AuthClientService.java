package com.bdtc.techradar.http.auth.service;

import com.bdtc.techradar.dto.user.UserDto;
import com.bdtc.techradar.http.auth.client.AuthClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthClientService {

    @Autowired
    private AuthClient authClient;

    public UserDto getUser(String tokenJWT) {
        return authClient.getUserDetails(tokenJWT);
    }
}
