package com.asan.wallet;

import com.asan.wallet.models.entity.WalletEntity;
import com.asan.wallet.models.requestrespons.CreateWalletRequest;
import com.asan.wallet.services.TransactionService;
import com.asan.wallet.services.WalletService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
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

    @BeforeAll
    public void createWallet() throws Exception {

        WalletEntity wallet = walletService.createWallet(new CreateWalletRequest(1000L, "john"));

        id = wallet.getId();

    }

    @Test
    public void getWallet() throws Exception {

        WalletEntity wallet = walletService.getWallet(id);
        Assertions.assertThat(wallet).isNotNull();
    }

//    @Test
//    public void depositCallFromWalletService() throws ServiceException {
//
//        WalletEntity baseWallet = walletService.getWallet(id);
//        walletService.deposit(new WithdrawDepositRequest(id, "trackId", 500L, new Date(), new Date()));
//        WalletEntity walletAfterDeposit = walletService.getWallet(id);
//
//        Assertions.assertThat(walletAfterDeposit.getBalance()).isEqualTo(baseWallet.getBalance() + 500L);
//    }


//    @Test
//    public void walletDeposit() throws ServiceException {
//
//        WDResponse WDResponse = walletService.deposit(new WithdrawDepositRequest(id, generateID(), 500L, null, null));
//        Assertions.assertThat(WDResponse).isNotNull();
//
//    }
//
//    @Test
//    public void walletWithdraw() throws ServiceException {
//        WDResponse WDResponse = walletService.deposit(new Request(id, generateID(), 500L, null, null));
//        Assertions.assertThat(WDResponse).isNotNull();
//
//    }
//
//    @Test
//    public void getTransaction() {
//
//        List<TransactionEntity> trx = transactionService.getTransactions(id);
//        Assertions.assertThat(trx).isNotNull();
//    }


    private String generateID() {
        return UUID.randomUUID().toString();
    }

}
