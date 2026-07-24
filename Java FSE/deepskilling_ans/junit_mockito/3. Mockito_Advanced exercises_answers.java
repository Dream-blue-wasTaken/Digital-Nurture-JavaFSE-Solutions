/**
 * 3. Mockito_Advanced exercises_answers
 * 
 * Exercise 1: Mocking Databases and Repositories
 * Exercise 2: Mocking External Services (RESTful APIs)
 * Exercise 3: Mocking File I/O
 * Exercise 4: Mocking Network Interactions
 * Exercise 5: Mocking Multiple Return Values
 */
public class Mockito_Advanced_exercises_answers {

    // Interfaces for External Resources
    public interface Repository {
        String getData();
    }

    public interface RestClient {
        String getResponse();
    }

    public interface FileReader {
        String read();
    }

    public interface FileWriter {
        void write(String content);
    }

    public interface NetworkClient {
        String connect();
    }

    // Target Services
    public static class DatabaseService {
        private Repository repo;
        public DatabaseService(Repository repo) { this.repo = repo; }
        public String processData() {
            return "Processed " + repo.getData();
        }
    }

    public static class ApiService {
        private RestClient client;
        public ApiService(RestClient client) { this.client = client; }
        public String fetchData() {
            return "Fetched " + client.getResponse();
        }
    }

    public static class FileService {
        private FileReader reader;
        private FileWriter writer;
        public FileService(FileReader reader, FileWriter writer) {
            this.reader = reader;
            this.writer = writer;
        }
        public String processFile() {
            String content = reader.read();
            String processed = "Processed " + content;
            writer.write(processed);
            return processed;
        }
    }

    public static class NetworkService {
        private NetworkClient netClient;
        public NetworkService(NetworkClient netClient) { this.netClient = netClient; }
        public String connectToServer() {
            return "Connected to " + netClient.connect();
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Running Advanced Mockito Exercises Answers ===");

        // Exercise 1: Database Repository Mock
        Repository mockRepo = () -> "Mock Data";
        DatabaseService dbService = new DatabaseService(mockRepo);
        System.out.println("[Ex 1] Database Service Output: " + dbService.processData());

        // Exercise 2: RESTful API Client Mock
        RestClient mockRestClient = () -> "Mock Response";
        ApiService apiService = new ApiService(mockRestClient);
        System.out.println("[Ex 2] API Service Output: " + apiService.fetchData());

        // Exercise 3: File I/O Mocking
        FileReader mockReader = () -> "Mock File Content";
        FileWriter mockWriter = content -> System.out.println("[Ex 3 File Writer]: Written -> " + content);
        FileService fileService = new FileService(mockReader, mockWriter);
        System.out.println("[Ex 3] File Service Result: " + fileService.processFile());

        // Exercise 4: Network Interaction Mocking
        NetworkClient mockNetwork = () -> "Mock Connection";
        NetworkService netService = new NetworkService(mockNetwork);
        System.out.println("[Ex 4] Network Service Output: " + netService.connectToServer());

        // Exercise 5: Multiple Return Values
        Repository multiRepo = new Repository() {
            private int count = 0;
            public String getData() {
                count++;
                return (count == 1) ? "First Mock Data" : "Second Mock Data";
            }
        };
        DatabaseService multiService = new DatabaseService(multiRepo);
        System.out.println("[Ex 5] First Call: " + multiService.processData());
        System.out.println("[Ex 5] Second Call: " + multiService.processData());
    }
}
