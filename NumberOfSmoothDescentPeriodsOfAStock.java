// You are given an integer array prices representing the daily price history of a stock, where prices[i] is the stock price on the ith day.

// A smooth descent period of a stock consists of one or more contiguous days such that the price on each day is lower than the price on the preceding day by exactly 1. The first day of the period is exempted from this rule.

// Return the number of smooth descent periods.

 

// Example 1:

// Input: prices = [3,2,1,4]
// Output: 7
// Explanation: There are 7 smooth descent periods:
// [3], [2], [1], [4], [3,2], [2,1], and [3,2,1]
// Note that a period with one day is a smooth descent period by the definition.
// Example 2:

// Input: prices = [8,6,7,7]
// Output: 4
// Explanation: There are 4 smooth descent periods: [8], [6], [7], and [7]
// Note that [8,6] is not a smooth descent period as 8 - 6 ≠ 1.
// Example 3:

// Input: prices = [1]
// Output: 1
// Explanation: There is 1 smooth descent period: [1]
 

// Constraints:

// 1 <= prices.length <= 105
// 1 <= prices[i] <= 105


/**
 * Problem: Number of Smooth Descent Periods of a Stock
 *
 * Time Complexity: O(n)
 * - We traverse the prices array exactly once.
 *
 * Space Complexity: O(1)
 * - Only constant extra variables are used.
 */
class NumberOfSmoothDescentPeriodsOfAStock {

    /**
     * Returns total number of smooth descent periods
     */
    public static long getDescentPeriods(int[] prices) {

        int n = prices.length;

        // period -> length of current smooth descent sequence
        // total  -> total count of valid descent periods
        long period = 1;   // O(1) space
        long total = 1;    // O(1) space

        // Loop runs (n - 1) times → O(n) time
        for (int i = 1; i < n; i++) {

            // Constant time comparison → O(1)
            if (prices[i - 1] - 1 == prices[i]) {
                period++;      // O(1)
            } 
            else {
                period = 1;    // O(1)
            }

            // Constant time addition → O(1)
            total += period;
        }

        // Overall time: O(n)
        // Overall space: O(1)
        return total;
    }

    /**
     * Main method for testing
     */
    public static void main(String[] args) {

        int[] prices = {3, 2, 1, 4};

        // Single function call → O(n)
        long result = getDescentPeriods(prices);

        System.out.println("Number of smooth descent periods: " + result);
    }
}
