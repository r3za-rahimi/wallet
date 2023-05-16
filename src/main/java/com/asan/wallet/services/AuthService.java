package com.asan.wallet.services;


import com.asan.wallet.models.requestrespons.AuthResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "authentication",url = "${url.auth}")
public interface AuthService {

    @GetMapping(value = "/api/token/is-valid")
    AuthResponse isTokenValid(@RequestHeader("Authorization") String token);



}
