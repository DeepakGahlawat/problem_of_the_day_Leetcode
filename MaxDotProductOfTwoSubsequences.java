/*
    ===============================================================
            MAX DOT PRODUCT OF TWO SUBSEQUENCES
    ===============================================================

    🧠 INTUITION
    ---------------------------------------------------------------
    We are given two arrays nums1 and nums2.
    We must choose NON-EMPTY subsequences from both arrays
    (same length) such that their dot product is maximized.

    Important challenge:
    👉 Values can be NEGATIVE
    👉 We MUST pick at least one pair
    👉 So standard "ignore negative" DP does NOT work

    ---------------------------------------------------------------
    🧩 DP DEFINITION
    ---------------------------------------------------------------
    Let dp[i][j] = maximum dot product using:
        nums1[i ... end]
        nums2[j ... end]

    Transitions at (i, j):

    1️⃣ TAKE both elements:
        prod = nums1[i] * nums2[j]

        We can:
        - Start new subsequence with prod
        - OR extend an existing subsequence

        take = max(
            prod,
            prod + dp[i+1][j+1]
        )

    2️⃣ SKIP nums1[i]:
        skip1 = dp[i+1][j]

    3️⃣ SKIP nums2[j]:
        skip2 = dp[i][j+1]

    dp[i][j] = max(take, skip1, skip2)

    ---------------------------------------------------------------
    ⚠️ VERY IMPORTANT DETAIL
    ---------------------------------------------------------------
    Base case returns Integer.MIN_VALUE (not 0)
    to ensure we NEVER choose an empty subsequence.

    ---------------------------------------------------------------
    🚀 OPTIMIZATION
    ---------------------------------------------------------------
    We use bottom-up DP with SPACE OPTIMIZATION:
        - Only keep one row (dp) and one current row (curr)
        - Reduces space from O(n*m) → O(m)

    ---------------------------------------------------------------
    ⏱️ TIME & SPACE COMPLEXITY
    ---------------------------------------------------------------
    Time Complexity:
        O(n * m)

    Space Complexity:
        O(m)

    ---------------------------------------------------------------
    ✔ This is a HARD DP problem
    ✔ Very common FAANG interview question
    ✔ Handling negatives correctly is the key insight
    ===============================================================
*/

import java.util.*;

public class MaxDotProductOfTwoSubsequences {

    public int maxDotProduct(int[] nums1, int[] nums2) {

        int n = nums1.length;
        int m = nums2.length;

        // dp[j] represents dp[i+1][j]
        int[] dp = new int[m + 1];

        // Base initialization
        Arrays.fill(dp, Integer.MIN_VALUE);

        // Bottom-up DP
        for (int i = n - 1; i >= 0; i--) {

            int[] curr = new int[m + 1];
            curr[m] = Integer.MIN_VALUE;

            for (int j = m - 1; j >= 0; j--) {

                int prod = nums1[i] * nums2[j];

                int next = dp[j + 1];

                // Take both elements
                int take = prod;
                if (next != Integer.MIN_VALUE) {
                    take = Math.max(prod, prod + next);
                }

                // Skip one element
                int skip1 = dp[j];
                int skip2 = curr[j + 1];

                curr[j] = Math.max(take, Math.max(skip1, skip2));
            }

            dp = curr;
        }

        return dp[0];
    }

    // ---------------------------------------------------------------
    // Recursive + Memoized version (for understanding / reference)
    // ---------------------------------------------------------------
    /*
    int solve(int i, int j, int[] nums1, int[] nums2, Integer[][] dp) {

        if (i == nums1.length || j == nums2.length) {
            return Integer.MIN_VALUE;
        }

        if (dp[i][j] != null) return dp[i][j];

        int prod = nums1[i] * nums2[j];

        int next = solve(i + 1, j + 1, nums1, nums2, dp);

        int take = prod;
        if (next != Integer.MIN_VALUE) {
            take = Math.max(prod, prod + next);
        }

        int skip1 = solve(i + 1, j, nums1, nums2, dp);
        int skip2 = solve(i, j + 1, nums1, nums2, dp);

        return dp[i][j] = Math.max(take, Math.max(skip1, skip2));
    }
    */

    // ---------------------------------------------------------------
    // MAIN METHOD (for local testing)
    // ---------------------------------------------------------------
    public static void main(String[] args) {

        MaxDotProductOfTwoSubsequences solver =
                new MaxDotProductOfTwoSubsequences();

        int[] nums1a = {2, 1, -2, 5};
        int[] nums2a = {3, 0, -6};
        System.out.println(solver.maxDotProduct(nums1a, nums2a));
        // Expected: 18

        int[] nums1b = {3, -2};
        int[] nums2b = {2, -6, 7};
        System.out.println(solver.maxDotProduct(nums1b, nums2b));
        // Expected: 21

        int[] nums1c = {-1, -1};
        int[] nums2c = {1, 1};
        System.out.println(solver.maxDotProduct(nums1c, nums2c));
        // Expected: -1
    }
}
