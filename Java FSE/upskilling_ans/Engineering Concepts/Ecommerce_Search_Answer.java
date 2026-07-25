import java.util.Arrays;
import java.util.Scanner;
public class Ecommerce_Search_Answer {
    public static int linearSearch(int[] products, int targetId) {
        for (int i = 0; i < products.length; i++) {
            if (products[i] == targetId) {
                return i;