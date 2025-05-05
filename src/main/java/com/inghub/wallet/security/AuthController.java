package com.inghub.wallet.security;

import com.inghub.wallet.entity.Customer;
import com.inghub.wallet.exception.ResultNotFoundException;
import com.inghub.wallet.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final JwtUtil jwtUtil;

    private final CustomerRepository customerRepository;

    private static final Map<String, String> EMPLOYEE_CREDENTIALS = Map.of(
            "admin", "password"
    );

    @PostMapping("/login/employee")
    public ResponseEntity<?> employeeLogin(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");

        if (EMPLOYEE_CREDENTIALS.containsKey(username) && EMPLOYEE_CREDENTIALS.get(username).equals(password)) {
            String token = jwtUtil.generateToken(0L, "EMPLOYEE");
            return ResponseEntity.ok(Map.of("token", token));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }



    @PostMapping("/login")
    public ResponseEntity<?> customerLogin(@RequestBody Map<String, String> request) {
        String tckn = request.get("tckn");
        String password = request.get("password");

        Customer customer = customerRepository.findByTckn(tckn).orElseThrow(() -> new ResultNotFoundException("Customer not found"));
        if (customer != null && customer.getPassword().equals(password)) {
            String token = jwtUtil.generateToken(customer.getId(), "CUSTOMER");
            return ResponseEntity.ok(Map.of("token", token));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}