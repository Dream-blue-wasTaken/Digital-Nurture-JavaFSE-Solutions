/**
 * Exercise 7: Financial Forecasting
 * 
 * Scenario: Develop a recursive algorithm for financial prediction
 * and discuss optimization techniques to address recursive computation overhead.
 */

public class Financial_Forecasting_Answer {
    
    /**
     * Recursive method to calculate future value using compound interest formula
     * FV = PV * (1 + rate)^n
     * 
     * @param presentValue Initial investment amount
     * @param growthRate Annual growth rate (as decimal, e.g., 0.05 for 5%)
     * @param periods Number of periods (years)
     * @return Future value after specified periods
     */
    public static double calculateFutureValueRecursive(double presentValue, double growthRate, int periods) {
        // Base case: no more periods, return the present value
        if (periods == 0) {
            return presentValue;
        }
        // Recursive case: apply growth for one period and recurse for remaining periods
        return calculateFutureValueRecursive(presentValue * (1 + growthRate), growthRate, periods - 1);
    }
    
    /**
     * Optimized iterative version (avoids recursion overhead)
     */
    public static double calculateFutureValueIterative(double presentValue, double growthRate, int periods) {
        double futureValue = presentValue;
        for (int i = 0; i < periods; i++) {
            futureValue *= (1 + growthRate);
        }
        return futureValue;
    }
    
    /**
     * Optimized version using mathematical formula (most efficient)
     */
    public static double calculateFutureValueFormula(double presentValue, double growthRate, int periods) {
        return presentValue * Math.pow(1 + growthRate, periods);
    }
    
    /**
     * Recursive with memoization for repeated calculations
     */
    public static double calculateFutureValueMemo(double presentValue, double growthRate, int periods, Double[] memo) {
        if (periods == 0) {
            return presentValue;
        }
        if (memo[periods] != null) {
            return memo[periods];
        }
        memo[periods] = calculateFutureValueMemo(presentValue * (1 + growthRate), growthRate, periods - 1, memo);
        return memo[periods];
    }
    
    /**
     * Recursive prediction for variable growth rates (more realistic)
     */
    public static double calculateVariableGrowthRecursive(double presentValue, double[] growthRates, int index) {
        if (index >= growthRates.length) {
            return presentValue;
        }
        return calculateVariableGrowthRecursive(presentValue * (1 + growthRates[index]), growthRates, index + 1);
    }
    
    /**
     * Tail-recursive approach (optimized by compiler in some languages)
     */
    public static double calculateFutureValueTailRecursive(double presentValue, double growthRate, int periods) {
        return tailRecursiveHelper(presentValue, growthRate, periods);
    }
    
    private static double tailRecursiveHelper(double currentValue, double growthRate, int remainingPeriods) {
        if (remainingPeriods == 0) {
            return currentValue;
        }
        return tailRecursiveHelper(currentValue * (1 + growthRate), growthRate, remainingPeriods - 1);
    }
    
    public static void main(String[] args) {
        double initialInvestment = 10000.0;
        double annualReturn = 0.08; // 8% annual return
        int years = 20;
        
        System.out.println("=== Financial Forecasting ===");
        System.out.println("Initial Investment: $" + initialInvestment);
        System.out.println("Annual Growth Rate: " + (annualReturn * 100) + "%");
        System.out.println("Investment Period: " + years + " years\n");
        
        // Test all methods
        long startTime = System.nanoTime();
        double recursiveResult = calculateFutureValueRecursive(initialInvestment, annualReturn, years);
        long recursiveTime = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        double iterativeResult = calculateFutureValueIterative(initialInvestment, annualReturn, years);
        long iterativeTime = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        double formulaResult = calculateFutureValueFormula(initialInvestment, annualReturn, years);
        long formulaTime = System.nanoTime() - startTime;
        
        System.out.println("--- Results ---");
        System.out.printf("Recursive:    $%.2f (Time: %d ns)\n", recursiveResult, recursiveTime);
        System.out.printf("Iterative:    $%.2f (Time: %d ns)\n", iterativeResult, iterativeTime);
        System.out.printf("Formula:      $%.2f (Time: %d ns)\n", formulaResult, formulaTime);
        
        // Variable growth rates example
        System.out.println("\n--- Variable Growth Rates ---");
        double[] yearlyRates = {0.08, 0.07, 0.09, 0.06, 0.10, 0.08, 0.07, 0.09, 0.08, 0.07};
        double varResult = calculateVariableGrowthRecursive(initialInvestment, yearlyRates, 0);
        System.out.printf("Investment with variable rates: $%.2f\n", varResult);
        
        System.out.println("\n--- Optimization Techniques ---");
        System.out.println("1. Iterative approach: Avoids recursion overhead entirely");
        System.out.println("2. Mathematical formula: O(1) time using Math.pow()");
        System.out.println("3. Memoization: Cache results to avoid recalculating same values");
        System.out.println("4. Tail recursion: Allows compiler optimization (not in Java by default)");
        System.out.println("5. For large n: Prefer iterative or formula approach");
        System.out.println();
        System.out.println("Recursion overhead includes:");
        System.out.println("  - Function call overhead for each recursive call");
        System.out.println("  - Stack memory usage (O(n) stack frames)");
        System.out.println("  - Risk of StackOverflowError for very large inputs");
    }
}
