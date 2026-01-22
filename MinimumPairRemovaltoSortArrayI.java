import java.util.ArrayList;

public class MinimumPairRemovaltoSortArrayI {

    /**
     * Problem: Minimum Pair Removal to Sort Array I
     *
     * Operation:
     * 1) Find adjacent pair with minimum sum (if multiple, choose LEFTMOST)
     * 2) Replace that pair with their sum (array size decreases by 1)
     * 3) Repeat until array becomes non-decreasing
     *
     * Return: minimum number of operations needed
     *
     * Time Complexity: O(n^2)
     * Space Complexity: O(n)
     */
    public int minimumPairRemoval(int[] nums) {

        // Convert array into dynamic list for easy removal and update
        ArrayList<Integer> list = new ArrayList<>();
        for (int it : nums) list.add(it);

        int op = 0;

        while (true) {

            // ✅ stop when array becomes non-decreasing
            boolean sorted = true;
            for (int i = 1; i < list.size(); i++) {
                if (list.get(i) < list.get(i - 1)) {
                    sorted = false;
                    break;
                }
            }

            if (sorted) break;

            // Find adjacent pair with minimum sum (leftmost if tie)
            int minInd = 0;
            int minSum = Integer.MAX_VALUE;

            for (int i = 0; i < list.size() - 1; i++) {
                int sum = list.get(i) + list.get(i + 1);

                // < ensures leftmost choice if tie occurs
                if (sum < minSum) {
                    minSum = sum;
                    minInd = i;
                }
            }

            // Perform merge operation
            op++;
            list.remove(minInd + 1);    // remove right element
            list.set(minInd, minSum);   // replace left element with sum
        }

        return op;
    }

    public static void main(String[] args) {
        MinimumPairRemovaltoSortArrayI sol = new MinimumPairRemovaltoSortArrayI();

        // Example 1
        int[] nums1 = {5, 2, 3, 1};
        System.out.println("Input: [5,2,3,1]");
        System.out.println("Output: " + sol.minimumPairRemoval(nums1)); // Expected: 2
        System.out.println();

        // Example 2
        int[] nums2 = {1, 2, 2};
        System.out.println("Input: [1,2,2]");
        System.out.println("Output: " + sol.minimumPairRemoval(nums2)); // Expected: 0
        System.out.println();
    }
}
