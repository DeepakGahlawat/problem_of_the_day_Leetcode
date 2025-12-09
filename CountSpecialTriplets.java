// You are given an integer array nums.

// A special triplet is defined as a triplet of indices (i, j, k) such that:

// 0 <= i < j < k < n, where n = nums.length
// nums[i] == nums[j] * 2
// nums[k] == nums[j] * 2
// Return the total number of special triplets in the array.

// Since the answer may be large, return it modulo 109 + 7.

 

// Example 1:

// Input: nums = [6,3,6]

// Output: 1

// Explanation:

// The only special triplet is (i, j, k) = (0, 1, 2), where:

// nums[0] = 6, nums[1] = 3, nums[2] = 6
// nums[0] = nums[1] * 2 = 3 * 2 = 6
// nums[2] = nums[1] * 2 = 3 * 2 = 6
// Example 2:

// Input: nums = [0,1,0,0]

// Output: 1

// Explanation:

// The only special triplet is (i, j, k) = (0, 2, 3), where:

// nums[0] = 0, nums[2] = 0, nums[3] = 0
// nums[0] = nums[2] * 2 = 0 * 2 = 0
// nums[3] = nums[2] * 2 = 0 * 2 = 0
// Example 3:

// Input: nums = [8,4,2,8,4]

// Output: 2

// Explanation:

// There are exactly two special triplets:

// (i, j, k) = (0, 1, 3)
// nums[0] = 8, nums[1] = 4, nums[3] = 8
// nums[0] = nums[1] * 2 = 4 * 2 = 8
// nums[3] = nums[1] * 2 = 4 * 2 = 8
// (i, j, k) = (1, 2, 4)
// nums[1] = 4, nums[2] = 2, nums[4] = 4
// nums[1] = nums[2] * 2 = 2 * 2 = 4
// nums[4] = nums[2] * 2 = 2 * 2 = 4
 

// Constraints:

// 3 <= n == nums.length <= 105
// 0 <= nums[i] <= 105

import java.util.*;

class CountSpecialTriplets {

    public int specialTriplets(int[] arr) {
        int mod = (int) 1e9 + 7;
        HashMap<Integer, Integer> fromLast = new HashMap<>();
        HashMap<Integer, Integer> fromFirst = new HashMap<>();
        int n = arr.length;

        for (int i = 0; i < n; i++) {
            fromLast.put(arr[i], fromLast.getOrDefault(arr[i], 0) + 1);
        }

        long count = 0;

        for (int i = 0; i < n; i++) {
            fromLast.put(arr[i], fromLast.get(arr[i]) - 1);

            int start = fromFirst.containsKey(arr[i] * 2)
                    ? fromFirst.get(arr[i] * 2) : 0;

            int last = fromLast.containsKey(arr[i] * 2)
                    ? fromLast.get(arr[i] * 2) : 0;

            count = (count + ((long) start * last) % mod) % mod;

            fromFirst.put(arr[i], fromFirst.getOrDefault(arr[i], 0) + 1);
        }

        return (int) count;
    }

    // ✅ MAIN METHOD WITH ALL TEST CASES
    public static void main(String[] args) {
        CountSpecialTriplets sol = new CountSpecialTriplets();

        int[][] testCases = {
                {6, 3, 6},
                {0, 1, 0, 0},
                {8, 4, 2, 8, 4}
        };

        int[] expected = {1, 1, 2};

        for (int i = 0; i < testCases.length; i++) {
            int result = sol.specialTriplets(testCases[i]);
            System.out.println(
                "Test Case " + (i + 1) + ": " +
                Arrays.toString(testCases[i]) +
                " | Output = " + result +
                " | Expected = " + expected[i]
            );
        }
    }
}
