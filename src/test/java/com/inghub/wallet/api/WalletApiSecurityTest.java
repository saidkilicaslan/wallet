package com.inghub.wallet.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inghub.wallet.entity.Currency;
import com.inghub.wallet.entity.Customer;
import com.inghub.wallet.model.WalletCreate;
import com.inghub.wallet.repository.CustomerRepository;
import com.inghub.wallet.repository.WalletRepository;
import com.inghub.wallet.util.TestJwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WalletApiSecurityTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private String baseUrl;

    @BeforeEach
    void setUp() {
        walletRepository.deleteAll();
        customerRepository.deleteAll();

        baseUrl = "http://localhost:" + port + "/api/v1/wallets";

        Customer customer = new Customer();
        customer.setTckn("12345678901");
        customer.setName("John");
        customer.setSurname("Doe");
        customerRepository.save(customer);
    }

    @Test
    void givenValidRequest_whenInvalidUserCreateWallet_thenReturnsForbidden() {
        // given
        WalletCreate request = new WalletCreate();
        request.setWalletName("My Wallet");
        request.setTckn("12345678901");
        request.setCurrency(Currency.TRY);
        request.setActiveForWithdraw(true);
        request.setActiveForShopping(true);


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(TestJwtUtil.generateCustomerToken("12345678901")); // JWT üretim yöntemi sana bağlı

        HttpEntity<WalletCreate> entity = new HttpEntity<>(request, headers);

        // when
        ResponseEntity<String> response = restTemplate.postForEntity(baseUrl, entity, String.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }


}