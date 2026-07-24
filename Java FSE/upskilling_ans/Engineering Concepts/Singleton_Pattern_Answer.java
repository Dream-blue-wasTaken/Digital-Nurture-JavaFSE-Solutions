/**
 * Exercise 1: Implementing the Singleton Pattern
 * 
 * Scenario: Create a thread-safe Logger utility class that ensures only
 * one instance of the logger exists throughout the application.
 */

// Thread-safe Singleton Logger class
public class Logger {
    
    // Private static volatile instance variable
    private static volatile Logger instance;
    
    // Private constructor to prevent instantiation
    private Logger() {
        // Initialize logger configuration
        System.out.println("Logger initialized");
    }
    
    // Public static method to get the singleton instance (Double-Checked Locking)
    public static Logger getInstance() {
        if (instance == null) {
            synchronized (Logger.class) {
                if (instance == null) {
                    instance = new Logger();
                }
            }
        }
        return instance;
    }
    
    // Logger methods
    public void log(String message) {
        System.out.println("[LOG] " + message);
    }
    
    public void error(String message) {
        System.out.println("[ERROR] " + message);
    }
    
    public void warn(String message) {
        System.out.println("[WARN] " + message);
    }
    
    // Test the Singleton
    public static void main(String[] args) {
        // Get the singleton instance
        Logger logger1 = Logger.getInstance();
        Logger logger2 = Logger.getInstance();
        
        // Test logging methods
        logger1.log("Application started");
        logger1.warn("Low memory warning");
        logger1.error("Null pointer exception");
        
        // Verify both references point to the same instance
        System.out.println("Are both instances same? " + (logger1 == logger2)); // true
        
        // Thread safety test
        Runnable task = () -> {
            Logger threadLogger = Logger.getInstance();
            threadLogger.log("Logging from thread: " + Thread.currentThread().getName());
        };
        
        Thread t1 = new Thread(task, "Thread-1");
        Thread t2 = new Thread(task, "Thread-2");
        
        t1.start();
        t2.start();
    }
}
