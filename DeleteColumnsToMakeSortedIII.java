/*
    ============================================================
            DELETE COLUMNS TO MAKE SORTED – III
    ============================================================

    🧠 INTUITION
    ------------------------------------------------------------
    This is the MOST ADVANCED version of the
    "Delete Columns to Make Sorted" series.

    We are given an array of strings (all same length).
    We want to DELETE the MINIMUM number of columns so that
    the remaining columns form a NON-DECREASING sequence
    for every row (top to bottom).

    Unlike Part I & II:
        - Columns are NOT independent
        - We must preserve the RELATIVE ORDER of kept columns

    This becomes a:
        ✅ Dynamic Programming problem
        ✅ Similar to Longest Increasing Subsequence (LIS)
        ✅ Across columns, with row-wise constraints

    ------------------------------------------------------------
    🧩 CORE IDEA
    ------------------------------------------------------------
    Think column by column.

    For each column `curr`, we decide:
        1️⃣ KEEP it (only if valid compared to last kept column)
        2️⃣ DELETE it

    We track:
        - `last` → index of the last kept column
        - `curr` → current column index

    ------------------------------------------------------------
    🧮 DP STATE DEFINITION
    ------------------------------------------------------------
    dp[curr][last] =
        Minimum deletions needed starting from column `curr`,
        given the last kept column is `last`.

    Optimization:
        We only need the next row → space optimized to 1D array.

    ------------------------------------------------------------
    🧮 TRANSITIONS
    ------------------------------------------------------------
    Option 1: KEEP current column
        Allowed only if:
            strs[row][curr] >= strs[row][last] for ALL rows

        Cost:
            dp[curr+1][curr]

    Option 2: DELETE current column
        Cost:
            1 + dp[curr+1][last]

    Take minimum of both.

    ------------------------------------------------------------
    ⏱️ TIME & SPACE COMPLEXITY
    ------------------------------------------------------------
    Let:
        m = number of strings
        n = length of each string

    Time Complexity:
        O(n² × m)

    Space Complexity:
        O(n)   (space optimized DP)

    ------------------------------------------------------------
    ✔ This is a HARD DP problem (LeetCode Hard)
      Tests deep DP + optimization skills
    ============================================================
*/

import java.util.*;

public class DeleteColumnsToMakeSortedIII {

    public int minDeletionSize(String[] strs) {

        int m = strs.length;        // number of rows
        int n = strs[0].length();   // number of columns

        // next[last+1] represents dp[curr+1][last+1]
        int[] next = new int[n + 1];

        // Traverse columns from right to left
        for (int curr = n - 1; curr >= 0; curr--) {

            int[] currDp = new int[n + 1];

            // last ranges from -1 to curr-1
            for (int last = curr - 1; last >= -1; last--) {

                int minDelete = n;

                // OPTION 1: KEEP current column
                boolean canKeep = true;

                if (last != -1) {
                    for (int row = 0; row < m; row++) {
                        if (strs[row].charAt(curr)
                                < strs[row].charAt(last)) {
                            canKeep = false;
                            break;
                        }
                    }
                }

                if (canKeep) {
                    minDelete = Math.min(minDelete, next[curr + 1]);
                }

                // OPTION 2: DELETE current column
                minDelete = Math.min(minDelete, 1 + next[last + 1]);

                currDp[last + 1] = minDelete;
            }

            // Move dp row forward
            next = currDp;
        }

        // last = -1 → stored at index 0
        return next[0];
    }

    // ============================================================
    //                         MAIN METHOD
    // ============================================================
    public static void main(String[] args) {

        DeleteColumnsToMakeSortedIII solver =
                new DeleteColumnsToMakeSortedIII();

        String[] strs = {"babca", "bbazb"};

        System.out.println(
                "Minimum columns to delete = " +
                        solver.minDeletionSize(strs)
        );
    }
}
