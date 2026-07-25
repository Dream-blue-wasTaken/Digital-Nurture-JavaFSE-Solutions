import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;

/**
 * 2. JUnit_Advanced Testing exercices_answers
 * 
 * Exercise 1: Parameterized Tests (@ParameterizedTest, @ValueSource)
 * Exercise 2: Test Suites and Categories (@Suite, @SelectClasses)
 * Exercise 3: Test Execution Order (@TestMethodOrder, @Order)
 * Exercise 4: Exception Testing (assertThrows)
 * Exercise 5: Timeout and Performance Testing (assertTimeout)
 */
public class JUnit_Advanced_Testing_exercices_answers {

    // Target Component: EvenChecker (Exercise 1)
    public static class EvenChecker {
        public boolean isEven(int number) {
            return number % 2 == 0;
        }
    }

    // Target Component: ExceptionThrower (Exercise 4)
    public static class ExceptionThrower {
        public void throwException() {
            throw new IllegalArgumentException("Invalid argument provided");
        }
    }

    // Target Component: PerformanceTester (Exercise 5)
    public static class PerformanceTester {
        public void performTask() {
            try {
                Thread.sleep(50); // Simulates fast task execution
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    // Exercise 1 Test Class
    public static class EvenCheckerTest {
        private EvenChecker evenChecker = new EvenChecker();

        @ParameterizedTest
        @ValueSource(ints = {2, 4, 6, 8, 10, 12})
        public void testIsEven(int number) {
            assertTrue(evenChecker.isEven(number), number + " should be even");
        }
    }

    // Exercise 3 Test Class: Test Execution Order
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    public static class OrderedTests {

        @Test
        @Order(1)
        public void firstTest() {
            System.out.println("Executing Step 1: Initialization Test");
            assertTrue(true);
        }

        @Test
        @Order(2)
        public void secondTest() {
            System.out.println("Executing Step 2: Processing Test");
            assertTrue(true);
        }

        @Test
        @Order(3)
        public void thirdTest() {
            System.out.println("Executing Step 3: Cleanup Test");
            assertTrue(true);
        }
    }

    // Exercise 4 Test Class: Exception Testing
    public static class ExceptionThrowerTest {
        private ExceptionThrower thrower = new ExceptionThrower();

        @Test
        public void testExpectedException() {
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> thrower.throwException(),
                "Expected throwException() to throw IllegalArgumentException"
            );
            assertEquals("Invalid argument provided", exception.getMessage());
        }
    }

    // Exercise 5 Test Class: Timeout & Performance Testing
    public static class PerformanceTesterTest {
        private PerformanceTester tester = new PerformanceTester();

        @Test
        public void testTaskTimeout() {
            assertTimeout(Duration.ofMillis(200), () -> {
                tester.performTask();
            }, "Task execution exceeded timeout limit");
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Running JUnit Advanced Testing Exercises ===");

        // Test 1: Parameterized Tests
        EvenCheckerTest evenTest = new EvenCheckerTest();
        int[] sampleInts = {2, 4, 6, 8, 10, 12};
        for (int val : sampleInts) {
            evenTest.testIsEven(val);
        }
        System.out.println("[PASS] Parameterized Tests Passed for inputs: 2, 4, 6, 8, 10, 12");

        // Test 3: Ordered Tests
        OrderedTests ordered = new OrderedTests();
        ordered.firstTest();
        ordered.secondTest();
        ordered.thirdTest();

        // Test 4: Exception Testing
        ExceptionThrowerTest excTest = new ExceptionThrowerTest();
        excTest.testExpectedException();
        System.out.println("[PASS] Exception Testing Passed!");

        // Test 5: Timeout Testing
        PerformanceTesterTest perfTest = new PerformanceTesterTest();
        perfTest.testTaskTimeout();
        System.out.println("[PASS] Timeout & Performance Testing Passed!");
    }
}
