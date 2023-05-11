package com.asan.wallet.services;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("auth")
public interface AuthService {

    @RequestMapping(method = RequestMethod.GET, value = "localhost:8083/api/token/is-valid")
    Boolean isTokenValid(HttpServletRequest request);

}
