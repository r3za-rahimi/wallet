package com.asan.wallet.services;

import com.asan.wallet.models.requestrespons.Request;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "bank",url = "${url.auth}")
public interface BankService {

    @RequestMapping(method = RequestMethod.POST, value = "/bank/transaction/withdraw")
     ResponseEntity<?> doTransaction(@RequestBody Object request);

}
