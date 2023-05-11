package com.asan.wallet;

import com.asan.wallet.exceptionhandler.exceptions.ServiceException;
import com.asan.wallet.models.TransactionEntity;
import com.asan.wallet.models.WalletEntity;
import com.asan.wallet.models.dto.Request;
import com.asan.wallet.models.dto.Response;
import com.asan.wallet.services.TransactionService;
import com.asan.wallet.services.WalletService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
class WalletApplicationTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private WalletService walletService;

    @Autowired
    private TransactionService transactionService;

    String id;

    @Test
    void contextLoads() {
    }

    @BeforeEach
    public void createWallet() throws Exception {

        WalletEntity wallet = walletService.insert(new WalletEntity(1000L, "john", new ArrayList<>()));

        id = wallet.getId();

    }

    @Test
    public void getWallet() throws Exception {

        WalletEntity wallet = walletService.getWallet(id);
        Assertions.assertThat(wallet).isNotNull();
    }

    @Test
    public void depositCallFromWalletService() throws ServiceException {

        WalletEntity baseWallet = walletService.getWallet(id);
        walletService.deposit(new Request(id, "trackId", 500L, new Date(), new Date()));
        WalletEntity walletAfterDeposit = walletService.getWallet(id);

        Assertions.assertThat(walletAfterDeposit.getBalance()).isEqualTo(baseWallet.getBalance() + 500L);
    }


    @Test
    public void walletDeposit() throws ServiceException {

        Response response = walletService.deposit(new Request(id, generateID(), 500L, null, null));
        Assertions.assertThat(response).isNotNull();

    }

    @Test
    public void walletWithdraw() throws ServiceException {
        Response response = walletService.deposit(new Request(id, generateID(), 500L, null, null));
        Assertions.assertThat(response).isNotNull();

    }

    @Test
    public void getTransaction() {


        List<TransactionEntity> trx = transactionService.getTransactions(id);
        Assertions.assertThat(trx).isNotNull();
    }


    private String generateID() {
        return UUID.randomUUID().toString();
    }

}
