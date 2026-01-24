import java.util.Arrays;

public class MinimizeMaximumPairSumInArray {

    /**
     * Problem: Minimize Maximum Pair Sum in Array
     *
     * Given an array of EVEN length, we need to:
     * - Form n/2 pairs
     * - Each element must be used exactly once
     * - Minimize the maximum pair sum among all pairs
     *
     * Key Idea (Greedy + Sorting):
     * --------------------------------
     * 1) Sort the array.
     * 2) Pair the smallest element with the largest element,
     *    second smallest with second largest, and so on.
     *
     * Why this works:
     * - Pairing small with large balances the sums.
     * - If we pair large numbers together, max sum increases.
     * - This greedy strategy guarantees the minimum possible
     *   maximum pair sum.
     *
     * Example:
     * nums = [3,5,2,3] → sorted = [2,3,3,5]
     * pairs:
     *   (2,5) → 7
     *   (3,3) → 6
     * max = 7 (minimum possible)
     *
     * Time Complexity: O(n log n)  (sorting)
     * Space Complexity: O(1) extra (sorting in-place)
     */
    public int minPairSum(int[] nums) {

        // Step 1: Sort the array
        Arrays.sort(nums);

        int maxSum = 0;
        int n = nums.length;

        // Step 2: Pair smallest with largest
        for (int i = 0; i < n / 2; i++) {
            int pairSum = nums[i] + nums[n - 1 - i];
            maxSum = Math.max(maxSum, pairSum);
        }

        // Step 3: Return the minimized maximum pair sum
        return maxSum;
    }

    /**
     * Main method for testing
     */
    public static void main(String[] args) {
        MinimizeMaximumPairSumInArray sol = new MinimizeMaximumPairSumInArray();

        // Example 1
        int[] nums1 = {3, 5, 2, 3};
        System.out.println("Input: [3,5,2,3]");
        System.out.println("Output: " + sol.minPairSum(nums1)); // Expected: 7
        System.out.println();

        // Example 2
        int[] nums2 = {3, 5, 4, 2, 4, 6};
        System.out.println("Input: [3,5,4,2,4,6]");
        System.out.println("Output: " + sol.minPairSum(nums2)); // Expected: 8
        System.out.println();

        // Extra test
        int[] nums3 = {1, 1, 1, 1};
        System.out.println("Input: [1,1,1,1]");
        System.out.println("Output: " + sol.minPairSum(nums3)); // Expected: 2
    }
}
