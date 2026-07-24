// ============================================================
// Mockito Exercise 1: Mocking and Stubbing
// Mockito Exercise 2: Verifying Interactions
// ============================================================

import org.junit.jupiter.api.*;
import org.mockito.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

// ===== Service Interfaces and Classes =====

interface PaymentGateway {
    boolean processPayment(String cardNumber, double amount);
    String getTransactionStatus(String transactionId);
}

interface EmailService {
    void sendEmail(String to, String subject, String body);
}

interface UserRepository {
    User findUserById(int id);
    boolean saveUser(User user);
    User findUserByEmail(String email);
}

class User {
    private int id;
    private String name;
    private String email;
    
    public User(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
    
    public int getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
}

class OrderService {
    private PaymentGateway paymentGateway;
    private EmailService emailService;
    private UserRepository userRepository;
    
    public OrderService(PaymentGateway paymentGateway, EmailService emailService, 
                       UserRepository userRepository) {
        this.paymentGateway = paymentGateway;
        this.emailService = emailService;
        this.userRepository = userRepository;
    }
    
    public String placeOrder(int userId, double amount, String cardNumber) {
        User user = userRepository.findUserById(userId);
        if (user == null) {
            return "User not found";
        }
        
        boolean paymentSuccess = paymentGateway.processPayment(cardNumber, amount);
        if (paymentSuccess) {
            emailService.sendEmail(user.getEmail(), "Order Confirmation", 
                "Your order of $" + amount + " has been placed successfully.");
            return "Order placed successfully";
        } else {
            return "Payment failed";
        }
    }
    
    public boolean processRefund(int userId, double amount, String transactionId) {
        String status = paymentGateway.getTransactionStatus(transactionId);
        if ("COMPLETED".equals(status)) {
            User user = userRepository.findUserById(userId);
            emailService.sendEmail(user.getEmail(), "Refund Processed", 
                "Your refund of $" + amount + " has been processed.");
            return true;
        }
        return false;
    }
}

// ============================================================
// Mockito Exercise 1: Mocking and Stubbing
// ============================================================
class Mockito_Exercise1_MockingStubbing_Answer {
    
    @Test
    @DisplayName("Mockito Ex1: Basic mocking with when/thenReturn")
    void testBasicMocking() {
        // Create mock objects
        PaymentGateway mockGateway = mock(PaymentGateway.class);
        EmailService mockEmailService = mock(EmailService.class);
        UserRepository mockUserRepo = mock(UserRepository.class);
        
        // Stub the methods
        User testUser = new User(1, "John Doe", "john@example.com");
        when(mockUserRepo.findUserById(1)).thenReturn(testUser);
        when(mockGateway.processPayment("1234-5678-9012-3456", 100.00)).thenReturn(true);
        
        // Create service with mocked dependencies
        OrderService orderService = new OrderService(mockGateway, mockEmailService, mockUserRepo);
        
        // Test
        String result = orderService.placeOrder(1, 100.00, "1234-5678-9012-3456");
        
        // Verify
        assertEquals("Order placed successfully", result);
        verify(mockEmailService).sendEmail("john@example.com", "Order Confirmation", 
            "Your order of $100.0 has been placed successfully.");
    }
    
    @Test
    @DisplayName("Mockito Ex1: Stubbing with multiple return values")
    void testMultipleReturnValues() {
        List<String> mockList = mock(List.class);
        
        // Stub multiple return values in sequence
        when(mockList.get(0)).thenReturn("First");
        when(mockList.get(1)).thenReturn("Second");
        when(mockList.get(2)).thenThrow(new RuntimeException("Index out of bounds"));
        
        assertEquals("First", mockList.get(0));
        assertEquals("Second", mockList.get(1));
        assertThrows(RuntimeException.class, () -> mockList.get(2));
    }
    
    @Test
    @DisplayName("Mockito Ex1: Stubbing void methods (doNothing, doThrow)")
    void testVoidMethodStubbing() {
        EmailService mockEmail = mock(EmailService.class);
        
        // doNothing is the default for void methods
        doNothing().when(mockEmail).sendEmail(anyString(), anyString(), anyString());
        
        // No exception should be thrown
        assertDoesNotThrow(() -> mockEmail.sendEmail("test@test.com", "Subject", "Body"));
        
        // doThrow for exception testing
        doThrow(new RuntimeException("Email service down"))
            .when(mockEmail).sendEmail(eq("bad@test.com"), anyString(), anyString());
        
        assertThrows(RuntimeException.class, 
            () -> mockEmail.sendEmail("bad@test.com", "Subject", "Body"));
    }
    
    @Test
    @DisplayName("Mockito Ex1: Argument matchers")
    void testArgumentMatchers() {
        UserRepository mockRepo = mock(UserRepository.class);
        
        // Using any() matchers
        when(mockRepo.findUserById(anyInt())).thenReturn(new User(1, "Test", "test@test.com"));
        when(mockRepo.saveUser(any(User.class))).thenReturn(true);
        
        User result1 = mockRepo.findUserById(100);
        User result2 = mockRepo.findUserById(999);
        
        assertNotNull(result1);
        assertEquals("Test", result1.getName());
        assertNotNull(result2);
        assertEquals("Test", result2.getName());
        assertTrue(mockRepo.saveUser(new User(2, "New", "new@test.com")));
    }
}

// ============================================================
// Mockito Exercise 2: Verifying Interactions
// ============================================================
class Mockito_Exercise2_VerifyingInteractions_Answer {
    
