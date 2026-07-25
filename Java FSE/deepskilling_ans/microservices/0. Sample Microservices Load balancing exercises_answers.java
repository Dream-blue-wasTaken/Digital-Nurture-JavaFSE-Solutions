package com.microservices.loadbalancing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class LoadBalancingMicroservicesApplication {
    public static void main(String[] args) {
        SpringApplication.run(LoadBalancingMicroservicesApplication.class, args);
    }

    @Bean
    @LoadBalanced
    public WebClient.Builder loadBalancedWebClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    public GlobalFilter customGlobalFilter() {
        return (exchange, chain) -> {
            System.out.println("Global Request Filtered: " + exchange.getRequest().getPath());
            return chain.filter(exchange);
        };
    }
}

@RestController
class ServiceConsumerController {
    private final WebClient.Builder webClientBuilder;

    public ServiceConsumerController(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @GetMapping("/consume")
    public Mono<String> callService() {
        return webClientBuilder.build()
                .get()
                .uri("http://ACCOUNT-SERVICE/api/accounts")
                .retrieve()
                .bodyToMono(String.class);
    }
}
