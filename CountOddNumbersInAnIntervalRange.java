// Given two non-negative integers low and high. Return the count of odd numbers between low and high (inclusive).

 

// Example 1:

// Input: low = 3, high = 7
// Output: 3
// Explanation: The odd numbers between 3 and 7 are [3,5,7].
// Example 2:

// Input: low = 8, high = 10
// Output: 1
// Explanation: The odd numbers between 8 and 10 are [9].
 

// Constraints:

// 0 <= low <= high <= 10^9

import java.util.*;

public class CountOddNumbersInAnIntervalRange {

    public int countOdds(int low, int high) {
        int total = high - low + 1;
        if (low % 2 == 1 && high % 2 == 1) return total / 2 + 1;
        return total / 2;
    }

    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter low: ");
        int low = sc.nextInt();

        System.out.print("Enter high: ");
        int high = sc.nextInt();

        CountOddNumbersInAnIntervalRange obj = new CountOddNumbersInAnIntervalRange();
        int result = obj.countOdds(low, high);

        System.out.println("Number of odd numbers: " + result);

        sc.close();
    }
}
