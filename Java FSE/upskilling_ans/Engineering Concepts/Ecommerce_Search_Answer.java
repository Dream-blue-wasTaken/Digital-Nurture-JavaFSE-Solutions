/**
 * Exercise 2: E-commerce Platform Search Function
 * 
 * Scenario: Implement linear and binary search algorithms for an 
 * e-commerce platform's product search functionality.
 * Explain Big O notation and compare algorithm performance.
 */

import java.util.Arrays;
import java.util.Scanner;

public class Ecommerce_Search_Answer {
    
    /**
     * Linear Search - O(n)
     * Best for unsorted data or small datasets
     */
    public static int linearSearch(int[] products, int targetId) {
        for (int i = 0; i < products.length; i++) {
            if (products[i] == targetId) {
                return i; // Found at index i
            }
        }
        return -1; // Not found
    }
    
    /**
     * Binary Search - O(log n)
     * Requires sorted data, much faster for large datasets
     */
    public static int binarySearch(int[] products, int targetId) {
        int left = 0;
        int right = products.length - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (products[mid] == targetId) {
                return mid; // Found at index mid
            }
            
            if (products[mid] < targetId) {
                left = mid + 1; // Search right half
            } else {
                right = mid - 1; // Search left half
            }
        }
        return -1; // Not found
    }
    
    /**
     * Recursive Binary Search
     */
    public static int binarySearchRecursive(int[] products, int left, int right, int targetId) {
        if (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (products[mid] == targetId) {
                return mid;
            }
            
            if (products[mid] < targetId) {
                return binarySearchRecursive(products, mid + 1, right, targetId);
            }
            
            return binarySearchRecursive(products, left, mid - 1, targetId);
        }
        return -1;
    }
    
    /**
     * Product search with product name (String search)
     */
    public static int linearSearchProducts(String[] productNames, String targetName) {
        for (int i = 0; i < productNames.length; i++) {
            if (productNames[i].equalsIgnoreCase(targetName)) {
                return i;
            }
        }
        return -1;
    }
    
    public static void main(String[] args) {
        // Sample product IDs (sorted for binary search)
        int[] productIds = {101, 205, 308, 412, 523, 634, 745, 856, 967, 1078};
        String[] productNames = {"Laptop", "Phone", "Tablet", "Headphones", 
                                  "Keyboard", "Mouse", "Monitor", "Printer", 
                                  "Webcam", "Speaker"};
        
        System.out.println("=== E-commerce Product Search ===");
        System.out.println("Product IDs: " + Arrays.toString(productIds));
        System.out.println("Product Names: " + Arrays.toString(productNames));
        System.out.println();
        
        // Test Linear Search
        int searchId = 523;
        System.out.println("--- Linear Search for Product ID: " + searchId + " ---");
        int linearResult = linearSearch(productIds, searchId);
        if (linearResult != -1) {
            System.out.println("Found at index: " + linearResult + 
                             " -> " + productNames[linearResult]);
        } else {
            System.out.println("Product not found");
        }
        
        // Test Binary Search
        System.out.println("\n--- Binary Search for Product ID: " + searchId + " ---");
        int binaryResult = binarySearch(productIds, searchId);
        if (binaryResult != -1) {
            System.out.println("Found at index: " + binaryResult + 
                             " -> " + productNames[binaryResult]);
        } else {
            System.out.println("Product not found");
        }
        
        // Test search not found case
        int notFoundId = 999;
        System.out.println("\n--- Searching for non-existent ID: " + notFoundId + " ---");
        System.out.println("Linear Search: " + linearSearch(productIds, notFoundId) + " (not found)");
        System.out.println("Binary Search: " + binarySearch(productIds, notFoundId) + " (not found)");
        
        // String search
        System.out.println("\n--- String Search for 'Monitor' ---");
        int strResult = linearSearchProducts(productNames, "Monitor");
        System.out.println("Found at index: " + strResult);
        
        // Performance comparison
        System.out.println("\n--- Big O Analysis ---");
        System.out.println("Linear Search: O(n) - Time grows linearly with input size");
        System.out.println("  - Best case: O(1) - Element is at first position");
        System.out.println("  - Worst case: O(n) - Element is at last position or not found");
        System.out.println("  - Unsorted data: Only linear search works");
        System.out.println();
        System.out.println("Binary Search: O(log n) - Time grows logarithmically");
        System.out.println("  - Best case: O(1) - Element is at middle position");
        System.out.println("  - Worst case: O(log n) - Element found after log n divisions");
        System.out.println("  - Requirement: Data must be SORTED first");
        System.out.println();
        System.out.println("For n=1,000,000 elements:");
        System.out.println("  - Linear Search: up to 1,000,000 comparisons");
        System.out.println("  - Binary Search: only ~20 comparisons");
    }
}
