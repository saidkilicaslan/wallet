package com.inghub.wallet.security;

import com.inghub.wallet.entity.Customer;
import com.inghub.wallet.exception.ResultNotFoundException;
import com.inghub.wallet.repository.CustomerRepository;
import lombok.AllArgsConstructor;
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
    private static final String SECRET_HEADER = "password";
    private static final Map<String, String> EMPLOYEE_CREDENTIALS = Map.of(
            "admin", "admin"
    );

    @PostMapping("/login/employee")
    public ResponseEntity<?> employeeLogin(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get(SECRET_HEADER);

        if (EMPLOYEE_CREDENTIALS.containsKey(username) && EMPLOYEE_CREDENTIALS.get(username).equals(password)) {
            return ResponseEntity.ok(Map.of("token", jwtUtil.generateToken(0L, "EMPLOYEE")));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }



    @PostMapping("/login")
    public ResponseEntity<?> customerLogin(@RequestBody Map<String, String> request) {
        Customer customer = customerRepository.findByTckn(request.get("tckn")).orElseThrow(() -> new ResultNotFoundException("Customer not found"));
        if (customer != null && customer.getPassword().equals(request.get(SECRET_HEADER))) {
            return ResponseEntity.ok(Map.of("token", jwtUtil.generateToken(customer.getId(), "CUSTOMER")));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}