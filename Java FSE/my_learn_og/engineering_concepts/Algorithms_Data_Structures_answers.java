import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Exercise 1: Inventory Management System
 * 
 * Scenario:
 * You are developing an inventory management system for a warehouse. Efficient data storage and retrieval are crucial.
 * 
 * 1. Understand the Problem:
 *    - Data structures and algorithms are essential in handling large inventories to ensure fast lookup, insertion, update, and deletion operations.
 *    - Suitable Data Structures: 
 *      - HashMap: Provides O(1) average time complexity for lookup, insert, and delete by Product ID.
 *      - ArrayList / Dynamic Array: Good for index-based sequential access, but search/delete by ID takes O(n).
 * 
 * 2. Setup & Implementation:
 *    - Product class with productId, productName, quantity, price.
 *    - InventoryManagementSystem class managing products with HashMap for O(1) efficiency.
 * 
 * 3. Analysis:
 *    - Add Product: O(1) average time complexity using HashMap.
 *    - Update Product: O(1) average time complexity using HashMap.
 *    - Delete Product: O(1) average time complexity using HashMap.
 *    - Optimization: Using HashMap ensures O(1) operations instead of O(n) linear search over lists.
 */
public class Algorithms_Data_Structures_answers {

    // Product Model
    public static class Product {
        private String productId;
        private String productName;
        private int quantity;
        private double price;

        public Product(String productId, String productName, int quantity, double price) {
            this.productId = productId;
            this.productName = productName;
            this.quantity = quantity;
            this.price = price;
        }

        public String getProductId() { return productId; }
        public void setProductId(String productId) { this.productId = productId; }

        public String getProductName() { return productName; }
        public void setProductName(String productName) { this.productName = productName; }

        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }

        public double getPrice() { return price; }
        public void setPrice(double price) { this.price = price; }

