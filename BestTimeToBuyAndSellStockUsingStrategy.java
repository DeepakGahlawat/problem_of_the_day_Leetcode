/*
    =====================================================================
        BEST TIME TO BUY AND SELL STOCK USING STRATEGY
    =====================================================================

    🧠 INTUITION
    ---------------------------------------------------------------------
    We are given:
        - prices[i]   → stock price on day i
        - strategy[i] → either +1 (profit) or -1 (loss)
        - k           → length of a special window

    Initially, the total profit is calculated as:
        sum(prices[i] * strategy[i]) for all i

    We are allowed to choose ONE contiguous window of length k
    and APPLY A DIFFERENT STRATEGY on it to maximize total profit.

    The trick is to efficiently recompute profit if we replace
    the strategy effect on a window of size k.

    ---------------------------------------------------------------------
    🧩 APPROACH (PREFIX SUM OPTIMIZATION)
    ---------------------------------------------------------------------
    1️⃣ Compute prefix sum of:
         - prices            → preSumPrices[]
         - prices * strategy → preSum[]

    2️⃣ Initial total profit = preSum[n]

    3️⃣ Slide a window of size k:
         - Remove old contribution of that window
         - Add new optimized contribution using prices prefix sum
         - Track maximum possible profit

    Prefix sums allow each window calculation in O(1) time.

    ---------------------------------------------------------------------
    ⏱️ TIME & SPACE COMPLEXITY
    ---------------------------------------------------------------------
    Time Complexity:
        O(n)

    Space Complexity:
        O(n)

    ---------------------------------------------------------------------
    ✔ Efficient linear-time solution using prefix sums
    =====================================================================
*/

import java.util.*;

public class BestTimeToBuyAndSellStockUsingStrategy {

    public long maxProfit(int[] prices, int[] strategy, int k) {

        int n = prices.length;

        // Prefix sums for prices
        long[] preSumPrices = new long[n + 1];

        // Prefix sums for prices * strategy
        long[] preSum = new long[n + 1];

        // Build prefix sums
        for (int i = 0; i < n; i++) {
            preSumPrices[i + 1] = prices[i] + preSumPrices[i];
            preSum[i + 1] = prices[i] * strategy[i] + preSum[i];
        }

        // Initial total profit without any modification
        long result = preSum[n];
        long total = preSum[n];

        // Sliding window of size k
        for (int i = 0; i <= n - k; i++) {

            // Remove old strategy contribution for window
            long remove = preSum[i + k] - preSum[i];

            // Add new optimized contribution
            long add = preSumPrices[i + k] - preSumPrices[i + k / 2];

            result = Math.max(result, total - remove + add);
        }

        return result;
    }

    // ============================================================
    //                         MAIN METHOD
    // ============================================================
    public static void main(String[] args) {

        BestTimeToBuyAndSellStockUsingStrategy solver =
                new BestTimeToBuyAndSellStockUsingStrategy();

        int[] prices = {5, 3, 6, 2, 4};
        int[] strategy = {1, -1, 1, -1, 1};
        int k = 3;

        System.out.println(
                "Maximum Profit = " +
                        solver.maxProfit(prices, strategy, k)
        );
    }
}
