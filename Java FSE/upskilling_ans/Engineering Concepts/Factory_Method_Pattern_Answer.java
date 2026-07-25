/**
 * Exercise 2: Implementing the Factory Method Pattern
 * 
 * Scenario: Implement a document management system that can create 
 * different types of documents (Word, PDF, Excel) via factory methods.
 */

// Abstract Product: Document
abstract class Document {
    protected String name;
    protected String content;
    
    public Document(String name, String content) {
        this.name = name;
        this.content = content;
    }
    
    public abstract void open();
    public abstract void save();
    public abstract void display();
    
    public String getName() {
        return name;
    }
}

// Concrete Product: WordDocument
class WordDocument extends Document {
    public WordDocument(String name, String content) {
        super(name, content);
    }
    
    @Override
    public void open() {
        System.out.println("Opening Word document: " + name + ".docx");
    }
    
    @Override
    public void save() {
        System.out.println("Saving Word document: " + name + ".docx");
    }
    
    @Override
    public void display() {
        System.out.println("Displaying Word Document: " + name);
        System.out.println("Content: " + content);
    }
}

// Concrete Product: PDFDocument
class PDFDocument extends Document {
    public PDFDocument(String name, String content) {
        super(name, content);
    }
    
    @Override
    public void open() {
        System.out.println("Opening PDF document: " + name + ".pdf");
    }
    
    @Override
    public void save() {
        System.out.println("Saving PDF document: " + name + ".pdf");
    }
    
    @Override
    public void display() {
        System.out.println("Displaying PDF Document: " + name);
        System.out.println("Content: " + content);
    }
}

// Concrete Product: ExcelDocument
class ExcelDocument extends Document {
    public ExcelDocument(String name, String content) {
        super(name, content);
    }
    
    @Override
    public void open() {
        System.out.println("Opening Excel document: " + name + ".xlsx");
    }
    
    @Override
    public void save() {
        System.out.println("Saving Excel document: " + name + ".xlsx");
    }
    
    @Override
    public void display() {
        System.out.println("Displaying Excel Document: " + name);
        System.out.println("Content: " + content);
    }
}

// Creator (Factory): DocumentFactory
abstract class DocumentFactory {
    public abstract Document createDocument(String name, String content);
    
    // Template method that uses the factory method
    public Document createAndProcessDocument(String name, String content) {
        Document doc = createDocument(name, content);
        doc.open();
        doc.display();
        doc.save();
        return doc;
    }
}

// Concrete Creator: WordDocumentFactory
class WordDocumentFactory extends DocumentFactory {
    @Override
    public Document createDocument(String name, String content) {
        return new WordDocument(name, content);
    }
}

// Concrete Creator: PDFDocumentFactory
class PDFDocumentFactory extends DocumentFactory {
    @Override
    public Document createDocument(String name, String content) {
        return new PDFDocument(name, content);
    }
}

// Concrete Creator: ExcelDocumentFactory
class ExcelDocumentFactory extends DocumentFactory {
    @Override
    public Document createDocument(String name, String content) {
        return new ExcelDocument(name, content);
    }
}

// Test the Factory Method Pattern
public class Factory_Method_Pattern_Answer {
    public static void main(String[] args) {
        // Create factories
        DocumentFactory wordFactory = new WordDocumentFactory();
        DocumentFactory pdfFactory = new PDFDocumentFactory();
        DocumentFactory excelFactory = new ExcelDocumentFactory();
        
        // Create documents using factories
        System.out.println("=== Creating Word Document ===");
        Document wordDoc = wordFactory.createAndProcessDocument("Report", "Annual Report 2026");
        
        System.out.println("\n=== Creating PDF Document ===");
        Document pdfDoc = pdfFactory.createAndProcessDocument("Invoice", "Invoice #12345");
        
        System.out.println("\n=== Creating Excel Document ===");
        Document excelDoc = excelFactory.createAndProcessDocument("Budget", "2026 Budget Forecast");
    }
}
