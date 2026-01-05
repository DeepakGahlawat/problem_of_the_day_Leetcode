/*
    ============================================================
                    MAXIMUM MATRIX SUM
    ============================================================

    🧠 INTUITION
    ------------------------------------------------------------
    You are allowed to flip the sign of TWO ADJACENT cells
    (multiply both by -1) any number of times.

    This operation allows us to:
        - Change signs of elements flexibly
        - Effectively move negative signs around the matrix

    The goal is to MAXIMIZE the total sum of all elements.

    ------------------------------------------------------------
    🧩 KEY OBSERVATIONS
    ------------------------------------------------------------
    1️⃣ Since we can flip signs in pairs, the RELATIVE parity
       (even / odd) of negative numbers matters.

    2️⃣ If the number of negative elements is EVEN:
         → We can make ALL numbers positive.

    3️⃣ If the number of negative elements is ODD:
         → One element MUST remain negative.
         → To minimize loss, we keep the SMALLEST absolute value negative.

    ------------------------------------------------------------
    🧮 GREEDY STRATEGY
    ------------------------------------------------------------
    - Take absolute value of every element
    - Count how many negatives exist
    - Track the minimum absolute value

    Final result:
        - If negatives count is even → sum of all absolute values
        - Else → sum - 2 * (minimum absolute value)

    ------------------------------------------------------------
    ⏱️ TIME & SPACE COMPLEXITY
    ------------------------------------------------------------
    Time Complexity:
        O(n²)

    Space Complexity:
        O(1)

    ------------------------------------------------------------
    ✔ Elegant greedy + parity logic
      Common LeetCode medium interview problem
    ============================================================
*/

public class MaximumMatrixSum {

    public long maxMatrixSum(int[][] matrix) {

        long sum = 0;
        long minAbs = Long.MAX_VALUE;
        int negativeCount = 0;
        int n = matrix.length;

        // Traverse entire matrix
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {

                int curr = matrix[i][j];

                // Count negative numbers
                if (curr < 0) negativeCount++;

                // Add absolute value
                long absVal = Math.abs(curr);
                sum += absVal;

                // Track smallest absolute value
                minAbs = Math.min(minAbs, absVal);
            }
        }

        // If negative count is even → all can be positive
        if (negativeCount % 2 == 0) return sum;

        // Otherwise, one smallest absolute value stays negative
        return sum - 2 * minAbs;
    }

    // ============================================================
    //                         MAIN METHOD
    // ============================================================
    public static void main(String[] args) {

        MaximumMatrixSum solver = new MaximumMatrixSum();

        int[][] matrix1 = {
                {1, -1},
                {-1, 1}
        };
        System.out.println(solver.maxMatrixSum(matrix1)); // 4

        int[][] matrix2 = {
                {1, 2, 3},
                {-1, -2, -3},
                {1, 2, 3}
        };
        System.out.println(solver.maxMatrixSum(matrix2)); // 16
    }
}
