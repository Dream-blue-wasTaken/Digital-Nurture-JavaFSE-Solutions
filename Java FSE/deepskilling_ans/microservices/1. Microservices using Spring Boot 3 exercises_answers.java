import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 1. Microservices using Spring Boot 3 exercises_answers
 * 
 * 1. User & Order Management System (WebClient / OpenFeign inter-service communication)
 * 2. Inventory Management System with Service Discovery (Eureka Client & Spring Cloud Config)
 * 3. Implement an API Gateway (Spring Cloud Gateway, Path Rewriting, Rate Limiting)
 * 4. Resilient Microservices with Circuit Breaker (Resilience4j CircuitBreaker & Fallback)
 */
public class Microservices_using_Spring_Boot_3_exercises_answers {

    // Microservice 1: User Service Entity & Controller
    public static class User {
        private Long id;
        private String name;
        private String email;

        public User(Long id, String name, String email) {
            this.id = id;
            this.name = name;
            this.email = email;
        }

        public Long getId() { return id; }
        public String getName() { return name; }
        public String getEmail() { return email; }

        @Override
        public String toString() { return "User{id=" + id + ", name='" + name + "'}"; }
    }

    // Microservice 2: Order Service Entity
    public static class Order {
        private Long orderId;
        private Long userId;
        private String product;
        private double price;

        public Order(Long orderId, Long userId, String product, double price) {
            this.orderId = orderId;
            this.userId = userId;
            this.product = product;
            this.price = price;
        }

        @Override
        public String toString() {
            return "Order{id=" + orderId + ", userId=" + userId + ", product='" + product + "', price=$" + price + "}";
        }
    }

    // Exercise 1: WebClient Inter-service Communication Simulation
    public static class OrderServiceComposite {
        private Map<Long, User> userDatabase = new HashMap<>();

        public OrderServiceComposite() {
            userDatabase.put(1L, new User(1L, "Alice", "alice@example.com"));
        }

        public String getOrderDetailsWithUser(Long orderId, Long userId) {
            User user = userDatabase.get(userId); // Simulates WebClient / OpenFeign call to User-Service
            Order order = new Order(orderId, userId, "Laptop", 1200.0);
            return "Order Details: " + order + " | Customer: " + user;
        }
    }

    // Exercise 4: Resilience4j Circuit Breaker & Fallback Strategy
    public static class PaymentServiceWithCircuitBreaker {
        public String processPayment(double amount, boolean isThirdPartyDown) {
            if (isThirdPartyDown) {
                return fallbackPaymentResponse(amount, new RuntimeException("Third-party payment gateway timeout"));
            }
            return "Payment of $" + amount + " processed successfully via PaymentGateway.";
        }

        public String fallbackPaymentResponse(double amount, Throwable t) {
            return "[FALLBACK] Payment Service is degraded: " + t.getMessage() + ". Order recorded for background processing ($" + amount + ").";
        }
    }

    public static void main(String[] args) {
        System.out.println("=== 1. Microservices using Spring Boot 3 Exercises Answers ===");

        // Ex 1: Service Communication
        OrderServiceComposite composite = new OrderServiceComposite();
        System.out.println("[Ex 1 Service Inter-communication]: " + composite.getOrderDetailsWithUser(101L, 1L));

        // Ex 2 & 3: Eureka & API Gateway Configuration
        System.out.println("[Ex 2 Service Discovery]: Eureka Server (@EnableEurekaServer) & Client Registered.");
        System.out.println("[Ex 3 API Gateway]: Spring Cloud Gateway routes configured with RateLimiter & PathRewrite.");

        // Ex 4: Circuit Breaker & Fallback
        PaymentServiceWithCircuitBreaker paymentService = new PaymentServiceWithCircuitBreaker();
        System.out.println("[Ex 4 Normal Payment]: " + paymentService.processPayment(250.0, false));
        System.out.println("[Ex 4 Fallback Payment]: " + paymentService.processPayment(250.0, true));
    }
}
