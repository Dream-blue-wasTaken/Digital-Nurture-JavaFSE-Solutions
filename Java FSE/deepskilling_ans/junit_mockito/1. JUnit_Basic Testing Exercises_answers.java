import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 1. JUnit_Basic Testing Exercises_answers
 * 
 * Exercise 1: Setting Up JUnit (pom.xml setup & basic test structure)
 * Exercise 2: Writing Basic JUnit Tests
 * Exercise 3: Assertions in JUnit (assertEquals, assertTrue, assertFalse, assertNull, assertNotNull)
 */
public class JUnit_Basic_Testing_Exercises_answers {

    // Target Class to Test (Exercise 2 & 3)
    public static class Calculator {
        public int add(int a, int b) {
            return a + b;
        }

        public int subtract(int a, int b) {
            return a - b;
        }

        public boolean isEven(int number) {
            return number % 2 == 0;
        }

        public String getGreeting(String name) {
            if (name == null) return null;
            return "Hello, " + name;
        }
    }

    // Exercise 2 & 3: Basic Tests & Assertions Test Class
    public static class CalculatorTest {
        private Calculator calculator;

        @BeforeEach
        public void setUp() {
            calculator = new Calculator();
        }

        @Test
        @DisplayName("Test Addition")
        public void testAdd() {
            int result = calculator.add(10, 20);
            assertEquals(30, result, "10 + 20 should equal 30");
        }

        @Test
        @DisplayName("Test Subtraction")
        public void testSubtract() {
            int result = calculator.subtract(50, 20);
            assertEquals(30, result, "50 - 20 should equal 30");
        }

        @Test
        @DisplayName("Test Assertions: Boolean, Nullability")
        public void testAssertions() {
            // assertTrue & assertFalse
            assertTrue(calculator.isEven(4), "4 is an even number");
            assertFalse(calculator.isEven(7), "7 is not an even number");

            // assertNull & assertNotNull
            assertNotNull(calculator.getGreeting("Alice"), "Greeting for Alice should not be null");
            assertNull(calculator.getGreeting(null), "Greeting for null input should be null");
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Running JUnit Basic Testing Exercises ===");
        CalculatorTest test = new CalculatorTest();
        test.setUp();
        test.testAdd();
        test.testSubtract();
        test.testAssertions();
        System.out.println("All JUnit Basic Tests Passed Successfully!");
    }
}
