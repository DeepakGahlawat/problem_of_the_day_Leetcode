import java.util.*;

/*
Class Name: LargestMagicSquare

Problem Summary:
A k x k magic square is a grid where:
- Every row sum is equal
- Every column sum is equal
- Both diagonal sums are equal

Goal:
Given an m x n grid, find the size (side length) of the largest magic square present.
Every 1x1 grid is always a valid magic square.

--------------------------------------------------
Intuition:
--------------------------------------------------
- Checking sums repeatedly is expensive.
- Use prefix sums for rows and columns to calculate row/column sums in O(1).
- Try the largest possible square first and return immediately when found.

--------------------------------------------------
Approach:
--------------------------------------------------
1. Build prefix sums for rows and columns.
2. Try all square sizes from largest to smallest.
3. For each square:
   - Compare all row sums
   - Compare all column sums
   - Compare both diagonal sums
4. Return the first valid (largest) square size.
5. If none found, return 1.

--------------------------------------------------
Time Complexity:
O(m * n * min(m, n))

Space Complexity:
O(m * n)
--------------------------------------------------
*/

class LargestMagicSquare {

    public int largestMagicSquare(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        // Row prefix sums
        int[][] rowsum = new int[m][n];
        for (int i = 0; i < m; ++i) {
            rowsum[i][0] = grid[i][0];
            for (int j = 1; j < n; ++j) {
                rowsum[i][j] = rowsum[i][j - 1] + grid[i][j];
            }
        }

        // Column prefix sums
        int[][] colsum = new int[m][n];
        for (int j = 0; j < n; ++j) {
            colsum[0][j] = grid[0][j];
            for (int i = 1; i < m; ++i) {
                colsum[i][j] = colsum[i - 1][j] + grid[i][j];
            }
        }

        // Try larger squares first
        for (int edge = Math.min(m, n); edge >= 2; --edge) {

            // Top-left corner of square
            for (int i = 0; i + edge <= m; ++i) {
                for (int j = 0; j + edge <= n; ++j) {

                    // Reference sum (first row)
                    int stdsum =
                        rowsum[i][j + edge - 1] -
                        (j > 0 ? rowsum[i][j - 1] : 0);

                    boolean valid = true;

                    // Check all rows
                    for (int r = i + 1; r < i + edge; ++r) {
                        int sum =
                            rowsum[r][j + edge - 1] -
                            (j > 0 ? rowsum[r][j - 1] : 0);
                        if (sum != stdsum) {
                            valid = false;
                            break;
                        }
                    }
                    if (!valid) continue;

                    // Check all columns
                    for (int c = j; c < j + edge; ++c) {
                        int sum =
                            colsum[i + edge - 1][c] -
                            (i > 0 ? colsum[i - 1][c] : 0);
                        if (sum != stdsum) {
                            valid = false;
                            break;
                        }
                    }
                    if (!valid) continue;

                    // Check diagonals
                    int d1 = 0, d2 = 0;
                    for (int k = 0; k < edge; ++k) {
                        d1 += grid[i + k][j + k];
                        d2 += grid[i + k][j + edge - 1 - k];
                    }

                    if (d1 == stdsum && d2 == stdsum) {
                        return edge;
                    }
                }
            }
        }

        // At least 1x1 is always magic
        return 1;
    }

    // Main method for testing
    public static void main(String[] args) {
        LargestMagicSquare obj = new LargestMagicSquare();

        int[][] grid1 = {
            {7, 1, 4, 5, 6},
            {2, 5, 1, 6, 4},
            {1, 5, 4, 3, 2},
            {1, 2, 7, 3, 4}
        };

        int[][] grid2 = {
            {5, 1, 3, 1},
            {9, 3, 3, 1},
            {1, 3, 3, 8}
        };

        System.out.println(obj.largestMagicSquare(grid1)); // Expected: 3
        System.out.println(obj.largestMagicSquare(grid2)); // Expected: 2
    }
}
