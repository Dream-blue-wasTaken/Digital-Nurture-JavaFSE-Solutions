package com.microservices.springboot3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableDiscoveryClient
public class SpringBoot3MicroservicesApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBoot3MicroservicesApplication.class, args);
    }
}

class UserOrderDTO {
    private String userId;
    private String userName;
    private List<String> orders;

    public UserOrderDTO(String userId, String userName, List<String> orders) {
        this.userId = userId;
        this.userName = userName;
        this.orders = orders;
    }

    public String getUserId() { return userId; }
    public String getUserName() { return userName; }
    public List<String> getOrders() { return orders; }
}

@RestController
@RequestMapping("/api/user-orders")
class UserOrderAggregatorController {

    private final WebClient webClient;

    public UserOrderAggregatorController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://ORDER-SERVICE").build();
    }

    @GetMapping("/{userId}")
    public Mono<UserOrderDTO> getUserOrders(@PathVariable String userId) {
        return webClient.get()
                .uri("/api/orders/user/" + userId)
                .retrieve()
                .bodyToFlux(String.class)
                .collectList()
                .map(orders -> new UserOrderDTO(userId, "John Doe", orders))
                .onErrorReturn(new UserOrderDTO(userId, "Fallback User", new ArrayList<>()));
    }
}
