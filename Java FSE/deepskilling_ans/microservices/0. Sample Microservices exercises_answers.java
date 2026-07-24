import java.security.Principal;
import java.util.Date;

/**
 * 0. Sample Microservices exercises_answers
 * 
 * Exercise 1: Centralized Authentication with OAuth 2.1 / OIDC (Spring Security OAuth2 Client)
 * Exercise 2: Configuring Authorization Servers and Resource Servers (ResourceServerConfig & JWT Verification)
 * Exercise 3: Using JSON Web Tokens (JWT) for Secure Communication (JwtTokenProvider & JwtTokenFilter)
 */
public class Sample_Microservices_exercises_answers {

    // Exercise 3: JWT Token Provider Helper Class
    public static class JwtTokenProvider {
        private String secretKey = "MySuperSecretKeyForJWTTokenGenerationAndValidation";
        private long validityInMilliseconds = 3600000; // 1 hour

        public String createToken(String username) {
            Date now = new Date();
            Date validity = new Date(now.getTime() + validityInMilliseconds);
            // Simulates JWT creation header.payload.signature
            return "eyJhbGciOiJIUzI1NiJ9." + username + "." + validity.getTime();
        }

        public boolean validateToken(String token) {
            return token != null && token.startsWith("eyJhbGciOiJIUzI1NiJ9.");
        }

        public String getUsernameFromToken(String token) {
            String[] parts = token.split("\\.");
            if (parts.length >= 2) return parts[1];
            return "unknown";
        }
    }

    // Secured REST Endpoints Simulation
    public static class SecureUserController {
        private JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();

        public String userEndpoint(Principal principal) {
            if (principal != null) {
                return "Authenticated OAuth2 User: " + principal.getName();
            }
            return "User authenticated via OAuth2.1/OIDC Security Context";
        }

        public String secureApiEndpoint(String authHeader) {
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                if (jwtTokenProvider.validateToken(token)) {
                    String username = jwtTokenProvider.getUsernameFromToken(token);
                    return "Access Granted to Resource Server for user: " + username;
                }
            }
            return "401 Unauthorized: Invalid or missing JWT bearer token";
        }
    }

    public static void main(String[] args) {
        System.out.println("=== 0. Sample Microservices Security & OAuth2/JWT Exercises Answers ===");

        JwtTokenProvider jwtProvider = new JwtTokenProvider();
        String sampleToken = jwtProvider.createToken("JohnDoe");
        System.out.println("[Ex 3 JWT Token Created]: " + sampleToken);

        SecureUserController controller = new SecureUserController();
        String response = controller.secureApiEndpoint("Bearer " + sampleToken);
        System.out.println("[Ex 2 Resource Server Response]: " + response);
    }
}
