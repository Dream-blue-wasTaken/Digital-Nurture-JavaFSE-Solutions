// ============================================================
// SL4J Logging - Exercise 1: Logging Error Messages
// ============================================================

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Exercise 1: Logging Error Messages with SLF4J
 * 
 * Configure logging levels and patterns, use SLF4J Logger to 
 * log lifecycle events and debug information.
 */

// ===== Configuration (application.properties) =====
/*
# Logging configuration
logging.level.root=WARN
logging.level.com.cognizant=DEBUG
logging.pattern.console=%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n
logging.file.name=logs/application.log
logging.file.max-size=10MB
logging.file.max-history=7
*/

// ===== pom.xml Dependencies =====
/*
<!-- SLF4J with Logback -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter</artifactId>
</dependency>

<!-- For non-Spring projects -->
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-api</artifactId>
    <version>2.0.9</version>
</dependency>
<dependency>
    <groupId>ch.qos.logback</groupId>
    <artifactId>logback-classic</artifactId>
    <version>1.4.14</version>
</dependency>
*/

public class SL4J_Logging_Answer {
    
    // Create Logger instance for this class
    private static final Logger logger = LoggerFactory.getLogger(SL4J_Logging_Answer.class);
    
    private String appName;
    
    public SL4J_Logging_Answer(String appName) {
        this.appName = appName;
        logger.info("{} application initialized", appName);
    }
    
    public void processData(String data) {
        logger.debug("Starting data processing: {}", data);
        
        try {
            if (data == null || data.isEmpty()) {
                logger.warn("Received empty or null data in {}", appName);
                throw new IllegalArgumentException("Data cannot be null or empty");
            }
            
            // Simulate processing
            logger.trace("Data length: {}", data.length());
            String processed = data.toUpperCase();
            logger.debug("Data processed successfully: {}", processed);
            
        } catch (IllegalArgumentException e) {
            logger.error("Error processing data in {}: {}", appName, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error in {}: {}", appName, e.getMessage(), e);
        }
    }
    
    public void performCalculation(int a, int b) {
        logger.info("Performing calculation: {} + {}", a, b);
        
        try {
            int result = a / b;
            logger.debug("Calculation result: {}", result);
            logger.info("Calculation completed successfully");
            
        } catch (ArithmeticException e) {
            logger.error("Division by zero error: Cannot divide {} by {}", a, b);
            logger.warn("Returning default value 0");
        }
    }
    
    public void connectToService(String serviceUrl) {
        logger.info("Attempting to connect to service: {}", serviceUrl);
        
        // START event
        logger.debug("START - Connection attempt to {}", serviceUrl);
        
        try {
            // Simulate connection
            if (serviceUrl == null) {
                throw new RuntimeException("Service URL is null");
            }
            
            logger.info("Successfully connected to {}", serviceUrl);
            
        } catch (Exception e) {
            logger.error("Failed to connect to service: {}", serviceUrl, e);
        } finally {
            // END event
            logger.debug("END - Connection attempt to {}", serviceUrl);
        }
    }
    
    public static void main(String[] args) {
        logger.info("=== SLF4J Logging Exercise Demo ===");
        logger.info("START - Application started");
        
        SL4J_Logging_Answer app = new SL4J_Logging_Answer("MyApp");
        
        // Test various logging scenarios
        logger.info("Test 1: Normal data processing");
        app.processData("Hello, World!");
        
        logger.info("Test 2: Error scenario");
        try {
            app.processData("");
        } catch (Exception e) {
            // Expected - error already logged via SLF4J
        }
        
        logger.info("Test 3: Calculation with error");
        app.performCalculation(10, 0);
        
        logger.info("Test 4: Service connection");
        app.connectToService("https://api.example.com");
        
        logger.info("Test 5: Null service");
        app.connectToService(null);
        
        logger.info("END - Application completed");
        logger.info("Check console for formatted log output above.");
        logger.info("Also check logs/application.log for file output.");
    }
}

// ===== logback.xml Configuration =====
/*
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
    <!-- Console Appender -->
    <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>
    
    <!-- File Appender -->
    <appender name="FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>logs/application.log</file>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>logs/application-%d{yyyy-MM-dd}.%i.log</fileNamePattern>
            <maxHistory>7</maxHistory>
            <timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                <maxFileSize>10MB</maxFileSize>
            </timeBasedFileNamingAndTriggeringPolicy>
        </rollingPolicy>
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>
    
    <!-- Logger Levels -->
    <logger name="com.cognizant" level="DEBUG"/>
    <root level="INFO">
        <appender-ref ref="CONSOLE"/>
        <appender-ref ref="FILE"/>
    </root>
</configuration>
*/
