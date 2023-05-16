package com.asan.wallet;

import com.asan.wallet.exceptionhandler.exceptions.ServiceException;
import com.asan.wallet.models.entity.WalletEntity;
import com.asan.wallet.models.enums.TrackingStatus;
import com.asan.wallet.models.requestrespons.CreateWalletRequest;
import com.asan.wallet.models.requestrespons.WDResponse;
import com.asan.wallet.models.requestrespons.WithdrawDepositRequest;
import com.asan.wallet.repositories.TransactionRepository;
import com.asan.wallet.services.TransactionService;
import com.asan.wallet.services.WalletService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.AbstractThrowableAssert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
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
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionService transactionService;

    @Value("${token.test}")
    private String token;

    @Test
    void contextLoads() {
    }

    @BeforeAll
    public void createWallet() throws Exception {

        WalletEntity wallet = walletService.createWallet("Asanpardakht");

    }

    @Test
    public void getWallet() throws Exception {

        WalletEntity wallet = walletService.getWalletByName("Asanpardakht");
        Assertions.assertThat(wallet).isNotNull();
        Assertions.assertThat(wallet.getUserName()).isEqualTo("Asanpardakht");

    }

    @Test
    public void depositCallFromWalletService() throws ServiceException {

        WalletEntity baseWallet = walletService.getWalletByName("Asanpardakht");
        walletService.deposit(new WithdrawDepositRequest("trackId", 525L), token);
        WalletEntity walletAfterDeposit = walletService.getWalletByName("Asanpardakht");
        Assertions.assertThat(walletAfterDeposit.getBalance()).isEqualTo(baseWallet.getBalance() + 525L);
    }

    @Test
    public void walletDeposit() throws ServiceException {

        WDResponse wdResponse = walletService.deposit(new WithdrawDepositRequest( generateID(), 500L),token);
        Assertions.assertThat(wdResponse).isNotNull();
        Assertions.assertThat(wdResponse.getStatus()).isEqualTo(TrackingStatus.SUCCESS);

    }

    @Test
    public void walletWithdraw() throws ServiceException {
        WDResponse wdResponse = walletService.withdraw(new WithdrawDepositRequest( generateID(), 500L),token);
        Assertions.assertThat(wdResponse).isNotNull();
        Assertions.assertThat(wdResponse.getStatus()).isEqualTo(TrackingStatus.SUCCESS);

    }

    @Test
    public void walletAfterWithdraw() throws ServiceException {
        WalletEntity baseWallet = walletService.getWalletByName("Asanpardakht");
        walletService.withdraw(new WithdrawDepositRequest(generateID(), 525L), token);
        WalletEntity walletAfterDeposit = walletService.getWalletByName("Asanpardakht");
        Assertions.assertThat(walletAfterDeposit.getBalance()).isEqualTo(baseWallet.getBalance() - 525L);
    }

    @Test
    public void getTransaction() throws ServiceException {

       TrackingStatus status = transactionService.getTransactionsStatus("trackId");
        Assertions.assertThat(status).isEqualTo(TrackingStatus.SUCCESS);
    }

//    @Test
//    public void getExceptionTransaction() throws ServiceException {
//
//
//           Assertions.assertThatThrownBy(() -> transactionService.getTransactionsStatus("trk"));
//
//         Assertions.assertThat().isEqualTo("Transaction_not_found");
//
//    }


    private String generateID() {
        return UUID.randomUUID().toString();
    }

}
