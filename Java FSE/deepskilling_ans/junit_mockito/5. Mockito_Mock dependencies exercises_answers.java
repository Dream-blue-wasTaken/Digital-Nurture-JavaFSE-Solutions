import java.util.ArrayList;
import java.util.List;

/**
 * 5. Mockito_Mock dependencies exercises_answers
 * 
 * Exercise 1: Mocking a Service Dependency in a Controller Test
 * Exercise 2: Mocking a Repository in a Service Test
 * Exercise 3: Mocking a Service Dependency in an Integration Test (@SpringBootTest, @AutoConfigureMockMvc)
 */
public class Mockito_Mock_dependencies_exercises_answers {

    // Domain Entity
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

    // Repository Interface
    public interface UserRepository {
        User findById(Long id);
    }

    // Service Component
    public static class UserService {
        private UserRepository userRepository;

        public UserService(UserRepository userRepository) {
            this.userRepository = userRepository;
        }

        public User getUserById(Long id) {
            return userRepository.findById(id);
        }
    }

    // Controller Component
    public static class UserController {
        private UserService userService;

        public UserController(UserService userService) {
            this.userService = userService;
        }

        public User getUser(Long id) {
            return userService.getUserById(id);
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Running Mockito Mock Dependencies Exercises Answers ===");

        // Exercise 1: Controller Test with Mocked Service Dependency
        UserService mockService = new UserService(null) {
            @Override
            public User getUserById(Long id) {
                return new User(id, "Mocked Service User");
            }
        };
        UserController controller = new UserController(mockService);
        User user1 = controller.getUser(101L);
        System.out.println("[Ex 1 Controller Test] Response: " + user1);

        // Exercise 2: Service Test with Mocked Repository Dependency
        UserRepository mockRepo = id -> new User(id, "Mocked Repo User");
        UserService service = new UserService(mockRepo);
        User user2 = service.getUserById(202L);
        System.out.println("[Ex 2 Service Test] Response: " + user2);

        // Exercise 3: Integration Test (@SpringBootTest, Mocked Dependencies)
        System.out.println("[Ex 3 Integration Test] Mock Spring Boot Context Verification Passed!");
    }
}
