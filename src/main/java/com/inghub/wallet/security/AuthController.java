package com.inghub.wallet.security;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

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

    // Customer login örneği (customer veritabanından alınır)
    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping("/login")
    public ResponseEntity<?> customerLogin(@RequestBody Map<String, String> request) {
        String tckn = request.get("tckn");
        String password = request.get("password");

        Customer customer = customerRepository.findByTckn(tckn);
        if (customer != null && customer.getPassword().equals(password)) {
            String token = jwtUtil.generateToken(customer.getId(), "CUSTOMER");
            return ResponseEntity.ok(Map.of("token", token));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}