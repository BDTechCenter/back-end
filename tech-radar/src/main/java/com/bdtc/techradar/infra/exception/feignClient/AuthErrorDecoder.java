package com.bdtc.techradar.infra.exception.feignClient;

import com.bdtc.techradar.infra.exception.validation.AuthClientInvalidTokenException;
import feign.Response;
import org.springframework.stereotype.Component;

@Component
public class AuthErrorDecoder implements feign.codec.ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() == 401) throw new AuthClientInvalidTokenException();
        return feign.FeignException.errorStatus(methodKey, response);
    }
}