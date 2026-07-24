import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Exercise 1 - 11: Design Patterns and Principles Exercises
 * 
 * Includes implementation for:
 * Exercise 1: Singleton Pattern
 * Exercise 2: Factory Method Pattern
 * Exercise 3: Builder Pattern
 * Exercise 4: Adapter Pattern
 * Exercise 5: Decorator Pattern
 * Exercise 6: Proxy Pattern
 * Exercise 7: Observer Pattern
 * Exercise 8: Strategy Pattern
 * Exercise 9: Command Pattern
 * Exercise 10: MVC Pattern
 * Exercise 11: Dependency Injection
 */
public class Design_Patterns_and_Principles_answers {

    // ==========================================
    // Exercise 1: Singleton Pattern
    // ==========================================
    public static class Logger {
        private static Logger instance;

        private Logger() {
            System.out.println("Logger initialized instance.");
        }

        public static synchronized Logger getInstance() {
            if (instance == null) {
                instance = new Logger();
            }
            return instance;
        }

        public void log(String message) {
            System.out.println("[LOG]: " + message);
        }
    }

    // ==========================================
    // Exercise 2: Factory Method Pattern
    // ==========================================
    public interface Document {
        void open();
    }

    public static class WordDocument implements Document {
        public void open() { System.out.println("Opening Word Document..."); }
    }

    public static class PdfDocument implements Document {
        public void open() { System.out.println("Opening PDF Document..."); }
    }

    public static class ExcelDocument implements Document {
        public void open() { System.out.println("Opening Excel Document..."); }
    }

    public static abstract class DocumentFactory {
        public abstract Document createDocument();
    }

    public static class WordDocumentFactory extends DocumentFactory {
        public Document createDocument() { return new WordDocument(); }
    }

    public static class PdfDocumentFactory extends DocumentFactory {
        public Document createDocument() { return new PdfDocument(); }
    }

    public static class ExcelDocumentFactory extends DocumentFactory {
        public Document createDocument() { return new ExcelDocument(); }
    }

    // ==========================================
    // Exercise 3: Builder Pattern
    // ==========================================
    public static class Computer {
        private String CPU;
        private String RAM;
        private String storage;
        private boolean isGraphicsCardEnabled;
        private boolean isBluetoothEnabled;

        private Computer(Builder builder) {
            this.CPU = builder.CPU;
            this.RAM = builder.RAM;
            this.storage = builder.storage;
            this.isGraphicsCardEnabled = builder.isGraphicsCardEnabled;
            this.isBluetoothEnabled = builder.isBluetoothEnabled;
        }

        @Override
        public String toString() {
            return "Computer [CPU=" + CPU + ", RAM=" + RAM + ", Storage=" + storage + 
                   ", GPU=" + isGraphicsCardEnabled + ", Bluetooth=" + isBluetoothEnabled + "]";
        }

        public static class Builder {
            private String CPU;
            private String RAM;
            private String storage;
            private boolean isGraphicsCardEnabled;
            private boolean isBluetoothEnabled;

            public Builder(String CPU, String RAM) {
                this.CPU = CPU;
                this.RAM = RAM;
            }

            public Builder setStorage(String storage) {
                this.storage = storage;
                return this;
            }

            public Builder setGraphicsCardEnabled(boolean isGraphicsCardEnabled) {
                this.isGraphicsCardEnabled = isGraphicsCardEnabled;
                return this;
            }

            public Builder setBluetoothEnabled(boolean isBluetoothEnabled) {
                this.isBluetoothEnabled = isBluetoothEnabled;
                return this;
            }

            public Computer build() {
                return new Computer(this);
            }
        }
    }

    // ==========================================
    // Exercise 4: Adapter Pattern
    // ==========================================
    public interface PaymentProcessor {
        void processPayment(double amount);
    }

    public static class PayPalGateway {
        public void makePayment(double dollars) {
            System.out.println("Processing $" + dollars + " payment via PayPal.");
        }
    }

    public static class StripeGateway {
        public void charge(double amountInUSD) {
            System.out.println("Processing $" + amountInUSD + " payment via Stripe.");
        }
    }

    public static class PayPalAdapter implements PaymentProcessor {
        private PayPalGateway payPalGateway;

        public PayPalAdapter(PayPalGateway payPalGateway) {
            this.payPalGateway = payPalGateway;
        }

        public void processPayment(double amount) {
            payPalGateway.makePayment(amount);
        }
    }

    public static class StripeAdapter implements PaymentProcessor {
        private StripeGateway stripeGateway;

        public StripeAdapter(StripeGateway stripeGateway) {
            this.stripeGateway = stripeGateway;
        }

        public void processPayment(double amount) {
            stripeGateway.charge(amount);
        }
    }

