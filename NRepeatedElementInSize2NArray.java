/*
    ============================================================
        N-REPEATED ELEMENT IN SIZE 2N ARRAY
    ============================================================

    🧠 INTUITION
    ------------------------------------------------------------
    The array has the following special properties:
        - Size = 2 * n
        - Exactly ONE element is repeated n times
        - All other elements are unique

    Because the repeated element appears so frequently,
    it MUST appear at least twice within a distance of
    1 or 2 positions in the array.

    ------------------------------------------------------------
    🧩 KEY OBSERVATION
    ------------------------------------------------------------
    We do NOT need extra space (HashSet / Map).

    If an element is repeated n times in a 2n-sized array,
    then:
        nums[i] == nums[i + 1]   OR
        nums[i] == nums[i + 2]
    must be true for some i.

    ------------------------------------------------------------
    🧮 APPROACH
    ------------------------------------------------------------
    1️⃣ Traverse the array from left to right
    2️⃣ For each index i:
         - Check nums[i] == nums[i + 1]
         - Check nums[i] == nums[i + 2]
    3️⃣ If match found → return nums[i]
    4️⃣ If nothing matches, return the last element

    ------------------------------------------------------------
    ⏱️ TIME & SPACE COMPLEXITY
    ------------------------------------------------------------
    Time Complexity:
        O(n)

    Space Complexity:
        O(1)

    ------------------------------------------------------------
    ✔ Extremely optimized solution
      Avoids HashSet / sorting
      Frequently asked interview problem
    ============================================================
*/

import java.util.*;

public class NRepeatedElementInSize2NArray {

    public int repeatedNTimes(int[] nums) {

        // Check neighbors and second neighbors
        for (int i = 0; i < nums.length - 1; i++) {

            // Case 1: immediate repetition
            if (nums[i] == nums[i + 1]) {
                return nums[i];
            }

            // Case 2: repetition with one element gap
            if (i + 2 < nums.length && nums[i] == nums[i + 2]) {
                return nums[i];
            }
        }

        // Fallback (guaranteed to exist)
        return nums[nums.length - 1];
    }

    // ============================================================
    //                         MAIN METHOD
    // ============================================================
    public static void main(String[] args) {

        NRepeatedElementInSize2NArray solver =
                new NRepeatedElementInSize2NArray();

        int[] nums1 = {1, 2, 3, 3};
        System.out.println(solver.repeatedNTimes(nums1)); // 3

        int[] nums2 = {2, 1, 2, 5, 3, 2};
        System.out.println(solver.repeatedNTimes(nums2)); // 2

        int[] nums3 = {5, 1, 5, 2, 5, 3, 5, 4};
        System.out.println(solver.repeatedNTimes(nums3)); // 5
    }
}
