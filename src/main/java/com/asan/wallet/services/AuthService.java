package com.asan.wallet.services;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "auth",url = "${url.auth}")
public interface AuthService {

    @RequestMapping(method = RequestMethod.POST, value = "/api/token/is-valid")
    Boolean isTokenValid(String request);



}
