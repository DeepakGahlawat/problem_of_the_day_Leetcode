/*
    ============================================================
                    MAGIC SQUARES IN GRID
    ============================================================

    🧠 INTUITION
    ------------------------------------------------------------
    A 3x3 magic square must satisfy ALL of the following:
        1️⃣ Contains DISTINCT numbers from 1 to 9
        2️⃣ Sum of each ROW is the same
        3️⃣ Sum of each COLUMN is the same
        4️⃣ Sum of BOTH diagonals is the same

    Given a grid, we need to COUNT how many 3x3 subgrids
    satisfy the above magic square properties.

    ------------------------------------------------------------
    🧩 KEY OBSERVATIONS
    ------------------------------------------------------------
    - Only 3x3 subgrids are relevant
    - Grid size is small (≤ 10x10), so brute-force scanning
      of all possible 3x3 subgrids is feasible
    - Each candidate 3x3 grid can be validated independently

    ------------------------------------------------------------
    🧮 APPROACH
    ------------------------------------------------------------
    1️⃣ Iterate over all possible top-left corners of 3x3 subgrids
    2️⃣ For each subgrid:
         - Check all values are in range [1, 9] and distinct
         - Compute reference sum using first column
         - Validate all rows, columns, and diagonals
    3️⃣ Count how many subgrids pass all checks

    ------------------------------------------------------------
    ⏱️ TIME & SPACE COMPLEXITY
    ------------------------------------------------------------
    Let r = rows, c = columns

    Time Complexity:
        O(r * c)   (each 3x3 check is constant time)

    Space Complexity:
        O(1) extra (HashSet size ≤ 9)

    ------------------------------------------------------------
    ✔ Clean brute-force + validation
      Classic matrix / LeetCode interview problem
    ============================================================
*/

import java.util.*;

public class MagicSquaresInGrid {

    public int numMagicSquaresInside(int[][] grid) {

        int m = grid.length;
        int n = grid[0].length;

        // Not enough space for a 3x3 grid
        if (m < 3 || n < 3) return 0;

        int count = 0;

        // Iterate over all possible 3x3 subgrids
        for (int i = 0; i <= m - 3; i++) {
            for (int j = 0; j <= n - 3; j++) {

                if (isMagic(i, j, grid)) {
                    count++;
                }
            }
        }
        return count;
    }

    // Checks whether the 3x3 grid starting at (strow, stcol) is magic
    boolean isMagic(int strow, int stcol, int[][] grid) {

        HashSet<Integer> set = new HashSet<>();

        // Step 1: Check range [1,9] and uniqueness
        for (int i = strow; i < strow + 3; i++) {
            for (int j = stcol; j < stcol + 3; j++) {

                int val = grid[i][j];
                if (val < 1 || val > 9 || set.contains(val)) {
                    return false;
                }
                set.add(val);
            }
        }

        // Step 2: Reference sum (first column)
        int targetSum =
                grid[strow][stcol] +
                grid[strow + 1][stcol] +
                grid[strow + 2][stcol];

        // Step 3: Check remaining columns
        for (int col = stcol + 1; col < stcol + 3; col++) {
            int colSum =
                    grid[strow][col] +
                    grid[strow + 1][col] +
                    grid[strow + 2][col];
            if (colSum != targetSum) return false;
        }

        // Step 4: Check all rows
        for (int row = strow; row < strow + 3; row++) {
            int rowSum =
                    grid[row][stcol] +
                    grid[row][stcol + 1] +
                    grid[row][stcol + 2];
            if (rowSum != targetSum) return false;
        }

        // Step 5: Check both diagonals
        int diag1 =
                grid[strow][stcol] +
                grid[strow + 1][stcol + 1] +
                grid[strow + 2][stcol + 2];

        int diag2 =
                grid[strow][stcol + 2] +
                grid[strow + 1][stcol + 1] +
                grid[strow + 2][stcol];

        return diag1 == targetSum && diag2 == targetSum;
    }

    // ============================================================
    //                         MAIN METHOD
    // ============================================================
    public static void main(String[] args) {

        MagicSquaresInGrid solver = new MagicSquaresInGrid();

        int[][] grid1 = {
                {4, 3, 8, 4},
                {9, 5, 1, 9},
                {2, 7, 6, 2}
        };

        System.out.println(
                "Magic squares count = " +
                        solver.numMagicSquaresInside(grid1)
        ); // Output: 1

        int[][] grid2 = {{8}};
        System.out.println(
                "Magic squares count = " +
                        solver.numMagicSquaresInside(grid2)
        ); // Output: 0
    }
}
