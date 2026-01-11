/*
========================================================
Class Name: Maximal Rectangle
========================================================

Intuition:
-----------
The problem asks for the largest rectangle consisting of only '1's
in a binary matrix.

Key Observation:
----------------
If we process the matrix row by row, we can treat each row as the
base of a histogram.

For every column:
- If matrix[row][col] == '1', we increase the height.
- If matrix[row][col] == '0', we reset the height to 0.

Thus, for each row we build a histogram of heights and compute
the largest rectangle in that histogram.

This converts the 2D problem into multiple 1D
"Largest Rectangle in Histogram" problems.

Approach:
---------
1. Maintain an array `prev[]` to store heights of consecutive '1's.
2. For each row:
   - Update heights.
   - Compute largest rectangle area using stack-based histogram logic.
3. Keep track of the maximum area.

Time Complexity:
----------------
Let rows = n, cols = m
- Updating heights per row: O(m)
- Largest rectangle per row: O(m)

Total Time Complexity: O(n * m)
Space Complexity: O(m) for histogram + stack

========================================================
*/

import java.util.*;

class MaximalRectangle {

    public int maximalRectangle(char[][] matrix) {
        int maxArea = 0;
        int n = matrix.length;
        int m = matrix[0].length;

        // Stores histogram heights for previous row
        int[] prev = new int[m];

        // Process each row
        for (char[] arr : matrix) {
            int[] newArr = new int[m];

            // Build histogram
            for (int i = 0; i < m; i++) {
                if (arr[i] == '1') {
                    newArr[i] = prev[i] + 1;   // extend height
                } else {
                    newArr[i] = 0;            // reset height
                }
            }

            prev = newArr;

            // Calculate max rectangle in current histogram
            maxArea = Math.max(maxArea, largestRectangleArea(prev));
        }
        return maxArea;
    }

    // Standard Largest Rectangle in Histogram (Monotonic Stack)
    public int largestRectangleArea(int[] heights) {
        Stack<Integer> st = new Stack<>();
        int n = heights.length;
        int maxArea = 0;

        for (int i = 0; i <= n; i++) {
            int currHeight = (i == n) ? 0 : heights[i];

            // Maintain increasing stack
            while (!st.isEmpty() && heights[st.peek()] > currHeight) {
                int height = heights[st.pop()];
                int width = st.isEmpty() ? i : i - st.peek() - 1;
                maxArea = Math.max(maxArea, height * width);
            }
            st.push(i);
        }
        return maxArea;
    }

    // Main method for testing
    public static void main(String[] args) {
        MaximalRectangle sol = new MaximalRectangle();

        char[][] matrix1 = {
            {'1','0','1','0','0'},
            {'1','0','1','1','1'},
            {'1','1','1','1','1'},
            {'1','0','0','1','0'}
        };

        char[][] matrix2 = {
            {'0'}
        };

        char[][] matrix3 = {
            {'1'}
        };

        System.out.println(sol.maximalRectangle(matrix1)); // 6
        System.out.println(sol.maximalRectangle(matrix2)); // 0
        System.out.println(sol.maximalRectangle(matrix3)); // 1
    }
}