    // ==========================================
    // Exercise 5: Decorator Pattern
    // ==========================================
    public interface Notifier {
        void send(String message);
    }

    public static class EmailNotifier implements Notifier {
        public void send(String message) {
            System.out.println("Sending Email: " + message);
        }
    }

    public static abstract class NotifierDecorator implements Notifier {
        protected Notifier wrappedNotifier;

        public NotifierDecorator(Notifier notifier) {
            this.wrappedNotifier = notifier;
        }

        public void send(String message) {
            wrappedNotifier.send(message);
        }
    }

    public static class SMSNotifierDecorator extends NotifierDecorator {
        public SMSNotifierDecorator(Notifier notifier) { super(notifier); }

        @Override
        public void send(String message) {
            super.send(message);
            System.out.println("Sending SMS: " + message);
        }
    }

    public static class SlackNotifierDecorator extends NotifierDecorator {
        public SlackNotifierDecorator(Notifier notifier) { super(notifier); }

        @Override
        public void send(String message) {
            super.send(message);
            System.out.println("Sending Slack Message: " + message);
        }
    }

    // ==========================================
    // Exercise 6: Proxy Pattern
    // ==========================================
    public interface Image {
        void display();
    }

    public static class RealImage implements Image {
        private String filename;

        public RealImage(String filename) {
            this.filename = filename;
            loadFromRemoteServer();
        }

        private void loadFromRemoteServer() {
            System.out.println("Loading image from remote server: " + filename);
        }

        public void display() {
            System.out.println("Displaying image: " + filename);
        }
    }

    public static class ProxyImage implements Image {
        private RealImage realImage;
        private String filename;

        public ProxyImage(String filename) {
            this.filename = filename;
        }

        public void display() {
            if (realImage == null) {
                realImage = new RealImage(filename);
            } else {
                System.out.println("Using cached image: " + filename);
            }
            realImage.display();
        }
    }

    // ==========================================
    // Exercise 7: Observer Pattern
    // ==========================================
    public interface Observer {
        void update(String stockName, double price);
    }

    public interface Stock {
        void registerObserver(Observer o);
        void deregisterObserver(Observer o);
        void notifyObservers();
    }

    public static class StockMarket implements Stock {
        private List<Observer> observers = new ArrayList<>();
        private String stockName;
        private double price;

        public StockMarket(String stockName, double price) {
            this.stockName = stockName;
            this.price = price;
        }

        public void setPrice(double price) {
            this.price = price;
            notifyObservers();
        }

        public void registerObserver(Observer o) { observers.add(o); }
        public void deregisterObserver(Observer o) { observers.remove(o); }
        public void notifyObservers() {
            for (Observer o : observers) {
                o.update(stockName, price);
            }
        }
    }

    public static class MobileApp implements Observer {
        public void update(String stockName, double price) {
            System.out.println("MobileApp Notification - " + stockName + " price updated to: $" + price);
        }
    }

    public static class WebApp implements Observer {
        public void update(String stockName, double price) {
            System.out.println("WebApp Notification - " + stockName + " price updated to: $" + price);
        }
    }

    // ==========================================
    // Exercise 8: Strategy Pattern
    // ==========================================
    public interface PaymentStrategy {
        void pay(double amount);
    }

    public static class CreditCardPayment implements PaymentStrategy {
        private String cardNumber;
        public CreditCardPayment(String cardNumber) { this.cardNumber = cardNumber; }

        public void pay(double amount) {
            System.out.println("Paid $" + amount + " using Credit Card ending in " + cardNumber.substring(cardNumber.length() - 4));
        }
    }

    public static class PayPalPayment implements PaymentStrategy {
        private String email;
        public PayPalPayment(String email) { this.email = email; }

        public void pay(double amount) {
            System.out.println("Paid $" + amount + " using PayPal account " + email);
        }
    }

    public static class PaymentContext {
        private PaymentStrategy strategy;

        public void setPaymentStrategy(PaymentStrategy strategy) {
            this.strategy = strategy;
        }

        public void executePayment(double amount) {
            if (strategy != null) {
                strategy.pay(amount);
            } else {
                System.out.println("No payment strategy set.");
            }
        }
    }

    // ==========================================
    // Exercise 9: Command Pattern
    // ==========================================
    public interface Command {
        void execute();
    }

    public static class Light {
        public void turnOn() { System.out.println("Light is ON"); }
        public void turnOff() { System.out.println("Light is OFF"); }
    }

    public static class LightOnCommand implements Command {
        private Light light;
        public LightOnCommand(Light light) { this.light = light; }
        public void execute() { light.turnOn(); }
    }

    public static class LightOffCommand implements Command {
        private Light light;
        public LightOffCommand(Light light) { this.light = light; }
        public void execute() { light.turnOff(); }
    }

