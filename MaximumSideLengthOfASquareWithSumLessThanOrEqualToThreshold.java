
/*
Class Name: MaximumSideLengthOfASquareWithSumLessThanOrEqualToThreshold

Problem Summary:
You are given an m x n matrix `mat` and an integer `threshold`.
Your task is to find the maximum possible side length of a square submatrix
whose sum of elements is less than or equal to `threshold`.

If no such square exists, return 0.

--------------------------------------------------
Intuition:
--------------------------------------------------
- Directly checking all possible squares would be too slow.
- Use **2D Prefix Sum** to compute the sum of any square in O(1).
- Use **Binary Search** on the side length of the square:
  - If a square of size `k` is possible, then all sizes < k are also possible.
  - This monotonic behavior allows binary search.

--------------------------------------------------
Approach:
--------------------------------------------------
1. Build a 2D prefix sum array `presum`.
2. Binary search on the side length from 0 to min(m, n).
3. For each mid (candidate side length), check if any square of that size
   has sum ≤ threshold using prefix sum.
4. Keep track of the maximum valid side length.

--------------------------------------------------
Time Complexity:
- Prefix sum construction: O(m * n)
- Binary search: O(log(min(m, n)))
- Feasibility check per step: O(m * n)
Overall: O(m * n * log(min(m, n)))

Space Complexity:
- O(m * n) for prefix sum
--------------------------------------------------
*/

class MaximumSideLengthOfASquareWithSumLessThanOrEqualToThreshold {

    public int maxSideLength(int[][] mat, int threshold) {
        int m = mat.length;
        int n = mat[0].length;

        // Prefix sum matrix of size (m+1) x (n+1)
        int[][] presum = new int[m + 1][n + 1];

        // Build prefix sum row-wise
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                presum[i + 1][j + 1] = presum[i][j + 1] + mat[i][j];
            }
        }

        // Convert to full 2D prefix sum
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                presum[i + 1][j + 1] = presum[i + 1][j] + presum[i + 1][j + 1];
            }
        }

        int ans = 0;
        int low = 0;
        int high = Math.min(m, n);

        // Binary search on side length
        while (low <= high) {
            int mid = (low + high) / 2;

            if (isPossible(mid, presum, threshold)) {
                ans = mid;
                low = mid + 1; // try bigger square
            } else {
                high = mid - 1;
            }
        }

        return ans;
    }

    // Check if there exists a square of side = mid with sum <= threshold
    boolean isPossible(int mid, int[][] presum, int th) {
        int m = presum.length;
        int n = presum[0].length;

        for (int i = mid; i < m; i++) {
            for (int j = mid; j < n; j++) {

                // Sum of square using prefix sum formula
                int sum = presum[i][j]
                        + presum[i - mid][j - mid]
                        - presum[i][j - mid]
                        - presum[i - mid][j];

                if (sum <= th) return true;
            }
        }
        return false;
    }

    // Main method for testing
    public static void main(String[] args) {
        MaximumSideLengthOfASquareWithSumLessThanOrEqualToThreshold obj =
                new MaximumSideLengthOfASquareWithSumLessThanOrEqualToThreshold();

        int[][] mat1 = {
            {1, 1, 3, 2, 4, 3, 2},
            {1, 1, 3, 2, 4, 3, 2},
            {1, 1, 3, 2, 4, 3, 2}
        };
        int threshold1 = 4;
        System.out.println(obj.maxSideLength(mat1, threshold1)); // Expected: 2

        int[][] mat2 = {
            {2, 2, 2, 2, 2},
            {2, 2, 2, 2, 2},
            {2, 2, 2, 2, 2},
            {2, 2, 2, 2, 2},
            {2, 2, 2, 2, 2}
        };
        int threshold2 = 1;
        System.out.println(obj.maxSideLength(mat2, threshold2)); // Expected: 0
    }
}
