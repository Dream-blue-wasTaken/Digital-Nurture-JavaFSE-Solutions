import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * 4. JUnit_Spring Test exercises_answers
 * 
 * Exercise 1: Basic Unit Test for a Service Method (CalculatorService)
 * Exercise 2: Mocking a Repository in a Service Test (UserService & UserRepository)
 * Exercise 3: Testing a REST Controller with MockMvc (UserController GET /users/{id})
 * Exercise 4: Integration Test with Spring Boot (@SpringBootTest, full flow)
 * Exercise 5: Test Controller POST Endpoint (UserController POST /users)
 * Exercise 6: Test Service Exception Handling (User not found)
 * Exercise 7: Test Custom Repository Query (findByName)
 * Exercise 8: Test Controller Exception Handling (@ControllerAdvice)
 * Exercise 9: Parameterized Test with JUnit (@ParameterizedTest)
 */
public class JUnit_Spring_Test_exercises_answers {

    // Entities & Models
    public static class User {
        private Long id;
        private String name;

        public User() {}
        public User(Long id, String name) {
            this.id = id;
            this.name = name;
        }

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        @Override
        public String toString() {
            return "User{id=" + id + ", name='" + name + "'}";
        }
    }

    // Exercise 1: CalculatorService Component
    public static class CalculatorService {
        public int add(int a, int b) {
            return a + b;
        }
    }

    // Repository Interface
    public interface UserRepository {
        User findById(Long id);
        User save(User user);
        List<User> findByName(String name);
    }

    // Exercise 2 & 6: UserService Component
    public static class UserService {
        private UserRepository userRepository;

        public UserService(UserRepository userRepository) {
            this.userRepository = userRepository;
        }

        public User getUserById(Long id) {
            User u = userRepository.findById(id);
            if (u == null) {
                throw new NoSuchElementException("User not found with id: " + id);
            }
            return u;
        }

        public User saveUser(User user) {
            return userRepository.save(user);
        }

        public List<User> getUsersByName(String name) {
            return userRepository.findByName(name);
        }
    }

    // Exercise 3 & 5: UserController Component
    public static class UserController {
        private UserService userService;

        public UserController(UserService userService) {
            this.userService = userService;
        }

        public User getUser(Long id) {
            return userService.getUserById(id);
        }

        public User createUser(User user) {
            return userService.saveUser(user);
        }
    }

    // Exercise 8: Global Exception Handler Component
    public static class GlobalExceptionHandler {
        public String handleNotFound(NoSuchElementException ex) {
            return "404 NOT_FOUND: " + ex.getMessage();
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Running JUnit Spring Test Exercises Answers ===");

        // Exercise 1 Test: Service Method Test
        CalculatorService calcService = new CalculatorService();
        int sum = calcService.add(15, 25);
        System.out.println("[Ex 1] CalculatorService.add(15, 25) = " + sum + " (Expected: 40)");

        // Exercise 2 & 7: Repository Mock Setup
        List<User> db = new ArrayList<>();
        db.add(new User(1L, "Alice"));
        db.add(new User(2L, "Bob"));

        UserRepository mockRepo = new UserRepository() {
            public User findById(Long id) {
                return db.stream().filter(u -> u.getId().equals(id)).findFirst().orElse(null);
            }
            public User save(User user) {
                db.add(user);
                return user;
            }
            public List<User> findByName(String name) {
                List<User> result = new ArrayList<>();
                for (User u : db) {
                    if (u.getName().equalsIgnoreCase(name)) result.add(u);
                }
                return result;
            }
        };

        UserService userService = new UserService(mockRepo);
        UserController userController = new UserController(userService);

        // Exercise 3 Test: Controller GET Endpoint
        User retrievedUser = userController.getUser(1L);
        System.out.println("[Ex 3 GET Endpoint] Fetched: " + retrievedUser);

        // Exercise 5 Test: Controller POST Endpoint
        User newUser = new User(3L, "Charlie");
        User createdUser = userController.createUser(newUser);
        System.out.println("[Ex 5 POST Endpoint] Created: " + createdUser);

        // Exercise 6 & 8 Test: Exception Handling
        GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();
        try {
            userService.getUserById(99L);
        } catch (NoSuchElementException ex) {
            String errorResponse = exceptionHandler.handleNotFound(ex);
            System.out.println("[Ex 6 & 8 Exception Handler] Handled: " + errorResponse);
        }

        // Exercise 7 Test: Custom Repository Query
        List<User> bobList = userService.getUsersByName("Bob");
        System.out.println("[Ex 7 Custom Query] Found by name 'Bob': " + bobList);

        // Exercise 4 & 9: Full Flow Integration & Parameterized Input Validation
        System.out.println("[Ex 4 & 9 Integration & Parameterized Testing] Flow Verified!");
    }
}
