/*
    =====================================================================
            BEST TIME TO BUY AND SELL STOCK – V
    =====================================================================

    🧠 INTUITION
    ---------------------------------------------------------------------
    This is an advanced stock trading DP problem with:
        - At most k transactions
        - Ability to buy OR sell first (short selling allowed)
        - One active position at a time

    Each transaction is completed only when we return to a
    "no stock" state.

    ---------------------------------------------------------------------
    🧩 STATE DEFINITION
    ---------------------------------------------------------------------
    dp[day][t][state] = maximum profit from 'day' to end

    where:
        day   → current day index
        t     → remaining transactions
        state → current holding state

    state meanings:
        0 → No stock in hand (neutral)
        1 → Holding a BUY position
        2 → Holding a SELL position (short sell)

    ---------------------------------------------------------------------
    🧠 TRANSITIONS
    ---------------------------------------------------------------------
    From state 0 (no stock):
        - Skip → dp[day+1][t][0]
        - Buy  → -price[day] + dp[day+1][t][1]
        - Sell → +price[day] + dp[day+1][t][2]

    From state 1 (holding buy):
        - Skip
        - Sell (complete transaction)
              → +price[day] + dp[day+1][t-1][0]

    From state 2 (holding sell):
        - Skip
        - Buy (complete transaction)
              → -price[day] + dp[day+1][t-1][0]

    ---------------------------------------------------------------------
    🧱 BASE CASE
    ---------------------------------------------------------------------
    When day == n:
        - Valid only if state == 0
        - Otherwise invalid → very small value

    ---------------------------------------------------------------------
    ⏱️ TIME & SPACE COMPLEXITY
    ---------------------------------------------------------------------
    Time Complexity:
        O(n × k × 3)  → O(nk)

    Space Complexity:
        O(n × k × 3)

    ---------------------------------------------------------------------
    ✔ This is a HIGH-LEVEL DP problem
      (Seen in FAANG / advanced OA rounds)
    =====================================================================
*/

import java.util.*;

public class BestTimeToBuyAndSellStockV {

    public long maximumProfit(int[] prices, int k) {

        int n = prices.length;

        // dp[day][transactions][state]
        long[][][] dp = new long[n + 1][k + 1][3];

        // Base case: when day == n
        for (int t = 0; t <= k; t++) {
            dp[n][t][1] = Long.MIN_VALUE / 2; // invalid holding buy
            dp[n][t][2] = Long.MIN_VALUE / 2; // invalid holding sell
        }

        // Fill DP table bottom-up
        for (int day = n - 1; day >= 0; day--) {
            for (int t = 1; t <= k; t++) {
                for (int state = 0; state < 3; state++) {

                    // Option 1: Skip the day
                    long ans = dp[day + 1][t][state];

                    if (state == 0) {
                        // Buy
                        ans = Math.max(ans,
                                -prices[day] + dp[day + 1][t][1]);

                        // Sell (short sell)
                        ans = Math.max(ans,
                                prices[day] + dp[day + 1][t][2]);
                    }
                    else if (state == 1) {
                        // Sell to complete transaction
                        ans = Math.max(ans,
                                prices[day] + dp[day + 1][t - 1][0]);
                    }
                    else {
                        // Buy to complete transaction
                        ans = Math.max(ans,
                                -prices[day] + dp[day + 1][t - 1][0]);
                    }

                    // Store result
                    dp[day][t][state] = ans;
                }
            }
        }

        // Start from day 0, k transactions, no stock
        return dp[0][k][0];
    }

    // ============================================================
    //                         MAIN METHOD
    // ============================================================
    public static void main(String[] args) {

        BestTimeToBuyAndSellStockV solver =
                new BestTimeToBuyAndSellStockV();

        int[] prices = {1,7,9,8,2};
        int k = 2;

        System.out.println(
                "Maximum Profit = " +
                        solver.maximumProfit(prices, k)
        );
    }
}
