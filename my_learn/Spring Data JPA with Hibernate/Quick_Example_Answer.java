// ============================================================
// Spring Data JPA with Hibernate - Quick Example
// Difference between JPA, Hibernate, and Spring Data JPA
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
    <artifactId>spring-data-jpa-demo</artifactId>
    <version>1.0.0</version>
    <name>Spring Data JPA Demo</name>
    
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>com.h2database</groupId>
            <artifactId>h2</artifactId>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
    </dependencies>
</project>
*/

// ===== application.properties =====
/*
# Database Configuration
spring.datasource.url=jdbc:h2:mem:ormlearn
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# JPA/Hibernate Configuration
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# H2 Console
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# Logging
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE
*/

// ===== SQL Schema (data.sql) =====
/*
CREATE SCHEMA IF NOT EXISTS ormlearn;

CREATE TABLE ormlearn.country (
    co_code VARCHAR(2) PRIMARY KEY,
    co_name VARCHAR(128) NOT NULL
);

INSERT INTO ormlearn.country (co_code, co_name) VALUES ('IN', 'India');
INSERT INTO ormlearn.country (co_code, co_name) VALUES ('US', 'United States');
INSERT INTO ormlearn.country (co_code, co_name) VALUES ('UK', 'United Kingdom');
INSERT INTO ormlearn.country (co_code, co_name) VALUES ('JP', 'Japan');
INSERT INTO ormlearn.country (co_code, co_name) VALUES ('DE', 'Germany');
INSERT INTO ormlearn.country (co_code, co_name) VALUES ('FR', 'France');
INSERT INTO ormlearn.country (co_code, co_name) VALUES ('AU', 'Australia');
INSERT INTO ormlearn.country (co_code, co_name) VALUES ('CA', 'Canada');
INSERT INTO ormlearn.country (co_code, co_name) VALUES ('BR', 'Brazil');
INSERT INTO ormlearn.country (co_code, co_name) VALUES ('CN', 'China');
*/

// ===== Entity Class =====

// Country.java
package com.cognizant.ormlearn.model;

import jakarta.persistence.*;

@Entity
@Table(name = "country", schema = "ormlearn")
public class Country {
    
    @Id
    @Column(name = "co_code", length = 2)
    private String code;
    
    @Column(name = "co_name", length = 128)
    private String name;
    
    public Country() {}
    
    public Country(String code, String name) {
        this.code = code;
        this.name = name;
    }
    
    // Getters and Setters
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    @Override
    public String toString() {
        return "Country{code='" + code + "', name='" + name + "'}";
    }
}

// ===== Repository =====

// CountryRepository.java
package com.cognizant.ormlearn.repository;

import com.cognizant.ormlearn.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CountryRepository extends JpaRepository<Country, String> {
    
    // Derived query methods
    List<Country> findByNameContaining(String pattern);
    List<Country> findByNameStartingWith(String prefix);
    List<Country> findAllByOrderByNameAsc();
    List<Country> findByNameContainingIgnoreCase(String pattern);
}

// ===== Service =====

// CountryService.java
package com.cognizant.ormlearn.service;

import com.cognizant.ormlearn.model.Country;
import com.cognizant.ormlearn.repository.CountryRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CountryService {
    
    private final CountryRepository countryRepository;
    
    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }
    
    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }
    
    public Optional<Country> getCountryByCode(String code) {
        return countryRepository.findById(code);
    }
    
    public Country addCountry(Country country) {
        return countryRepository.save(country);
    }
    
    public Country updateCountry(String code, Country countryDetails) {
        Country country = countryRepository.findById(code)
            .orElseThrow(() -> new RuntimeException("Country not found with code: " + code));
        country.setName(countryDetails.getName());
        return countryRepository.save(country);
    }
    
    public void deleteCountry(String code) {
        countryRepository.deleteById(code);
    }
    
    public List<Country> searchCountriesByName(String pattern) {
        return countryRepository.findByNameContainingIgnoreCase(pattern);
    }
    
    public List<Country> getAllCountriesSorted() {
        return countryRepository.findAllByOrderByNameAsc();
    }
}

// ===== Main Application =====

