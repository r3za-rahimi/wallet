package com.asan.wallet;

import com.asan.wallet.models.requestrespons.WithdrawDepositRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;


    @Value("${token.test}")
    private String jwtToken;


    @Test
    public void testHappyDeposit() throws Exception {

        mvc.perform(MockMvcRequestBuilders
                        .post("/wallet/deposit").header("Authorization", jwtToken)
                        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(WithdrawDepositRequest.builder()
                                .amount(500L)
                                .trackingId(generateID())
                                .build())))
                .andExpect(status().isAccepted());

    }


    @Test
    public void testHappyWithdraw() throws Exception {

        mvc.perform(MockMvcRequestBuilders
                        .post("/wallet/withdraw").header("Authorization", jwtToken)
                        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(WithdrawDepositRequest.builder()
                                .amount(500L)
                                .trackingId(generateID())
                                .build())))
                .andExpect(status().isAccepted());

    }

    @Test
    public void testHappyBalance() throws Exception {

        mvc.perform(MockMvcRequestBuilders
                        .post("/wallet/balance").header("Authorization", jwtToken))
                .andExpect(status().isAccepted());

    }




    private String generateID() {
        return UUID.randomUUID().toString();
    }

}
