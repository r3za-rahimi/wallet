package com.asan.wallet;


import com.asan.wallet.exceptionhandler.exceptions.ServiceException;
import com.asan.wallet.models.dto.UserDetails;
import com.asan.wallet.models.entity.WalletEntity;
import com.asan.wallet.models.enums.TrackingStatus;
import com.asan.wallet.models.requestrespons.WDResponse;
import com.asan.wallet.models.requestrespons.WithdrawDepositRequest;
import com.asan.wallet.services.JwtService;
import com.asan.wallet.services.WalletService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;

import static org.mockito.Mockito.when;


@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MockServiceTest {

    @Mock
    WalletService walletService;


    @Value("${token.test}")
    private String token;

    public MockServiceTest() throws ServiceException {
    }

    @BeforeAll
    public void setup() throws Exception {

        WalletEntity wallet = WalletEntity.builder().userName("AsanPardakht").build();

        Mockito.when(walletService.createWallet(Mockito.any())).thenReturn(wallet);
//        when(walletService.createWallet("MAr")).thenReturn(new WalletEntity(500L , "MAr" , new ArrayList<>()));
//        when(walletService.deposit(new WithdrawDepositRequest("trk" , 10L) ,new UserDetails())).thenReturn(new WDResponse(TrackingStatus.SUCCESS));
//        when(walletService.withdraw(new WithdrawDepositRequest("trk" , 10L) ,new UserDetails())).thenReturn(new WDResponse(TrackingStatus.SUCCESS));
    }

    @Test
    public void insertWallet() throws Exception {

        WalletEntity walletIns = walletService.createWallet(UserDetails.builder().sub("AsanPardakht").build());
        Assertions.assertThat(walletIns.getUserName()).isEqualTo("AsanPardakht");




    }
//
//    @Test
//    public void walletDeposit() throws Exception {
//
//        WDResponse response = walletService.deposit(new WithdrawDepositRequest("trk" , 10L) , new UserDetails());
//        Assertions.assertThat(response.getStatus()).isEqualTo(TrackingStatus.SUCCESS);
//
//    }
//
//    @Test
//    public void walletWithdraw() throws Exception {
//
//        WDResponse response = walletService.withdraw(new WithdrawDepositRequest("trk" , 10L) , new UserDetails());
//        Assertions.assertThat(response.getStatus()).isEqualTo(TrackingStatus.SUCCESS);
//
//    }

}