// OrmLearnApplication.java
package com.cognizant.ormlearn;

import com.cognizant.ormlearn.model.Country;
import com.cognizant.ormlearn.service.CountryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.util.List;

@SpringBootApplication
public class OrmLearnApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(OrmLearnApplication.class, args);
    }
    
    @Bean
    public CommandLineRunner demo(CountryService countryService) {
        return args -> {
            System.out.println("=== Spring Data JPA Demo ===\n");
            
            // 1. Display all countries
            System.out.println("--- All Countries ---");
            countryService.getAllCountries().forEach(System.out::println);
            
            // 2. Find by code
            System.out.println("\n--- Find Country by Code 'IN' ---");
            countryService.getCountryByCode("IN")
                .ifPresentOrElse(
                    c -> System.out.println("Found: " + c),
                    () -> System.out.println("Country not found")
                );
            
            // 3. Search by name
            System.out.println("\n--- Search Countries containing 'land' ---");
            List<Country> results = countryService.searchCountriesByName("land");
            results.forEach(System.out::println);
            
            // 4. Add a new country
            System.out.println("\n--- Add New Country ---");
            Country newCountry = countryService.addCountry(new Country("SG", "Singapore"));
            System.out.println("Added: " + newCountry);
            
            // 5. Update a country
            System.out.println("\n--- Update Country ---");
            Country updated = countryService.updateCountry("SG", new Country("SG", "Republic of Singapore"));
            System.out.println("Updated: " + updated);
            
            System.out.println("\n=== Demo Completed ===");
        };
    }
}

// ============================================================
// Difference between JPA, Hibernate, and Spring Data JPA
// ============================================================

/*
DIFFERENCE BETWEEN JPA, HIBERNATE, AND SPRING DATA JPA

1. JPA (Java Persistence API)
   - WHAT: A specification/standard (not an implementation)
   - PURPOSE: Defines a standard way to map Java objects to relational database tables
   - TYPE: javax.persistence / jakarta.persistence API
   - SCOPE: Defines EntityManager, @Entity, @Table, @Id, JPQL
   - EXAMPLE:
     EntityManager em = ...
     Country c = em.find(Country.class, "IN");
     
2. Hibernate
   - WHAT: An ORM framework (implementation of JPA)
   - PURPOSE: Provides concrete implementation of JPA specification
   - TYPE: org.hibernate.*
   - FEATURES: Session, SessionFactory, HQL, caching, lazy loading
   - EXAMPLE:
     Session session = sessionFactory.openSession();
     Transaction tx = session.beginTransaction();
     Country c = session.get(Country.class, "IN");
     tx.commit();
     session.close();

3. Spring Data JPA
   - WHAT: A Spring framework module that simplifies JPA usage
   - PURPOSE: Reduces boilerplate code by providing repository abstraction
   - TYPE: org.springframework.data.jpa.repository
   - FEATURES: JpaRepository interface, derived query methods, 
               pagination, auditing, @Query annotation
   - EXAMPLE:
     public interface CountryRepository extends JpaRepository<Country, String> {
         List<Country> findByNameContaining(String pattern);
     }
     // No implementation needed! Spring generates it at runtime.

COMPARISON (using same task: find country by name):

Without Spring Data JPA (Plain Hibernate):
-------------------------------------------------------------------
String hql = "FROM Country WHERE co_name LIKE :pattern";
Session session = sessionFactory.openSession();
Query query = session.createQuery(hql);
query.setParameter("pattern", "%" + pattern + "%");
List<Country> results = query.list();
session.close();

With Spring Data JPA:
-------------------------------------------------------------------
@Repository
public interface CountryRepository extends JpaRepository<Country, String> {
    List<Country> findByNameContaining(String pattern);
}
// That's it! The implementation is automatic.

KEY ADVANTAGES OF SPRING DATA JPA:
1. Zero boilerplate code - no DAO implementations needed
2. Derived query methods - method name becomes query
3. @Query annotation for custom JPQL/SQL queries
4. Built-in pagination and sorting with Pageable
5. Automatic transaction management
6. Integration with Spring's dependency injection
7. Auditing support (@CreatedDate, @LastModifiedDate)
*/
