import java.util.HashMap;
import java.util.Map;

/**
 * 2. Microservices with API gateway_answers
 * 
 * Architecture & Implementation Answers for Banking Enterprise Microservices Architecture:
 * - Monolithic vs Microservices Decoupling
 * - Core Microservices: Account Service, Customer Service, Transaction Service, Loan Service
 * - Spring Cloud Gateway / Zuul Routing Configuration & Eureka Discovery
 * - Cross-cutting Concerns: Centralized Logging, JWT Auth, Circuit Breaker Rate Limiting
 */
public class Microservices_with_API_gateway_answers {

    // Account Service Representation
    public static class AccountService {
        private Map<String, Double> accounts = new HashMap<>();

        public AccountService() {
            accounts.put("ACC1001", 5430.50);
            accounts.put("ACC1002", 12890.00);
        }

        public String getAccountBalance(String accountNumber) {
            Double balance = accounts.get(accountNumber);
            if (balance != null) {
                return "Account [" + accountNumber + "] Current Balance: $" + balance;
            }
            return "Account [" + accountNumber + "] Not Found.";
        }
    }

    // API Gateway Router Component
    public static class BankingApiGateway {
        private AccountService accountService = new AccountService();

        public String routeRequest(String path, String params) {
            System.out.println("[API Gateway] Incoming Request -> Path: " + path + " | Query: " + params);
            if (path.startsWith("/api/accounts")) {
                return accountService.getAccountBalance(params);
            } else if (path.startsWith("/api/loans")) {
                return "Routing to Loan Service...";
            }
            return "404 Route Not Found on API Gateway";
        }
    }

    public static void main(String[] args) {
        System.out.println("=== 2. Microservices with API Gateway Exercises Answers ===");
        BankingApiGateway gateway = new BankingApiGateway();

        String res1 = gateway.routeRequest("/api/accounts/balance", "ACC1001");
        System.out.println("[Gateway Route Response]: " + res1);

        String res2 = gateway.routeRequest("/api/loans/apply", "user=101");
        System.out.println("[Gateway Route Response]: " + res2);
    }
}
