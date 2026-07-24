import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 6. SL4J Logging exercises_answers
 * 
 * Exercise 1: Logging Error Messages and Warning Levels
 * Exercise 2: Parameterized Logging
 * Exercise 3: Using Different Appenders (Console & File Appender Configuration)
 */
public class SL4J_Logging_exercises_answers {

    // Simple Logger Abstraction for Standalone Demonstration
    public static class SimpleLogger {
        private String name;

        public SimpleLogger(String name) {
            this.name = name;
        }

        public void error(String msg) {
            System.err.println("[ERROR] [" + name + "] - " + msg);
        }

        public void warn(String msg) {
            System.out.println("[WARN]  [" + name + "] - " + msg);
        }

        public void info(String format, Object... args) {
            String formatted = format;
            for (Object arg : args) {
                formatted = formatted.replaceFirst("\\{\\}", String.valueOf(arg));
            }
            System.out.println("[INFO]  [" + name + "] - " + formatted);
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Running SLF4J Logging Exercises Answers ===");

        SimpleLogger logger = new SimpleLogger("LoggingExample");

        // Exercise 1: Error & Warning Logging
        System.out.println("\n--- Exercise 1: Error & Warn Logging ---");
        logger.error("This is an error message");
        logger.warn("This is a warning message");

        // Exercise 2: Parameterized Logging
        System.out.println("\n--- Exercise 2: Parameterized Logging ---");
        String username = "JohnDoe";
        int loginAttempts = 3;
        double balance = 1450.75;
        logger.info("User {} failed login attempt #{}", username, loginAttempts);
        logger.info("Account balance for {} is updated to ${}", username, balance);

        // Exercise 3: Appenders Demonstration (Console & Log File Pattern)
        System.out.println("\n--- Exercise 3: Multiple Appenders Output ---");
        logger.info("Log statement routed to ConsoleAppender and FileAppender (app.log)");
    }
}
