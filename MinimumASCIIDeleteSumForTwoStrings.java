/*
    ===============================================================
        MINIMUM ASCII DELETE SUM FOR TWO STRINGS
    ===============================================================

    🧠 INTUITION
    ---------------------------------------------------------------
    We are given two strings s1 and s2.
    We are allowed to delete characters from either string.

    Goal:
        Make both strings equal
        Minimize the TOTAL ASCII value of deleted characters

    This is a classic DP problem similar to:
        - Edit Distance
        - Longest Common Subsequence (LCS)

    But instead of minimizing number of deletions,
    we minimize the ASCII COST of deletions.

    ---------------------------------------------------------------
    🧩 DP DEFINITION
    ---------------------------------------------------------------
    Let dp[i][j] = minimum ASCII delete sum required to make:
        s1[i ... end] and s2[j ... end] equal

    ---------------------------------------------------------------
    🔁 TRANSITIONS
    ---------------------------------------------------------------
    At position (i, j):

    1️⃣ If s1[i] == s2[j]:
        → No deletion needed
        dp[i][j] = dp[i+1][j+1]

    2️⃣ Otherwise:
        - Delete s1[i]:
              ASCII(s1[i]) + dp[i+1][j]
        - Delete s2[j]:
              ASCII(s2[j]) + dp[i][j+1]

        Take the minimum of both.

    ---------------------------------------------------------------
    ⚠️ BASE CASES
    ---------------------------------------------------------------
    - If s1 is exhausted:
        delete all remaining chars of s2
    - If s2 is exhausted:
        delete all remaining chars of s1

    ---------------------------------------------------------------
    🚀 OPTIMIZATION
    ---------------------------------------------------------------
    We use SPACE OPTIMIZED DP:
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
    ✔ Classic DP on strings
    ✔ Similar to LCS but cost-based
    ✔ Very common interview problem
    ===============================================================
*/

import java.util.*;

public class MinimumASCIIDeleteSumForTwoStrings {

    public int minimumDeleteSum(String s1, String s2) {

        int n = s1.length();
        int m = s2.length();

        // dp[j] = dp[i+1][j]
        int[] dp = new int[m + 1];

        // Base case: s1 is empty, delete all remaining chars of s2
        for (int j = m - 1; j >= 0; j--) {
            dp[j] = dp[j + 1] + s2.charAt(j);
        }

        // Fill DP bottom-up
        for (int i = n - 1; i >= 0; i--) {

            int[] curr = new int[m + 1];

            // Base case: s2 is empty, delete remaining chars of s1
            curr[m] = dp[m] + s1.charAt(i);

            for (int j = m - 1; j >= 0; j--) {

                char c1 = s1.charAt(i);
                char c2 = s2.charAt(j);

                int minCost = Integer.MAX_VALUE;

                // If characters match → no deletion
                if (c1 == c2) {
                    minCost = dp[j + 1];
                }

                // Delete from s1 OR delete from s2
                minCost = Math.min(
                        minCost,
                        Math.min(
                                c1 + dp[j],
                                c2 + curr[j + 1]
                        )
                );

                curr[j] = minCost;
            }

            dp = curr;
        }

        return dp[0];
    }

    // ---------------------------------------------------------------
    // Recursive + Memoized version (for understanding)
    // ---------------------------------------------------------------
    /*
    int find(int i, int j, String s1, String s2, int[][] dp) {

        if (i == s1.length()) {
            int sum = 0;
            for (int k = j; k < s2.length(); k++)
                sum += s2.charAt(k);
            return sum;
        }

        if (j == s2.length()) {
            int sum = 0;
            for (int k = i; k < s1.length(); k++)
                sum += s1.charAt(k);
            return sum;
        }

        if (dp[i][j] != -1) return dp[i][j];

        int ans = Integer.MAX_VALUE;

        if (s1.charAt(i) == s2.charAt(j)) {
            ans = find(i + 1, j + 1, s1, s2, dp);
        }

        ans = Math.min(ans,
                Math.min(
                        s1.charAt(i) + find(i + 1, j, s1, s2, dp),
                        s2.charAt(j) + find(i, j + 1, s1, s2, dp)
                )
        );

        return dp[i][j] = ans;
    }
    */

    // ---------------------------------------------------------------
    // MAIN METHOD (for local testing)
    // ---------------------------------------------------------------
    public static void main(String[] args) {

        MinimumASCIIDeleteSumForTwoStrings solver =
                new MinimumASCIIDeleteSumForTwoStrings();

        System.out.println(
                solver.minimumDeleteSum("sea", "eat")
        ); // 231

        System.out.println(
                solver.minimumDeleteSum("delete", "leet")
        ); // 403
    }
}
