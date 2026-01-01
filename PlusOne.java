/*
    ============================================================
                            PLUS ONE
    ============================================================

    🧠 INTUITION
    ------------------------------------------------------------
    The number is represented as an array of digits where:
        - Most significant digit is at index 0
        - Least significant digit is at the last index

    To add 1:
        - Start from the last digit
        - If digit < 9 → simply increment and return
        - If digit == 9 → it becomes 0 and carry continues

    If all digits are 9:
        - Result will have one extra digit (e.g., 999 → 1000)

    ------------------------------------------------------------
    🧮 APPROACH
    ------------------------------------------------------------
    1️⃣ Traverse digits from right to left
    2️⃣ If digits[i] < 9:
         - Increment and return array
    3️⃣ Else:
         - Set digit to 0 and continue
    4️⃣ If loop finishes:
         - Create new array with size n+1
         - Set first digit to 1

    ------------------------------------------------------------
    ⏱️ TIME & SPACE COMPLEXITY
    ------------------------------------------------------------
    Time Complexity:
        O(n)

    Space Complexity:
        O(n) in worst case (when all digits are 9)
        Otherwise O(1)

    ------------------------------------------------------------
    ✔ Simple carry-propagation problem
      Very common entry-level interview question
    ============================================================
*/

import java.util.*;

public class PlusOne {

    public int[] plusOne(int[] digits) {

        int n = digits.length;

        // Traverse from least significant digit
        for (int i = n - 1; i >= 0; i--) {

            // If digit is less than 9, just increment and return
            if (digits[i] < 9) {
                digits[i]++;
                return digits;
            }

            // Digit is 9 → becomes 0, carry continues
            digits[i] = 0;
        }

        // If all digits were 9, create new array
        int[] res = new int[n + 1];
        res[0] = 1;

        return res;
    }

    // ============================================================
    //                         MAIN METHOD
    // ============================================================
    public static void main(String[] args) {

        PlusOne solver = new PlusOne();

        int[] d1 = {1, 2, 3};
        System.out.println(Arrays.toString(solver.plusOne(d1))); // [1, 2, 4]

        int[] d2 = {4, 3, 2, 1};
        System.out.println(Arrays.toString(solver.plusOne(d2))); // [4, 3, 2, 2]

        int[] d3 = {9};
        System.out.println(Arrays.toString(solver.plusOne(d3))); // [1, 0]

        int[] d4 = {9, 9, 9};
        System.out.println(Arrays.toString(solver.plusOne(d4))); // [1, 0, 0, 0]
    }
}
