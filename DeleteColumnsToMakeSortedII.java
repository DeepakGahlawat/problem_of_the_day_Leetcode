/*
    ============================================================
            DELETE COLUMNS TO MAKE SORTED – II
    ============================================================

    🧠 INTUITION
    ------------------------------------------------------------
    We are given an array of strings (all of equal length).
    Our task is to delete the MINIMUM number of columns so that
    the array becomes lexicographically sorted.

    This is an extension of "Delete Columns to Make Sorted I".

    Key difference:
        - In Part I, columns are independent.
        - In Part II, decisions made on earlier columns affect
          later columns.

    ------------------------------------------------------------
    🧩 KEY IDEA
    ------------------------------------------------------------
    We track which adjacent string pairs are already sorted
    using a boolean array `sorted[]`.

    sorted[j] = true  →
        strs[j] < strs[j+1] is already confirmed by
        earlier columns, so future columns don’t matter
        for this pair.

    ------------------------------------------------------------
    🧮 APPROACH
    ------------------------------------------------------------
    1️⃣ Iterate column by column
    2️⃣ For each column:
         - Check if it violates order for ANY unsorted pair
           → delete this column
         - Otherwise:
             • Update sorted[] for pairs that become strictly
               ordered in this column
    3️⃣ Count how many columns were deleted

    ------------------------------------------------------------
    ⏱️ TIME & SPACE COMPLEXITY
    ------------------------------------------------------------
    Time Complexity:
        O(n × w)
        where n = number of strings
              w = length of each string

    Space Complexity:
        O(n)

    ------------------------------------------------------------
    ✔ Greedy + state tracking
      Common LeetCode Medium problem
    ============================================================
*/

import java.util.*;

public class DeleteColumnsToMakeSortedII {

    public int minDeletionSize(String[] strs) {

        int n = strs.length;
        int w = strs[0].length();

        // Tracks whether adjacent pairs are already sorted
        boolean[] sorted = new boolean[n - 1];

        int deleteCount = 0;

        // Traverse each column
        for (int col = 0; col < w; col++) {

            boolean valid = true;

            // Step 1: Check if this column violates order
            for (int row = 0; row < n - 1; row++) {
                if (!sorted[row] &&
                        strs[row].charAt(col) > strs[row + 1].charAt(col)) {
                    valid = false;
                    break;
                }
            }

            // If invalid, delete this column
            if (!valid) {
                deleteCount++;
                continue;
            }

            // Step 2: Update sorted pairs
            for (int row = 0; row < n - 1; row++) {
                if (!sorted[row] &&
                        strs[row].charAt(col) < strs[row + 1].charAt(col)) {
                    sorted[row] = true;
                }
            }
        }

        return deleteCount;
    }

    // ============================================================
    //                         MAIN METHOD
    // ============================================================
    public static void main(String[] args) {

        DeleteColumnsToMakeSortedII solver =
                new DeleteColumnsToMakeSortedII();

        String[] strs = {"ca", "bb", "ac"};

        System.out.println(
                "Minimum columns to delete = " +
                        solver.minDeletionSize(strs)
        );
    }
}
