import java.util.HashMap;
import java.util.Map;

/**
 * 3. Microservices composite handson_answers
 * 
 * Composite Microservices Pattern Hands-on:
 * - Enterprise Application Architecture for Banking & Telecom
 * - Decomposing Monolith into Composite Services (Account, Customer, Notification, Billing)
 * - Composite Aggregator Pattern implementation combining data from multiple downstream microservices
 */
public class Microservices_composite_handson_answers {

    // Downstream Microservice 1: Account Balance Service
    public static class AccountBalanceService {
        public double getBalance(String accountId) {
            return 8750.25; // Simulates database lookup
        }
    }

    // Downstream Microservice 2: Customer Profile Service
    public static class CustomerProfileService {
        public Map<String, String> getProfile(String customerId) {
            Map<String, String> profile = new HashMap<>();
            profile.put("name", "John Doe");
            profile.put("tier", "GOLD");
            profile.put("email", "john.doe@example.com");
            return profile;
        }
    }

    // Downstream Microservice 3: Loan Status Service
    public static class LoanStatusService {
        public String getActiveLoanStatus(String customerId) {
            return "Active Home Loan (EMI: $450/month)";
        }
    }

    // Composite Microservice Aggregator
    public static class CustomerDashboardCompositeService {
        private AccountBalanceService accountService = new AccountBalanceService();
        private CustomerProfileService profileService = new CustomerProfileService();
        private LoanStatusService loanService = new LoanStatusService();

        public Map<String, Object> getCustomerDashboard(String customerId, String accountId) {
            Map<String, Object> compositeResponse = new HashMap<>();
            compositeResponse.put("profile", profileService.getProfile(customerId));
            compositeResponse.put("balance", accountService.getBalance(accountId));
            compositeResponse.put("loans", loanService.getActiveLoanStatus(customerId));
            return compositeResponse;
        }
    }

    public static void main(String[] args) {
        System.out.println("=== 3. Microservices Composite Hands-on Exercises Answers ===");
        CustomerDashboardCompositeService compositeService = new CustomerDashboardCompositeService();
        Map<String, Object> dashboard = compositeService.getCustomerDashboard("CUST1001", "ACC2002");
        System.out.println("Aggregated Composite Response: " + dashboard);
    }
}
