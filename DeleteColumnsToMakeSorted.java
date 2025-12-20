/*
    ============================================================
                DELETE COLUMNS TO MAKE SORTED
    ============================================================

    🧠 INTUITION
    ------------------------------------------------------------
    We are given an array of strings where:
        - All strings have the same length
        - Each column represents a vertical sequence of characters

    A column is considered SORTED if:
        characters from top to bottom are in non-decreasing order.

    If any column violates this condition, that entire column
    must be deleted.

    ------------------------------------------------------------
    🧩 KEY OBSERVATION
    ------------------------------------------------------------
    Columns are INDEPENDENT.
    So we can check each column individually.

    ------------------------------------------------------------
    🧮 APPROACH
    ------------------------------------------------------------
    1️⃣ Iterate column by column
    2️⃣ For each column:
         - Compare characters row-wise
         - If any strs[row][col] < strs[row-1][col],
           mark this column for deletion
    3️⃣ Count such columns

    ------------------------------------------------------------
    ⏱️ TIME & SPACE COMPLEXITY
    ------------------------------------------------------------
    Time Complexity:
        O(m × n)
        where m = number of strings,
              n = length of each string

    Space Complexity:
        O(1)

    ------------------------------------------------------------
    ✔ Simple matrix-style traversal
      Common string interview problem
    ============================================================
*/

import java.util.*;

public class DeleteColumnsToMakeSorted {

    public int minDeletionSize(String[] strs) {

        int m = strs.length;        // number of rows
        int n = strs[0].length();   // number of columns

        int deleteCount = 0;

        // Traverse each column
        for (int col = 0; col < n; col++) {

            // Compare rows in this column
            for (int row = 1; row < m; row++) {

                // If column is not sorted
                if (strs[row].charAt(col) < strs[row - 1].charAt(col)) {
                    deleteCount++;
                    break; // move to next column
                }
            }
        }

        return deleteCount;
    }

    // ============================================================
    //                         MAIN METHOD
    // ============================================================
    public static void main(String[] args) {

        DeleteColumnsToMakeSorted solver =
                new DeleteColumnsToMakeSorted();

        String[] strs = {"cba", "daf", "ghi"};

        System.out.println(
                "Minimum columns to delete = " +
                        solver.minDeletionSize(strs)
        );
    }
}