        @Override
        public String toString() {
            return "Product{id='" + productId + "', name='" + productName + "', quantity=" + quantity + ", price=$" + price + "}";
        }
    }

    // Exercise 1: Inventory Management System
    public static class InventoryManager {
        private Map<String, Product> inventory = new HashMap<>();

        public void addProduct(Product product) {
            inventory.put(product.getProductId(), product);
            System.out.println("Added: " + product);
        }

        public void updateProduct(String productId, int quantity, double price) {
            Product product = inventory.get(productId);
            if (product != null) {
                product.setQuantity(quantity);
                product.setPrice(price);
                System.out.println("Updated: " + product);
            } else {
                System.out.println("Product not found: " + productId);
            }
        }

        public void deleteProduct(String productId) {
            Product removed = inventory.remove(productId);
            if (removed != null) {
                System.out.println("Deleted: " + removed);
            } else {
                System.out.println("Product not found to delete: " + productId);
            }
        }

        public Product getProduct(String productId) {
            return inventory.get(productId);
        }
    }

    // Exercise 2: E-commerce Platform Search Function
    public static class SearchEngine {
        public static Product linearSearch(Product[] products, String targetId) {
            for (Product p : products) {
                if (p.getProductId().equalsIgnoreCase(targetId)) {
                    return p;
                }
            }
            return null;
        }

        public static Product binarySearch(Product[] sortedProducts, String targetId) {
            int left = 0;
            int right = sortedProducts.length - 1;
            while (left <= right) {
                int mid = left + (right - left) / 2;
                int cmp = sortedProducts[mid].getProductId().compareToIgnoreCase(targetId);
                if (cmp == 0) {
                    return sortedProducts[mid];
                } else if (cmp < 0) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
            return null;
        }
    }

    // Exercise 3: Sorting Customer Orders
    public static class Order {
        private String orderId;
        private String customerName;
        private double totalPrice;

        public Order(String orderId, String customerName, double totalPrice) {
            this.orderId = orderId;
            this.customerName = customerName;
            this.totalPrice = totalPrice;
        }

        public double getTotalPrice() { return totalPrice; }

        @Override
        public String toString() {
            return "Order{id='" + orderId + "', customer='" + customerName + "', price=$" + totalPrice + "}";
        }

        public static void bubbleSort(Order[] orders) {
            int n = orders.length;
            for (int i = 0; i < n - 1; i++) {
                for (int j = 0; j < n - i - 1; j++) {
                    if (orders[j].getTotalPrice() > orders[j + 1].getTotalPrice()) {
                        Order temp = orders[j];
                        orders[j] = orders[j + 1];
                        orders[j + 1] = temp;
                    }
                }
            }
        }

        public static void quickSort(Order[] orders, int low, int high) {
            if (low < high) {
                int pi = partition(orders, low, high);
                quickSort(orders, low, pi - 1);
                quickSort(orders, pi + 1, high);
            }
        }

        private static int partition(Order[] orders, int low, int high) {
            double pivot = orders[high].getTotalPrice();
            int i = (low - 1);
            for (int j = low; j < high; j++) {
                if (orders[j].getTotalPrice() <= pivot) {
                    i++;
                    Order temp = orders[i];
                    orders[i] = orders[j];
                    orders[j] = temp;
                }
            }
            Order temp = orders[i + 1];
            orders[i + 1] = orders[high];
            orders[high] = temp;
            return i + 1;
        }
    }

    // Exercise 4: Employee Management System
    public static class Employee {
        private String employeeId;
        private String name;
        private String position;
        private double salary;

        public Employee(String employeeId, String name, String position, double salary) {
            this.employeeId = employeeId;
            this.name = name;
            this.position = position;
            this.salary = salary;
        }

        public String getEmployeeId() { return employeeId; }

        @Override
        public String toString() {
            return "Employee{id='" + employeeId + "', name='" + name + "', position='" + position + "', salary=$" + salary + "}";
        }
    }

    public static class EmployeeManagement {
        private Employee[] employees;
        private int count;

        public EmployeeManagement(int capacity) {
            employees = new Employee[capacity];
            count = 0;
        }

        public void addEmployee(Employee emp) {
            if (count < employees.length) {
                employees[count++] = emp;
                System.out.println("Employee Added: " + emp);
            } else {
                System.out.println("Array Full!");
            }
        }

        public Employee searchEmployee(String id) {
            for (int i = 0; i < count; i++) {
                if (employees[i].getEmployeeId().equals(id)) {
                    return employees[i];
                }
            }
            return null;
        }

        public void traverseEmployees() {
            System.out.println("--- Employee List ---");
            for (int i = 0; i < count; i++) {
                System.out.println(employees[i]);
            }
        }

        public void deleteEmployee(String id) {
            int index = -1;
            for (int i = 0; i < count; i++) {
                if (employees[i].getEmployeeId().equals(id)) {
                    index = i;
                    break;
                }
            }
            if (index != -1) {
                for (int i = index; i < count - 1; i++) {
                    employees[i] = employees[i + 1];
                }
                employees[--count] = null;
                System.out.println("Employee " + id + " deleted.");
            } else {
                System.out.println("Employee not found.");
            }
        }
    }

    // Exercise 5: Task Management System (Singly Linked List)
    public static class Task {
        private String taskId;
        private String taskName;
        private String status;

        public Task(String taskId, String taskName, String status) {
            this.taskId = taskId;
            this.taskName = taskName;
            this.status = status;
        }

        public String getTaskId() { return taskId; }

        @Override
        public String toString() {
            return "Task{id='" + taskId + "', name='" + taskName + "', status='" + status + "'}";
        }
    }

    public static class TaskLinkedList {
        private static class Node {
            Task task;
            Node next;
            Node(Task task) { this.task = task; }
        }

        private Node head;

        public void addTask(Task task) {
            Node newNode = new Node(task);
            if (head == null) {
                head = newNode;
            } else {
                Node current = head;
                while (current.next != null) {
                    current = current.next;
                }
                current.next = newNode;
            }
            System.out.println("Task Added: " + task);
        }

        public Task searchTask(String taskId) {
            Node current = head;
            while (current != null) {
                if (current.task.getTaskId().equals(taskId)) {
                    return current.task;
                }
                current = current.next;
            }
            return null;
        }

        public void traverseTasks() {
            System.out.println("--- Task List ---");
            Node current = head;
            while (current != null) {
                System.out.println(current.task);
                current = current.next;
            }
        }

        public void deleteTask(String taskId) {
            if (head == null) return;
            if (head.task.getTaskId().equals(taskId)) {
                head = head.next;
                System.out.println("Task " + taskId + " deleted.");
                return;
            }
            Node current = head;
            while (current.next != null && !current.next.task.getTaskId().equals(taskId)) {
                current = current.next;
            }
            if (current.next != null) {
                current.next = current.next.next;
                System.out.println("Task " + taskId + " deleted.");
            } else {
                System.out.println("Task not found: " + taskId);
            }
        }
    }

    // Exercise 6: Library Management System
    public static class Book {
        private String bookId;
        private String title;
        private String author;

        public Book(String bookId, String title, String author) {
            this.bookId = bookId;
            this.title = title;
            this.author = author;
        }

        public String getTitle() { return title; }

        @Override
        public String toString() {
            return "Book{id='" + bookId + "', title='" + title + "', author='" + author + "'}";
        }

        public static Book linearSearchByTitle(Book[] books, String title) {
            for (Book b : books) {
                if (b.getTitle().equalsIgnoreCase(title)) {
                    return b;
                }
            }
            return null;
        }

        public static Book binarySearchByTitle(Book[] sortedBooks, String title) {
            int left = 0;
            int right = sortedBooks.length - 1;
            while (left <= right) {
                int mid = left + (right - left) / 2;
                int cmp = sortedBooks[mid].getTitle().compareToIgnoreCase(title);
                if (cmp == 0) return sortedBooks[mid];
                else if (cmp < 0) left = mid + 1;
                else right = mid - 1;
            }
            return null;
        }
    }

    // Exercise 7: Financial Forecasting
    public static class FinancialForecasting {
        public static double predictFutureValue(double presentValue, double growthRate, int periods) {
            if (periods == 0) {
                return presentValue;
            }
            return (1 + growthRate) * predictFutureValue(presentValue, growthRate, periods - 1);
        }
    }

    // Main Test Execution
    public static void main(String[] args) {
        System.out.println("=== EXERCISE 1: Inventory Management System ===");
        InventoryManager invManager = new InventoryManager();
        invManager.addProduct(new Product("P101", "Laptop", 10, 999.99));
        invManager.addProduct(new Product("P102", "Mouse", 50, 25.50));
        invManager.updateProduct("P102", 45, 22.00);
        invManager.deleteProduct("P101");

        System.out.println("\n=== EXERCISE 2: Search Function ===");
        Product[] products = {
            new Product("P101", "Keyboard", 15, 45.00),
            new Product("P102", "Monitor", 8, 200.00),
            new Product("P103", "Speaker", 25, 60.00)
        };
        System.out.println("Linear Search Found: " + SearchEngine.linearSearch(products, "P102"));
        System.out.println("Binary Search Found: " + SearchEngine.binarySearch(products, "P103"));

        System.out.println("\n=== EXERCISE 3: Sorting Orders ===");
        Order[] orders = {
            new Order("O1", "Alice", 350.0),
            new Order("O2", "Bob", 120.0),
            new Order("O3", "Charlie", 500.0)
        };
        Order.quickSort(orders, 0, orders.length - 1);
        for (Order o : orders) System.out.println(o);

        System.out.println("\n=== EXERCISE 4: Employee Management System ===");
        EmployeeManagement empManager = new EmployeeManagement(5);
        empManager.addEmployee(new Employee("E1", "John", "Developer", 75000));
        empManager.addEmployee(new Employee("E2", "Jane", "Manager", 90000));
        empManager.traverseEmployees();
        empManager.deleteEmployee("E1");

        System.out.println("\n=== EXERCISE 5: Task Management System ===");
        TaskLinkedList taskList = new TaskLinkedList();
        taskList.addTask(new Task("T1", "Design DB", "In Progress"));
        taskList.addTask(new Task("T2", "Implement API", "Pending"));
        taskList.traverseTasks();
        taskList.deleteTask("T1");

        System.out.println("\n=== EXERCISE 6: Library Management System ===");
        Book[] books = {
            new Book("B1", "Algorithms", "CLRS"),
            new Book("B2", "Clean Code", "Robert C. Martin"),
            new Book("B3", "Design Patterns", "GoF")
        };
        System.out.println("Linear Search Book: " + Book.linearSearchByTitle(books, "Clean Code"));

        System.out.println("\n=== EXERCISE 7: Financial Forecasting ===");
        double initialValue = 1000.0;
        double growthRate = 0.05; // 5% per period
        int periods = 5;
        double futureValue = FinancialForecasting.predictFutureValue(initialValue, growthRate, periods);
        System.out.println("Predicted Future Value after " + periods + " periods: $" + String.format("%.2f", futureValue));
    }
}