    public static class RemoteControl {
        private Command command;

        public void setCommand(Command command) {
            this.command = command;
        }

        public void pressButton() {
            if (command != null) command.execute();
        }
    }

    // ==========================================
    // Exercise 10: MVC Pattern
    // ==========================================
    public static class Student {
        private String name;
        private String id;
        private String grade;

        public Student(String name, String id, String grade) {
            this.name = name;
            this.id = id;
            this.grade = grade;
        }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        public String getGrade() { return grade; }
        public void setGrade(String grade) { this.grade = grade; }
    }

    public static class StudentView {
        public void displayStudentDetails(String name, String id, String grade) {
            System.out.println("--- Student Details ---");
            System.out.println("Name: " + name);
            System.out.println("ID: " + id);
            System.out.println("Grade: " + grade);
        }
    }

    public static class StudentController {
        private Student model;
        private StudentView view;

        public StudentController(Student model, StudentView view) {
            this.model = model;
            this.view = view;
        }

        public void setStudentName(String name) { model.setName(name); }
        public String getStudentName() { return model.getName(); }
        public void setStudentGrade(String grade) { model.setGrade(grade); }
        public String getStudentGrade() { return model.getGrade(); }

        public void updateView() {
            view.displayStudentDetails(model.getName(), model.getId(), model.getGrade());
        }
    }

    // ==========================================
    // Exercise 11: Dependency Injection
    // ==========================================
    public interface CustomerRepository {
        String findCustomerById(String id);
    }

    public static class CustomerRepositoryImpl implements CustomerRepository {
        public String findCustomerById(String id) {
            return "Customer object for ID: " + id;
        }
    }

    public static class CustomerService {
        private CustomerRepository repository;

        // Constructor Injection
        public CustomerService(CustomerRepository repository) {
            this.repository = repository;
        }

        public void getCustomerDetails(String id) {
            String customer = repository.findCustomerById(id);
            System.out.println("Service fetched: " + customer);
        }
    }

    // Main Test Execution
    public static void main(String[] args) {
        System.out.println("=== EXERCISE 1: Singleton Pattern ===");
        Logger logger1 = Logger.getInstance();
        Logger logger2 = Logger.getInstance();
        logger1.log("Testing Singleton Pattern");
        System.out.println("Same Instance? " + (logger1 == logger2));

        System.out.println("\n=== EXERCISE 2: Factory Method Pattern ===");
        DocumentFactory docFactory = new PdfDocumentFactory();
        Document doc = docFactory.createDocument();
        doc.open();

        System.out.println("\n=== EXERCISE 3: Builder Pattern ===");
        Computer comp = new Computer.Builder("Intel i7", "16GB")
                .setStorage("512GB SSD")
                .setGraphicsCardEnabled(true)
                .build();
        System.out.println(comp);

        System.out.println("\n=== EXERCISE 4: Adapter Pattern ===");
        PaymentProcessor processor = new PayPalAdapter(new PayPalGateway());
        processor.processPayment(150.0);

        System.out.println("\n=== EXERCISE 5: Decorator Pattern ===");
        Notifier notifier = new SlackNotifierDecorator(new SMSNotifierDecorator(new EmailNotifier()));
        notifier.send("System Maintenance Alert");

        System.out.println("\n=== EXERCISE 6: Proxy Pattern ===");
        Image image = new ProxyImage("sample.png");
        image.display(); // First time: loads and displays
        image.display(); // Second time: cached display

        System.out.println("\n=== EXERCISE 7: Observer Pattern ===");
        StockMarket techStock = new StockMarket("AAPL", 150.0);
        techStock.registerObserver(new MobileApp());
        techStock.registerObserver(new WebApp());
        techStock.setPrice(155.5);

        System.out.println("\n=== EXERCISE 8: Strategy Pattern ===");
        PaymentContext payCtx = new PaymentContext();
        payCtx.setPaymentStrategy(new CreditCardPayment("1234567890123456"));
        payCtx.executePayment(250.00);

        System.out.println("\n=== EXERCISE 9: Command Pattern ===");
        Light livingRoomLight = new Light();
        RemoteControl remote = new RemoteControl();
        remote.setCommand(new LightOnCommand(livingRoomLight));
        remote.pressButton();

        System.out.println("\n=== EXERCISE 10: MVC Pattern ===");
        Student student = new Student("Alice", "S1001", "A");
        StudentView view = new StudentView();
        StudentController controller = new StudentController(student, view);
        controller.updateView();
        controller.setStudentGrade("A+");
        controller.updateView();

        System.out.println("\n=== EXERCISE 11: Dependency Injection ===");
        CustomerRepository repo = new CustomerRepositoryImpl();
        CustomerService service = new CustomerService(repo);
        service.getCustomerDetails("C101");
    }
}
