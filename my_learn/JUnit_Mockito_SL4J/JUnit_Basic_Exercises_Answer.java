// ============================================================
// JUnit Basic Testing - Exercise 1: Setting Up JUnit
// Exercise 3: Assertions in JUnit
// Exercise 4: Arrange-Act-Assert (AAA) / Teardown Methods
// ============================================================

// --- Calculator.java (Class to be tested) ---
public class Calculator {
    
    public int add(int a, int b) {
        return a + b;
    }
    
    public int subtract(int a, int b) {
        return a - b;
    }
    
    public int multiply(int a, int b) {
        return a * b;
    }
    
    public int divide(int a, int b) {
        if (b == 0) {
            throw new ArithmeticException("Division by zero is not allowed");
        }
        return a / b;
    }
    
    public double power(double base, double exponent) {
        return Math.pow(base, exponent);
    }
    
    public boolean isEven(int number) {
        return number % 2 == 0;
    }
    
    public boolean isPalindrome(String str) {
        if (str == null) return false;
        String reversed = new StringBuilder(str).reverse().toString();
        return str.equals(reversed);
    }
}

// --- CalculatorTest.java (JUnit Test Class - Exercise 1: Setup, Exercise 3: Assertions, Exercise 4: AAA) ---
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CalculatorTest {
    
    private Calculator calculator;
    private static int testCount = 0;
    
    // @BeforeAll - Runs once before all tests (Exercise 1: Setup)
    @BeforeAll
    static void setUpBeforeAll() {
        System.out.println("=== Calculator Test Suite Started ===");
    }
    
    // @BeforeEach - Runs before each test (Arrange phase)
    @BeforeEach
    void setUp() {
        // Arrange: Create the calculator object before each test
        calculator = new Calculator();
        testCount++;
        System.out.println("\n--- Test " + testCount + " ---");
    }
    
    // @AfterEach - Runs after each test (Teardown - Exercise 4)
    @AfterEach
    void tearDown() {
        // Teardown: Clean up after each test
        calculator = null;
        System.out.println("Cleanup completed for Test " + testCount);
    }
    
    // @AfterAll - Runs once after all tests
    @AfterAll
    static void tearDownAfterAll() {
        System.out.println("\n=== Calculator Test Suite Completed ===");
        System.out.println("Total tests executed: " + testCount);
    }
    
    // ===== Exercise 3: Assertions in JUnit =====
    
    @Test
    @DisplayName("Test addition with positive numbers")
    void testAddition() {
        // Arrange (done in @BeforeEach)
        // Act
        int result = calculator.add(10, 20);
        // Assert
        assertEquals(30, result, "10 + 20 should equal 30");
        assertNotEquals(25, result, "10 + 20 should not equal 25");
        assertTrue(result > 0, "Result should be positive");
    }
    
    @Test
    @DisplayName("Test addition with negative numbers")
    void testAdditionWithNegatives() {
        assertEquals(-5, calculator.add(-10, 5), "-10 + 5 should equal -5");
        assertEquals(-15, calculator.add(-10, -5), "-10 + -5 should equal -15");
    }
    
    @Test
    @DisplayName("Test subtraction")
    void testSubtraction() {
        assertEquals(10, calculator.subtract(20, 10), "20 - 10 should equal 10");
        assertEquals(-5, calculator.subtract(10, 15), "10 - 15 should equal -5");
    }
    
    @Test
    @DisplayName("Test multiplication")
    void testMultiplication() {
        assertEquals(50, calculator.multiply(10, 5), "10 * 5 should equal 50");
        assertEquals(0, calculator.multiply(10, 0), "10 * 0 should equal 0");
        assertAll("Multiplication",
            () -> assertEquals(50, calculator.multiply(10, 5)),
            () -> assertEquals(-20, calculator.multiply(-4, 5)),
            () -> assertEquals(20, calculator.multiply(-4, -5))
        );
    }
    
    @Test
    @DisplayName("Test division")
    void testDivision() {
        assertEquals(5, calculator.divide(10, 2), "10 / 2 should equal 5");
        assertEquals(0, calculator.divide(1, 10), "1 / 10 should equal 0");
    }
    
    @Test
    @DisplayName("Test division by zero throws exception")
    void testDivisionByZero() {
        // Assert that an exception is thrown
        Exception exception = assertThrows(ArithmeticException.class, 
            () -> calculator.divide(10, 0));
        assertEquals("Division by zero is not allowed", exception.getMessage());
    }
    
    @Test
    @DisplayName("Test isEven with both even and odd numbers")
    void testIsEven() {
        assertTrue(calculator.isEven(4), "4 should be even");
        assertTrue(calculator.isEven(0), "0 should be even");
        assertFalse(calculator.isEven(7), "7 should not be even");
        assertFalse(calculator.isEven(-3), "-3 should not be even");
    }
    
    @Test
    @DisplayName("Test palindrome strings")
    void testIsPalindrome() {
        assertTrue(calculator.isPalindrome("radar"), "radar is a palindrome");
        assertTrue(calculator.isPalindrome("level"), "level is a palindrome");
        assertFalse(calculator.isPalindrome("hello"), "hello is not a palindrome");
        assertFalse(calculator.isPalindrome(null), "null is not a palindrome");
    }
    
    @Test
    @DisplayName("Test power calculation")
    void testPower() {
        assertEquals(8.0, calculator.power(2, 3), "2^3 should equal 8");
        assertEquals(1.0, calculator.power(5, 0), "5^0 should equal 1");
        assertEquals(0.25, calculator.power(2, -2), "2^-2 should equal 0.25");
    }
    
    @Test
    @DisplayName("Group assertion example")
    void testMultipleAssertions() {
        // Using assertAll to group related assertions
        assertAll("Calculator Operations",
            () -> assertEquals(15, calculator.add(10, 5)),
            () -> assertEquals(5, calculator.subtract(10, 5)),
            () -> assertEquals(50, calculator.multiply(10, 5)),
            () -> assertEquals(2, calculator.divide(10, 5))
        );
    }
}
