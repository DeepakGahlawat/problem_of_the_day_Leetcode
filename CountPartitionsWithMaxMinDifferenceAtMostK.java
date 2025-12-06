import java.util.*;

class Solution {

    public int countPartitions(int[] nums, int k) {
        int n = nums.length;
        long mod = (long) 1e9 + 7;
        long[] dp = new long[n + 1];
        long[] prefix = new long[n + 1];
        Deque<Integer> minQ = new LinkedList<>();
        Deque<Integer> maxQ = new LinkedList<>();

        dp[0] = 1;
        prefix[0] = 1;

        for (int i = 0, j = 0; i < n; i++) {

            // Maintain max deque
            while (!maxQ.isEmpty() && nums[maxQ.peekLast()] <= nums[i])
                maxQ.pollLast();
            maxQ.offerLast(i);

            // Maintain min deque
            while (!minQ.isEmpty() && nums[minQ.peekLast()] >= nums[i])
                minQ.pollLast();
            minQ.offerLast(i);

            // Shrink window until valid
            while (!maxQ.isEmpty() &&
                   !minQ.isEmpty() &&
                   nums[maxQ.peekFirst()] - nums[minQ.peekFirst()] > k) {

                if (maxQ.peekFirst() == j) maxQ.pollFirst();
                if (minQ.peekFirst() == j) minQ.pollFirst();
                j++;
            }

            dp[i + 1] = (prefix[i] - (j > 0 ? prefix[j - 1] : 0) + mod) % mod;
            prefix[i + 1] = (prefix[i] + dp[i + 1]) % mod;
        }

        return (int) dp[n];
    }
}

public class CountPartitionsWithMaxMinDifferenceAtMostK {

    public static void main(String[] args) {

        Solution sol = new Solution();

        int[] nums = {3, 3, 4};
        int k = 3;

        int result = sol.countPartitions(nums, k);
        System.out.println("Result: " + result);

        int[] nums2 = {9, 4, 1, 3, 7};
        int k2 = 4;
        int result2 = sol.countPartitions(nums2, k2);
        System.out.println("Result2: " + result2);
    }
}
