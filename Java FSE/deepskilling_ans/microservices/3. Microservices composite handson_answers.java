package com.microservices.composite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class CompositeMicroserviceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CompositeMicroserviceApplication.class, args);
    }
}

class CustomerOverviewDTO {
    private String customerId;
    private String name;
    private Double accountBalance;
    private Double totalLoanAmount;

    public CustomerOverviewDTO(String customerId, String name, Double accountBalance, Double totalLoanAmount) {
        this.customerId = customerId;
        this.name = name;
        this.accountBalance = accountBalance;
        this.totalLoanAmount = totalLoanAmount;
    }

    public String getCustomerId() { return customerId; }
    public String getName() { return name; }
    public Double getAccountBalance() { return accountBalance; }
    public Double getTotalLoanAmount() { return totalLoanAmount; }
}

@RestController
@RequestMapping("/api/composite/customer-summary")
class CompositeCustomerController {

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerOverviewDTO> getCustomerSummary(@PathVariable String customerId) {
        String name = "Customer " + customerId;
        Double balance = 15000.50;
        Double loanAmount = 250000.00;

        CustomerOverviewDTO overview = new CustomerOverviewDTO(customerId, name, balance, loanAmount);
        return ResponseEntity.ok(overview);
    }
}
