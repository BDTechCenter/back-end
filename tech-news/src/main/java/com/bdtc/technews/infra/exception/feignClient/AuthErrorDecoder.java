package com.bdtc.technews.infra.exception.feignClient;

import com.bdtc.technews.infra.exception.validation.AuthClientInvalidTokenException;
import com.bdtc.technews.infra.exception.validation.BusinessRuleException;
import feign.Response;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class AuthErrorDecoder implements feign.codec.ErrorDecoder{

    @Override
    public Exception decode(String methodKey, Response response) {
        if(response.status() == 401) {
            throw new AuthClientInvalidTokenException();
        }
        return feign.FeignException.errorStatus(methodKey, response);
    }
}
