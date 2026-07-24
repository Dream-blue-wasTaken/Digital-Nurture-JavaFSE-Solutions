// ============================================================
// Spring REST using Spring Boot
// Exercise 1: Create Spring Web Project + Load Country from XML
// Exercise 2: Hello World REST, Country REST Service, Get by Code
// Exercise 5: JWT Authentication Service
// ============================================================

// ===== pom.xml =====
/*
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
         http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.2.0</version>
    </parent>
    
    <groupId>com.cognizant</groupId>
    <artifactId>spring-learn</artifactId>
    <version>1.0.0</version>
    <name>Spring Learn</name>
    
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
        </dependency>
        <!-- JWT Dependencies -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>
        <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt-api</artifactId>
            <version>0.12.3</version>
        </dependency>
        <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt-impl</artifactId>
            <version>0.12.3</version>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt-jackson</artifactId>
            <version>0.12.3</version>
            <scope>runtime</scope>
        </dependency>
    </dependencies>
</project>
*/

// ===== application.properties =====
/*
server.port=8090

# Logging configuration (Exercise 1: Incorporate Logging)
logging.level.com.cognizant=DEBUG
logging.pattern.console=%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n
*/

// ============================================================
// Exercise 1: Country class (XML configuration)
// ============================================================

// Country.java
package com.cognizant.springlearn.model;

public class Country {
    private String code;
    private String name;
    
    public Country() {}
    
    public Country(String code, String name) {
        this.code = code;
        this.name = name;
    }
    
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}

// ===== country.xml =====
/*
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans 
       http://www.springframework.org/schema/beans/spring-beans.xsd">
    
    <!-- Singleton scope (default) -->
    <bean id="country" class="com.cognizant.springlearn.model.Country">
        <property name="code" value="IN"/>
        <property name="name" value="India"/>
    </bean>
    
    <!-- Prototype scope -->
    <bean id="countryPrototype" class="com.cognizant.springlearn.model.Country" scope="prototype">
        <property name="code" value="US"/>
        <property name="name" value="United States"/>
    </bean>
    
    <!-- List of countries -->
    <bean id="countryList" class="java.util.ArrayList">
        <constructor-arg>
            <list>
                <ref bean="country"/>
                <ref bean="countryPrototype"/>
            </list>
        </constructor-arg>
    </bean>
    
</beans>
*/

// ============================================================
// Exercise 2: Hello World REST Controller
// ============================================================

// HelloController.java
package com.cognizant.springlearn.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(HelloController.class);
    
    @GetMapping("/hello")
    public String sayHello() {
        LOGGER.debug("Hello endpoint accessed");
        return "Hello World! Welcome to Spring REST!";
    }
    
    @GetMapping("/hello/{name}")
    public String greetUser(@PathVariable String name) {
        LOGGER.debug("Greeting user: {}", name);
        return "Hello, " + name + "! Welcome to Spring REST!";
    }
}

// ============================================================
// Exercise 2: Country REST Service
// ============================================================

// CountryController.java
package com.cognizant.springlearn.controller;

import com.cognizant.springlearn.model.Country;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.PostConstruct;
import java.util.*;