    @Test
    @DisplayName("Mockito Ex2: Basic verification")
    void testBasicVerification() {
        PaymentGateway mockGateway = mock(PaymentGateway.class);
        
        // Call the mock
        mockGateway.processPayment("1234", 100.00);
        mockGateway.processPayment("5678", 200.00);
        
        // Verify interactions
        verify(mockGateway).processPayment("1234", 100.00);
        verify(mockGateway).processPayment("5678", 200.00);
    }
    
    @Test
    @DisplayName("Mockito Ex2: Verify number of invocations")
    void testVerifyNumberOfInvocations() {
        EmailService mockEmail = mock(EmailService.class);
        
        // Call multiple times
        mockEmail.sendEmail("a@test.com", "Sub1", "Body1");
        mockEmail.sendEmail("b@test.com", "Sub2", "Body2");
        mockEmail.sendEmail("a@test.com", "Sub1", "Body1");
        
        // Verify exact number of calls
        verify(mockEmail, times(2)).sendEmail("a@test.com", "Sub1", "Body1");
        verify(mockEmail, times(1)).sendEmail("b@test.com", "Sub2", "Body2");
        
        // Never called
        verify(mockEmail, never()).sendEmail("c@test.com", "Sub3", "Body3");
        
        // At least/at most
        verify(mockEmail, atLeast(1)).sendEmail(anyString(), anyString(), anyString());
        verify(mockEmail, atMost(3)).sendEmail(anyString(), anyString(), anyString());
    }
    
    @Test
    @DisplayName("Mockito Ex2: Verify order of interactions")
    void testVerifyOrder() {
        PaymentGateway mockGateway = mock(PaymentGateway.class);
        EmailService mockEmail = mock(EmailService.class);
        
        // Perform interactions in a specific order
        mockGateway.processPayment("1111", 50.00);
        mockEmail.sendEmail("user@test.com", "Success", "Payment of $50.00 successful");
        mockGateway.processPayment("2222", 75.00);
        
        // Verify order using InOrder
        InOrder inOrder = inOrder(mockGateway, mockEmail);
        inOrder.verify(mockGateway).processPayment("1111", 50.00);
        inOrder.verify(mockEmail).sendEmail("user@test.com", "Success", 
            "Payment of $50.00 successful");
        inOrder.verify(mockGateway).processPayment("2222", 75.00);
    }
    
    @Test
    @DisplayName("Mockito Ex2: Verify no more interactions")
    void testVerifyNoMoreInteractions() {
        UserRepository mockRepo = mock(UserRepository.class);
        
        mockRepo.findUserById(1);
        mockRepo.saveUser(new User(2, "Test", "test@test.com"));
        
        verify(mockRepo).findUserById(1);
        verify(mockRepo).saveUser(any(User.class));
        
        // Verify no other interactions happened
        verifyNoMoreInteractions(mockRepo);
    }
    
    @Test
    @DisplayName("Mockito Ex2: Verify with timeout")
    void testVerifyWithTimeout() {
        UserRepository mockRepo = mock(UserRepository.class);
        
        // Simulate some async behavior
        mockRepo.findUserByEmail("async@test.com");
        
        // Verify with timeout (waits up to 100ms for the interaction)
        verify(mockRepo, timeout(100)).findUserByEmail("async@test.com");
    }
    
    @Test
    @DisplayName("Mockito Ex2: Full integration test with verification")
    void testFullIntegrationWithVerification() {
        // Create mocks
        PaymentGateway mockGateway = mock(PaymentGateway.class);
        EmailService mockEmail = mock(EmailService.class);
        UserRepository mockUserRepo = mock(UserRepository.class);
        
        // Setup stubs
        User testUser = new User(1, "Alice", "alice@example.com");
        when(mockUserRepo.findUserById(1)).thenReturn(testUser);
        when(mockGateway.processPayment("1111-2222-3333-4444", 250.00)).thenReturn(true);
        
        // Create service
        OrderService orderService = new OrderService(mockGateway, mockEmail, mockUserRepo);
        
        // Execute
        String result = orderService.placeOrder(1, 250.00, "1111-2222-3333-4444");
        
        // Verify result
        assertEquals("Order placed successfully", result);
        
        // Verify all interactions happened correctly
        verify(mockUserRepo).findUserById(1);
        verify(mockGateway).processPayment("1111-2222-3333-4444", 250.00);
        verify(mockEmail).sendEmail("alice@example.com", "Order Confirmation", 
            "Your order of $250.0 has been placed successfully.");
        verifyNoMoreInteractions(mockUserRepo, mockGateway, mockEmail);
    }
}
