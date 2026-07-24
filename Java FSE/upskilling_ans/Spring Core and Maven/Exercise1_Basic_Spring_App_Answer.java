// ============================================================
// Spring Core & Maven - Exercise 1: Configuring a Basic Spring App
// Exercise 2: Implementing Dependency Injection
// Exercise 4: Creating/Configuring Maven Project
// ============================================================

// ===== pom.xml =====
/*
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
         http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    
    <groupId>com.cognizant</groupId>
    <artifactId>library-management</artifactId>
    <version>1.0.0</version>
    <packaging>jar</packaging>
    
    <name>Library Management System</name>
    <description>Spring Core Library Management Application</description>
    
    <properties>
        <maven.compiler.source>1.8</maven.compiler.source>
        <maven.compiler.target>1.8</maven.compiler.target>
        <spring.version>5.3.30</spring.version>
    </properties>
    
    <dependencies>
        <!-- Spring Core -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>${spring.version}</version>
        </dependency>
        
        <!-- JUnit for testing -->
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-api</artifactId>
            <version>5.10.0</version>
            <scope>test</scope>
        </dependency>
    </dependencies>
    
    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.11.0</version>
                <configuration>
                    <source>1.8</source>
                    <target>1.8</target>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
*/

// ===== Model Classes =====

// Book.java
package com.cognizant.library.model;

public class Book {
    private int id;
    private String title;
    private String author;
    private String isbn;
    
    public Book() {}
    
    public Book(int id, String title, String author, String isbn) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
    }
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    
    @Override
    public String toString() {
        return "Book{id=" + id + ", title='" + title + "', author='" + author + "', isbn='" + isbn + "'}";
    }
}

// ===== Repository Layer =====

// BookRepository.java
package com.cognizant.library.repository;

import com.cognizant.library.model.Book;
import java.util.*;

public class BookRepository {
    
    private Map<Integer, Book> bookMap = new HashMap<>();
    
    public BookRepository() {
        // Initialize with some books
        bookMap.put(1, new Book(1, "The Great Gatsby", "F. Scott Fitzgerald", "9780743273565"));
        bookMap.put(2, new Book(2, "To Kill a Mockingbird", "Harper Lee", "9780061120084"));
        bookMap.put(3, new Book(3, "1984", "George Orwell", "9780451524935"));
    }
    
    public Book findById(int id) {
        return bookMap.get(id);
    }
    
    public List<Book> findAll() {
        return new ArrayList<>(bookMap.values());
    }
    
    public void save(Book book) {
        bookMap.put(book.getId(), book);
    }
    
    public boolean delete(int id) {
        return bookMap.remove(id) != null;
    }
}

// ===== Service Layer =====

// BookService.java
package com.cognizant.library.service;

import com.cognizant.library.model.Book;
import com.cognizant.library.repository.BookRepository;
import java.util.List;

public class BookService {
    
    private BookRepository bookRepository;
    
    // Constructor for Dependency Injection (Exercise 2)
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    
    // Setter for Setter Injection (Exercise 2)
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    
    public Book getBookById(int id) {
        return bookRepository.findById(id);
    }
    
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
    
    public void addBook(Book book) {
        bookRepository.save(book);
        System.out.println("Book added: " + book.getTitle());
    }
    
    public void removeBook(int id) {
        if (bookRepository.delete(id)) {
            System.out.println("Book removed with ID: " + id);
        } else {
            System.out.println("Book not found with ID: " + id);
        }
    }
}

// ===== XML Configuration (applicationContext.xml) =====
/*
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans 
       http://www.springframework.org/schema/beans/spring-beans.xsd">
    
    <!-- Exercise 1: Configure beans -->
    <bean id="bookRepository" class="com.cognizant.library.repository.BookRepository"/>
    
    <!-- Exercise 2: Constructor Injection -->
    <bean id="bookService" class="com.cognizant.library.service.BookService">
        <constructor-arg ref="bookRepository"/>
    </bean>
    
</beans>
*/

// ===== Main Application =====

// LibraryApp.java
package com.cognizant.library;

import com.cognizant.library.model.Book;
import com.cognizant.library.service.BookService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class LibraryApp {
    public static void main(String[] args) {
        // Exercise 1: Load Spring context from XML
        System.out.println("=== Library Management System ===");
        System.out.println("Loading Spring Application Context...\n");
        
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        
        // Exercise 2: Retrieve beans from the IoC container
        BookService bookService = context.getBean("bookService", BookService.class);
        
        // Test the application
        System.out.println("--- All Books ---");
        bookService.getAllBooks().forEach(System.out::println);
        
        System.out.println("\n--- Get Book by ID ---");
        Book book = bookService.getBookById(1);
        System.out.println("Found: " + book);
        
        System.out.println("\n--- Add New Book ---");
        bookService.addBook(new Book(4, "Pride and Prejudice", "Jane Austen", "9780141439518"));
        
        System.out.println("\n--- Remove Book ---");
        bookService.removeBook(3);
        
        System.out.println("\n--- Updated Book List ---");
        bookService.getAllBooks().forEach(System.out::println);
        
        ((ClassPathXmlApplicationContext) context).close();
    }
}

// ============================================================
// Exercise 2: Constructor and Setter Injection
// ============================================================

// applicationContext-injection.xml
/*
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans 
       http://www.springframework.org/schema/beans/spring-beans.xsd">
    
    <!-- Repository bean -->
    <bean id="bookRepository" class="com.cognizant.library.repository.BookRepository"/>
    
    <!-- Constructor Injection -->
    <bean id="bookServiceByConstructor" class="com.cognizant.library.service.BookService">
        <constructor-arg ref="bookRepository"/>
    </bean>
    
    <!-- Setter Injection -->
    <bean id="bookServiceBySetter" class="com.cognizant.library.service.BookService">
        <property name="bookRepository" ref="bookRepository"/>
    </bean>
    
</beans>
*/

// ============================================================
// Exercise 4: Maven Project Structure
// ============================================================

/*
Project Structure:
library-management/
|-- pom.xml
|-- src/
|   |-- main/
|   |   |-- java/
|   |   |   |-- com/
|   |   |       |-- cognizant/
|   |   |           |-- library/
|   |   |               |-- LibraryApp.java
|   |   |               |-- model/
|   |   |               |   |-- Book.java
|   |   |               |-- repository/
|   |   |               |   |-- BookRepository.java
|   |   |               |-- service/
|   |   |                   |-- BookService.java
|   |   |-- resources/
|   |       |-- applicationContext.xml
|   |-- test/
|       |-- java/
|           |-- com/
|               |-- cognizant/
|                   |-- library/
|                       |-- service/
|                           |-- BookServiceTest.java

Maven Commands:
mvn clean           - Clean the project
mvn compile         - Compile the project
mvn test            - Run tests
mvn package         - Package as JAR
mvn install         - Install to local repository
*/