@RestController
@RequestMapping("/countries")
public class CountryController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(CountryController.class);
    private final Map<String, Country> countryMap = new LinkedHashMap<>();
    
    @PostConstruct
    public void init() {
        // Initialize with sample data
        addCountry(new Country("IN", "India"));
        addCountry(new Country("US", "United States"));
        addCountry(new Country("UK", "United Kingdom"));
        addCountry(new Country("JP", "Japan"));
        addCountry(new Country("DE", "Germany"));
        addCountry(new Country("FR", "France"));
        addCountry(new Country("AU", "Australia"));
        addCountry(new Country("CA", "Canada"));
        LOGGER.debug("Countries initialized: {}", countryMap.size());
    }
    
    private void addCountry(Country country) {
        countryMap.put(country.getCode(), country);
    }
    
    // GET all countries
    @GetMapping
    public List<Country> getAllCountries() {
        LOGGER.debug("Fetching all countries");
        return new ArrayList<>(countryMap.values());
    }
    
    // GET country by code (Exercise 2: REST - Get country based on code)
    @GetMapping("/{code}")
    public Country getCountryByCode(@PathVariable String code) {
        LOGGER.debug("Fetching country with code: {}", code);
        Country country = countryMap.get(code.toUpperCase());
        if (country == null) {
            throw new RuntimeException("Country not found with code: " + code);
        }
        return country;
    }
    
    // POST - Add a new country
    @PostMapping
    public Country addCountry(@RequestBody Country country) {
        LOGGER.debug("Adding country: {}", country);
        countryMap.put(country.getCode().toUpperCase(), country);
        return country;
    }
    
    // PUT - Update a country
    @PutMapping("/{code}")
    public Country updateCountry(@PathVariable String code, @RequestBody Country country) {
        LOGGER.debug("Updating country with code: {}", code);
        if (!countryMap.containsKey(code.toUpperCase())) {
            throw new RuntimeException("Country not found with code: " + code);
        }
        country.setCode(code.toUpperCase());
        countryMap.put(code.toUpperCase(), country);
        return country;
    }
    
    // DELETE - Remove a country
    @DeleteMapping("/{code}")
    public String deleteCountry(@PathVariable String code) {
        LOGGER.debug("Deleting country with code: {}", code);
        if (countryMap.remove(code.toUpperCase()) != null) {
            return "Country deleted successfully";
        }
        throw new RuntimeException("Country not found with code: " + code);
    }
}

// ============================================================
// Exercise 5: JWT Authentication Service
// ============================================================

// SecurityConfig.java
package com.cognizant.springlearn.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        UserDetails admin = User.builder()
            .username("admin")
            .password(encoder.encode("pwd"))
            .roles("ADMIN")
            .build();
        
        UserDetails user = User.builder()
            .username("user")
            .password(encoder.encode("pwd"))
            .roles("USER")
            .build();
        
        return new InMemoryUserDetailsManager(admin, user);
    }
}

// JwtUtil.java
package com.cognizant.springlearn.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {
    
    private final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final long expirationMs = 3600000; // 1 hour
    
    public String generateToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationMs);
        
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(secretKey)
            .compact();
    }
    
    public String getUsernameFromToken(String token) {
        return Jwts.parser()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
    }
    
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}

// AuthenticationController.java
package com.cognizant.springlearn.controller;

import com.cognizant.springlearn.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import java.util.Base64;

@RestController
public class AuthenticationController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    
    public AuthenticationController(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }
    
    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestHeader("Authorization") String authHeader) {
        LOGGER.debug("Authentication request received");
        
        try {
            // Decode Basic Auth credentials
            String encodedCredentials = authHeader.substring("Basic ".length());
            String decodedCredentials = new String(Base64.getDecoder().decode(encodedCredentials));
            String[] parts = decodedCredentials.split(":");
            String username = parts[0];
            String password = parts[1];
            
            // Validate credentials
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (userDetails == null || !password.equals("pwd")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
            }
            
            // Generate JWT
            String token = jwtUtil.generateToken(username);
            LOGGER.debug("JWT generated for user: {}", username);
            
            return ResponseEntity.ok(new JwtResponse(token));
            
        } catch (Exception e) {
            LOGGER.error("Authentication failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
        }
    }
    
    // Response DTO
    static class JwtResponse {
        private String token;
        
        public JwtResponse(String token) {
            this.token = token;
        }
        
        public String getToken() { return token; }
    }
}

// JwtAuthorizationFilter.java
package com.cognizant.springlearn.filter;

import com.cognizant.springlearn.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    
    private final JwtUtil jwtUtil;
    
    public JwtAuthorizationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                   FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            
            if (jwtUtil.validateToken(token)) {
                String username = jwtUtil.getUsernameFromToken(token);
                UsernamePasswordAuthenticationToken auth = 
                    new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList());
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        
        filterChain.doFilter(request, response);
    }
}
