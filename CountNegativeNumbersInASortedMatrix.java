/*
    ============================================================
        COUNT NEGATIVE NUMBERS IN A SORTED MATRIX
    ============================================================

    🧠 INTUITION
    ------------------------------------------------------------
    The matrix is sorted in NON-INCREASING order:
        - Each row is sorted left → right
        - Each column is sorted top → bottom

    That means:
        All negative numbers (if any) appear CONTIGUOUSLY
        at the END of each row.

    So for every row, we can:
        - Find the FIRST negative number using Binary Search
        - Count how many elements come after it

    ------------------------------------------------------------
    🧩 KEY OBSERVATION
    ------------------------------------------------------------
    If `low` is the index of the first negative number in a row,
    then:
        Number of negatives in that row = n - low

    ------------------------------------------------------------
    🧮 APPROACH
    ------------------------------------------------------------
    1️⃣ Iterate through each row
    2️⃣ Use binary search to find the first negative element
    3️⃣ Add (number of columns - index) to the answer
    4️⃣ Return total count

    ------------------------------------------------------------
    ⏱️ TIME & SPACE COMPLEXITY
    ------------------------------------------------------------
    Let m = number of rows, n = number of columns

    Time Complexity:
        O(m log n)

    Space Complexity:
        O(1)

    ------------------------------------------------------------
    ✔ Efficient binary-search-based solution
      Very common LeetCode / GFG matrix problem
    ============================================================
*/

import java.util.*;

public class CountNegativeNumbersInASortedMatrix {

    public int countNegatives(int[][] grid) {

        int count = 0;
        int n = grid[0].length;

        // Process each row independently
        for (int[] row : grid) {

            int low = 0, high = n - 1;

            // Binary search for first negative number
            while (low <= high) {
                int mid = (low + high) / 2;

                if (row[mid] >= 0) {
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }

            // All elements from index `low` to end are negative
            count += (n - low);
        }

        return count;
    }

    // ============================================================
    //                         MAIN METHOD
    // ============================================================
    public static void main(String[] args) {

        CountNegativeNumbersInASortedMatrix solver =
                new CountNegativeNumbersInASortedMatrix();

        int[][] grid1 = {
                {4, 3, 2, -1},
                {3, 2, 1, -1},
                {1, 1, -1, -2},
                {-1, -1, -2, -3}
        };

        System.out.println(
                "Negative count = " +
                        solver.countNegatives(grid1)
        ); // Output: 8

        int[][] grid2 = {
                {3, 2},
                {1, 0}
        };

        System.out.println(
                "Negative count = " +
                        solver.countNegatives(grid2)
        ); // Output: 0
    }
}
