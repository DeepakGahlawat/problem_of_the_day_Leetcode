/*
    ============================================================
                        FOUR DIVISORS
    ============================================================

    🧠 INTUITION
    ------------------------------------------------------------
    We are given an array of integers.
    For each number, we need to check:
        - Does it have EXACTLY 4 divisors?
        - If yes, add the SUM of its divisors to the answer

    Brute force divisor checking for each number would be too slow
    because nums[i] can go up to 10^5 and nums.length up to 10^4.

    ------------------------------------------------------------
    🧩 KEY IDEA (Sieve-based Precomputation)
    ------------------------------------------------------------
    Precompute for all numbers from 1 to 100000:
        - Number of divisors
        - Sum of divisors

    This is similar to the Sieve of Eratosthenes, but instead of
    marking primes, we:
        - Count divisors
        - Accumulate divisor sums

    ------------------------------------------------------------
    🧮 APPROACH
    ------------------------------------------------------------
    1️⃣ Initialize arrays:
         - counts[i] → number of divisors of i
         - sum[i]    → sum of divisors of i

    2️⃣ Start by assuming divisor 1 for all numbers

    3️⃣ For every i from 2 to 100000:
         - i divides itself → update count & sum
         - i divides all multiples of i

    4️⃣ Iterate through nums[]:
         - If counts[num] == 4 → add sum[num] to result

    ------------------------------------------------------------
    ⏱️ TIME & SPACE COMPLEXITY
    ------------------------------------------------------------
    Precomputation:
        O(N log N), where N = 100000

    Query processing:
        O(nums.length)

    Space Complexity:
        O(N)

    ------------------------------------------------------------
    ✔ Efficient precomputation-based solution
      Very common math + sieve interview problem
    ============================================================
*/

import java.util.*;

public class FourDivisors {

    public int sumFourDivisors(int[] nums) {

        int MAX = 100000;

        // counts[i] = number of divisors of i
        // sum[i]    = sum of divisors of i
        int[] counts = new int[MAX + 1];
        int[] sum = new int[MAX + 1];

        // Every number has divisor 1
        Arrays.fill(counts, 1);
        Arrays.fill(sum, 1);

        // Sieve-like divisor enumeration
        for (int i = 2; i <= MAX; i++) {

            // i divides itself
            counts[i]++;
            sum[i] += i;

            // i divides all multiples of i
            for (int p = i + i; p <= MAX; p += i) {
                counts[p]++;
                sum[p] += i;
            }
        }

        int res = 0;

        // Check given numbers
        for (int val : nums) {
            if (counts[val] == 4) {
                res += sum[val];
            }
        }

        return res;
    }

    // ============================================================
    //                         MAIN METHOD
    // ============================================================
    public static void main(String[] args) {

        FourDivisors solver = new FourDivisors();

        int[] nums1 = {21, 4, 7};
        System.out.println(solver.sumFourDivisors(nums1)); // 32

        int[] nums2 = {21, 21};
        System.out.println(solver.sumFourDivisors(nums2)); // 64

        int[] nums3 = {1, 2, 3, 4, 5};
        System.out.println(solver.sumFourDivisors(nums3)); // 0
    }
}
