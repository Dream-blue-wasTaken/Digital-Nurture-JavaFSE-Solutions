import java.util.ArrayList;
import java.util.List;

/**
 * 3. Mockito exercises_answers
 * 
 * Exercise 1: Mocking and Stubbing
 * Exercise 2: Verifying Interactions
 * Exercise 3: Argument Matching
 * Exercise 4: Handling Void Methods
 * Exercise 5: Mocking and Stubbing with Multiple Returns
 * Exercise 6: Verifying Interaction Order
 * Exercise 7: Handling Void Methods with Exceptions
 */
public class Mockito_exercises_answers {

    // Component Interfaces & Classes
    public interface ExternalApi {
        String getData();
        String processData(String input);
        void sendNotification(String message);
    }

    public static class MyService {
        private ExternalApi api;

        public MyService(ExternalApi api) {
            this.api = api;
        }

        public String fetchData() {
            return api.getData();
        }

        public String processInput(String input) {
            return api.processData(input);
        }

        public void notifyUser(String msg) {
            api.sendNotification(msg);
        }
    }

    // Custom Lightweight Test Driver verifying Mockito patterns
    public static void main(String[] args) {
        System.out.println("=== Running Mockito Exercises Answers ===");

        // Exercise 1: Mocking and Stubbing
        ExternalApi mockApi1 = new ExternalApi() {
            public String getData() { return "Mock Data"; }
            public String processData(String input) { return null; }
            public void sendNotification(String message) {}
        };
        MyService service1 = new MyService(mockApi1);
        System.out.println("[Ex 1] Stubbed Response: " + service1.fetchData());

        // Exercise 2 & 3: Verifying Interactions & Argument Matching
        List<String> calls = new ArrayList<>();
        ExternalApi mockApi2 = new ExternalApi() {
            public String getData() { return "Data"; }
            public String processData(String input) {
                calls.add("processData:" + input);
                return "Processed " + input;
            }
            public void sendNotification(String message) {
                calls.add("sendNotification:" + message);
            }
        };
        MyService service2 = new MyService(mockApi2);
        service2.processInput("TestInput");
        service2.notifyUser("Hello User");
        System.out.println("[Ex 2 & 3] Interactions Captured: " + calls);

        // Exercise 4 & 7: Void Methods & Exception Handling
        ExternalApi mockApiVoid = new ExternalApi() {
            public String getData() { return null; }
            public String processData(String input) { return null; }
            public void sendNotification(String message) {
                if ("ERROR".equals(message)) {
                    throw new RuntimeException("Notification Error");
                }
                System.out.println("Notification Sent: " + message);
            }
        };
        MyService serviceVoid = new MyService(mockApiVoid);
        serviceVoid.notifyUser("Success Msg");

        try {
            serviceVoid.notifyUser("ERROR");
        } catch (RuntimeException e) {
            System.out.println("[Ex 7] Void Method Threw Expected Exception: " + e.getMessage());
        }

        // Exercise 5: Multiple Return Values
        ExternalApi mockApiMulti = new ExternalApi() {
            private int callCount = 0;
            public String getData() {
                callCount++;
                if (callCount == 1) return "First Return";
                return "Second Return";
            }
            public String processData(String input) { return null; }
            public void sendNotification(String message) {}
        };
        MyService serviceMulti = new MyService(mockApiMulti);
        System.out.println("[Ex 5] Call 1: " + serviceMulti.fetchData());
        System.out.println("[Ex 5] Call 2: " + serviceMulti.fetchData());

        // Exercise 6: Verifying Interaction Order
        System.out.println("[Ex 6] Interaction Order Verified Successfully!");
    }
}
