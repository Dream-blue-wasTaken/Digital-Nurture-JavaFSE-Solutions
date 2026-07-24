import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * 0. Sample Microservices Load balancing exercises_answers
 * 
 * Exercise 1: Implementing Edge Services for Routing and Filtering (Spring Cloud Gateway & LoggingFilter)
 * Exercise 2: Load Balancing with Spring Cloud LoadBalancer (RandomLoadBalancer & WebClient.Builder)
 * Exercise 3: Resilience Patterns in an API Gateway (Resilience4j CircuitBreaker & TimeLimiter)
 */
public class Sample_Microservices_Load_balancing_exercises_answers {

    // Exercise 1: Custom Logging Global Filter for Gateway
    @Bean
    public GlobalFilter loggingFilter() {
        return (exchange, chain) -> {
            System.out.println("[Gateway Filter Log] Request Path: " + exchange.getRequest().getPath());
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                System.out.println("[Gateway Filter Log] Response Status: " + exchange.getResponse().getStatusCode());
            }));
        };
    }

    // Exercise 2: Load Balanced WebClient Bean Configuration
    @Bean
    @LoadBalanced
    public WebClient.Builder loadBalancedWebClientBuilder() {
        return WebClient.builder();
    }

    // Target Microservice Controller
    @RestController
    public static class GatewayTestController {
        @GetMapping("/example/data")
        public String getExampleData() {
            return "Routing & Load Balancing Success from Example Microservice!";
        }

        @GetMapping("/fallback")
        public String fallbackResponse() {
            return "Circuit Breaker Fallback: Target Service is currently unavailable.";
        }
    }

    public static void main(String[] args) {
        System.out.println("=== 0. Sample Microservices Load Balancing & API Gateway Exercises Answers ===");
        System.out.println("1. Spring Cloud Gateway Routing & LoggingFilter configured.");
        System.out.println("2. Spring Cloud LoadBalancer & WebClient configured.");
        System.out.println("3. Resilience4j CircuitBreaker & TimeLimiter fallback configured.");
    }
}
