/*
    ============================================================
                MINIMUM PENALTY FOR A SHOP
    ============================================================

    🧠 INTUITION
    ------------------------------------------------------------
    We are given a string `customers` where:
        'Y' → customers came in that hour
        'N' → no customers came

    If the shop closes at hour j:
        - For hours [0 .. j-1] (shop OPEN):
              penalty += 1 for each 'N'
        - For hours [j .. n-1] (shop CLOSED):
              penalty += 1 for each 'Y'

    Goal:
        Find the EARLIEST hour j with MINIMUM penalty.

    ------------------------------------------------------------
    🧩 KEY OBSERVATION
    ------------------------------------------------------------
    Start with the shop CLOSED for the entire day (j = 0):
        - Penalty = total number of 'Y'

    As we move hour by hour:
        - 'Y' → penalty decreases (customer served)
        - 'N' → penalty increases (shop open, no customer)

    Track the minimum penalty and earliest hour.

    ------------------------------------------------------------
    🧮 APPROACH
    ------------------------------------------------------------
    1️⃣ Count total 'Y' → initial penalty
    2️⃣ Iterate through hours:
         - Update penalty based on current hour
         - Track minimum penalty and earliest hour
    3️⃣ Return the best hour

    ------------------------------------------------------------
    ⏱️ TIME & SPACE COMPLEXITY
    ------------------------------------------------------------
    Time Complexity:  O(n)
    Space Complexity: O(1)

    ------------------------------------------------------------
    ✔ Real interview favorite (Amazon, Google)
    ============================================================
*/

import java.util.*;

public class MinimumPenaltyForAShop {

    public int bestClosingTime(String customers) {

        int penalty = 0;

        // Initial penalty: shop closed all day
        for (char c : customers.toCharArray()) {
            if (c == 'Y') penalty++;
        }

        int minPenalty = penalty;
        int bestHour = 0;

        // Try closing time from hour 0 to n
        for (int i = 0; i < customers.length(); i++) {

            if (customers.charAt(i) == 'Y') {
                penalty--;   // customer served
            } else {
                penalty++;   // shop open but no customer
            }

            // Update earliest hour with minimum penalty
            if (penalty < minPenalty) {
                minPenalty = penalty;
                bestHour = i + 1;
            }
        }

        return bestHour;
    }

    // ============================================================
    //                         MAIN METHOD
    // ============================================================
    public static void main(String[] args) {

        MinimumPenaltyForAShop solver =
                new MinimumPenaltyForAShop();

        System.out.println(solver.bestClosingTime("YYNY"));   // 2
        System.out.println(solver.bestClosingTime("NNNNN")); // 0
        System.out.println(solver.bestClosingTime("YYYY"));  // 4
    }
}
