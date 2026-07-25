package com.microservices.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayMicroservicesApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayMicroservicesApplication.class, args);
    }
}

@RestController
@RequestMapping("/api/accounts")
class AccountServiceController {

    private final Map<String, Double> accounts = new HashMap<>();

    public AccountServiceController() {
        accounts.put("ACC-101", 5000.0);
        accounts.put("ACC-102", 12000.0);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<Double> getBalance(@PathVariable String accountId) {
        Double balance = accounts.get(accountId);
        if (balance != null) {
            return ResponseEntity.ok(balance);
        }
        return ResponseEntity.notFound().build();
    }
}

@RestController
@RequestMapping("/api/customers")
class CustomerServiceController {

    private final Map<String, String> customers = new HashMap<>();

    public CustomerServiceController() {
        customers.put("CUST-1", "Alice Smith");
        customers.put("CUST-2", "Bob Johnson");
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getCustomerName(@PathVariable String id) {
        String name = customers.get(id);
        if (name != null) {
            return ResponseEntity.ok(name);
        }
        return ResponseEntity.notFound().build();
    }
}
