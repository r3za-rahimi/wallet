package com.asan.wallet.services;

import com.asan.wallet.models.dto.Request;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "bank",url = "http://localhost:8081")
public interface FeignService {

//    @RequestMapping(method = RequestMethod.GET, value = "localhost:8083/api/token/is-valid")
//    Boolean isTokenValid(HttpServletRequest request);

    @RequestMapping(method = RequestMethod.POST, value = "/bank/transaction/withdraw")
     ResponseEntity<?> doTransaction(@RequestBody Request request);

}
