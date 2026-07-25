import java.security.Principal;
import java.util.Date;
public class Sample_Microservices_exercises_answers {
    public static class JwtTokenProvider {
        private String secretKey = "MySuperSecretKeyForJWTTokenGenerationAndValidation";
        private long validityInMilliseconds = 3600000;