package com.asan.wallet;

import com.asan.wallet.exceptionhandler.exceptions.ServiceException;
import com.asan.wallet.models.dto.UserDetails;
import com.asan.wallet.models.entity.WalletEntity;
import com.asan.wallet.models.enums.TrackingStatus;
import com.asan.wallet.models.requestrespons.BalanceRequest;
import com.asan.wallet.models.requestrespons.WDResponse;
import com.asan.wallet.models.requestrespons.WalletRequest;
import com.asan.wallet.models.requestrespons.WithdrawDepositRequest;
import com.asan.wallet.services.JwtService;
import com.asan.wallet.services.TransactionService;
import com.asan.wallet.services.WalletService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    JwtService jwtService;

    @Autowired
    private TransactionService transactionService;

    @Value("${token.test}")
    private String token;


    String walletId;

    @Test
    void contextLoads() {
    }

    @BeforeAll
    public void createWallet() throws Exception {

        WalletEntity wallet = walletService.createWallet(UserDetails.builder().sub("Asanpardakht").build());
        walletId =wallet.getId();

    }

    @Test
    public void getWallet() throws Exception {

        List<WalletEntity> wallets = walletService.getWalletsByUsername("Asanpardakht");
        Assertions.assertThat(wallets).isNotNull();
        Assertions.assertThat(wallets.get(0).getUserName()).isEqualTo("Asanpardakht");

    }
    @Test
    public void depositCallFromWalletService() throws ServiceException {

        List<WalletEntity> wallets = walletService.getWalletsByUsername("Asanpardakht");
        walletService.deposit(new WithdrawDepositRequest(walletId ,"trackId", 525L), jwtService.getAllClaimsFromToken(token));
        WalletEntity walletAfterDeposit = walletService.getWalletsByUsername("Asanpardakht").get(0);
        Assertions.assertThat(walletAfterDeposit.getBalance()).isEqualTo(wallets.get(0).getBalance() + 525L);
    }

    @Test
    public void walletDeposit() throws ServiceException {

        WDResponse wdResponse = walletService.deposit(new WithdrawDepositRequest(walletId, generateID(), 500L),jwtService.getAllClaimsFromToken(token));
        Assertions.assertThat(wdResponse).isNotNull();
        Assertions.assertThat(wdResponse.getStatus()).isEqualTo(TrackingStatus.SUCCESS);

    }

    @Test
    public void walletWithdraw() throws ServiceException {

        WDResponse wdResponse = walletService.withdraw(new WithdrawDepositRequest(walletId , generateID(), 500L),jwtService.getAllClaimsFromToken(token));
        Assertions.assertThat(wdResponse).isNotNull();
        Assertions.assertThat(wdResponse.getStatus()).isEqualTo(TrackingStatus.SUCCESS);

    }

    @Test
    public void walletAfterWithdraw() throws ServiceException {
        WalletEntity baseWallet = walletService.getWalletsByUsername("Asanpardakht").get(0);
        walletService.withdraw(new WithdrawDepositRequest(walletId , generateID(), 525L), jwtService.getAllClaimsFromToken(token));
        WalletEntity walletAfterDeposit = walletService.getWalletsByUsername("Asanpardakht").get(0);
        Assertions.assertThat(walletAfterDeposit.getBalance()).isEqualTo(baseWallet.getBalance() - 525L);
    }

    @Test
    public void getTransaction() throws ServiceException {

       TrackingStatus status = transactionService.getTransactionsStatus("trackId");
        Assertions.assertThat(status).isEqualTo(TrackingStatus.SUCCESS);
    }

//    @Test
    public void getExceptionTransaction()  {

        Assertions.assertThatExceptionOfType(ServiceException.class).isThrownBy(() -> {
            transactionService.getTransactionsStatus("trk");
        });

    }

    @Test
    public void testHappyDeposit() throws Exception {

        mvc.perform(MockMvcRequestBuilders
                        .post("/wallet/deposit").header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(WithdrawDepositRequest.builder()
                                .walletId(walletId)
                                .amount(500L)
                                .trackingId(generateID())
                                .build())))
                .andExpect(status().isAccepted());
    }


    @Test
    public void testHappyWithdraw() throws Exception {

        mvc.perform(MockMvcRequestBuilders
                        .post("/wallet/withdraw").header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(WithdrawDepositRequest.builder()
                                .walletId(walletId)
                                .amount(500L)
                                .trackingId(generateID())
                                .build())))
                .andExpect(status().isAccepted());

    }

    @Test
    public void testHappyBalance() throws Exception {

        mvc.perform(MockMvcRequestBuilders
                        .post("/wallet/balance").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(BalanceRequest.builder()
                                .walletId(walletId)
                                .build())))
                .andExpect(status().isOk());

    }



    private String generateID() {
        return UUID.randomUUID().toString();
    }

}
