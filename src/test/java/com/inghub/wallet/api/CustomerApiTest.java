package com.inghub.wallet.api;

import com.inghub.wallet.WalletApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = WalletApplication.class)
@ActiveProfiles("test")
class CustomerApiTest {


}
