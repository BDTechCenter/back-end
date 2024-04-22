package com.bdtc.technews.http.auth.service;

import com.bdtc.technews.dto.user.UserDto;
import com.bdtc.technews.http.auth.client.AuthClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthClientService {

    @Autowired
    private AuthClient authClient;

    public UserDto getUser(String tokenJWT) {
        UserDto userDetails = authClient.getUserDetails(tokenJWT);
        return userDetails;
    }
}
