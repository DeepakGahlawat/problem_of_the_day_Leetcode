/*
    ============================================================
        MAXIMIZE HAPPINESS OF SELECTED CHILDREN
    ============================================================

    🧠 INTUITION
    ------------------------------------------------------------
    We are given happiness values of n children.
    We need to select exactly k children over k turns.

    Rule:
        - Each time we select a child,
          happiness of ALL unselected children decreases by 1
        - Happiness never goes below 0

    Goal:
        - Maximize the TOTAL happiness of selected children

    ------------------------------------------------------------
    🧩 KEY OBSERVATION (GREEDY)
    ------------------------------------------------------------
    To maximize total happiness:
        - Always pick the child with the CURRENT maximum happiness

    Why?
        - If we delay picking a highly happy child,
          its value will keep decreasing due to future selections.

    After picking i children:
        - Effective happiness = original_happiness - i
        - If this becomes negative → treat it as 0

    ------------------------------------------------------------
    🧮 APPROACH
    ------------------------------------------------------------
    1️⃣ Sort the happiness array in ascending order
    2️⃣ Pick the largest k values from the end
    3️⃣ For the i-th pick:
         contribution = max(happiness[n-1-i] - i, 0)
    4️⃣ Sum all contributions

    ------------------------------------------------------------
    ⏱️ TIME & SPACE COMPLEXITY
    ------------------------------------------------------------
    Time Complexity:
        O(n log n)  (sorting)

    Space Complexity:
        O(1) extra (in-place sort)

    ------------------------------------------------------------
    ✔ Greedy strategy ensures maximum total happiness
      Common LeetCode / OA problem
    ============================================================
*/

import java.util.*;

public class MaximizeHappinessOfSelectedChildren {

    public long maximumHappinessSum(int[] happiness, int k) {

        int n = happiness.length;

        // Step 1: Sort happiness values
        Arrays.sort(happiness);

        long total = 0;

        // Step 2: Pick top k happiest children greedily
        for (int i = 0; i < k; i++) {

            // Happiness decreases by i due to previous picks
            int effectiveHappiness = happiness[n - 1 - i] - i;

            // Happiness cannot be negative
            if (effectiveHappiness > 0) {
                total += effectiveHappiness;
            }
        }

        return total;
    }

    // ============================================================
    //                         MAIN METHOD
    // ============================================================
    public static void main(String[] args) {

        MaximizeHappinessOfSelectedChildren solver =
                new MaximizeHappinessOfSelectedChildren();

        int[] happiness1 = {1, 2, 3};
        int k1 = 2;
        System.out.println(
                "Maximum happiness = " +
                        solver.maximumHappinessSum(happiness1, k1)
        );

        int[] happiness2 = {1, 1, 1, 1};
        int k2 = 2;
        System.out.println(
                "Maximum happiness = " +
                        solver.maximumHappinessSum(happiness2, k2)
        );

        int[] happiness3 = {2, 3, 4, 5};
        int k3 = 1;
        System.out.println(
                "Maximum happiness = " +
                        solver.maximumHappinessSum(happiness3, k3)
        );
    }
}
