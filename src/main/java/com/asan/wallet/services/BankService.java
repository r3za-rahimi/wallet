package com.asan.wallet.services;

import com.asan.wallet.models.requestrespons.PaymentRequest;
import com.asan.wallet.models.requestrespons.Request;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "bank",url = "${url.auth}")
public interface BankService {

    @PostMapping(value = "/bank/transaction/payment")
     ResponseEntity<?> doTransaction(@RequestBody PaymentRequest request);

}
