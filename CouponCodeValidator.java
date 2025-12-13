// You are given three arrays of length n that describe the properties of n coupons: code, businessLine, and isActive. The ith coupon has:

// code[i]: a string representing the coupon identifier.
// businessLine[i]: a string denoting the business category of the coupon.
// isActive[i]: a boolean indicating whether the coupon is currently active.
// A coupon is considered valid if all of the following conditions hold:

// code[i] is non-empty and consists only of alphanumeric characters (a-z, A-Z, 0-9) and underscores (_).
// businessLine[i] is one of the following four categories: "electronics", "grocery", "pharmacy", "restaurant".
// isActive[i] is true.
// Return an array of the codes of all valid coupons, sorted first by their businessLine in the order: "electronics", "grocery", "pharmacy", "restaurant", and then by code in lexicographical (ascending) order within each category.

 

// Example 1:

// Input: code = ["SAVE20","","PHARMA5","SAVE@20"], businessLine = ["restaurant","grocery","pharmacy","restaurant"], isActive = [true,true,true,true]

// Output: ["PHARMA5","SAVE20"]

// Explanation:

// First coupon is valid.
// Second coupon has empty code (invalid).
// Third coupon is valid.
// Fourth coupon has special character @ (invalid).
// Example 2:

// Input: code = ["GROCERY15","ELECTRONICS_50","DISCOUNT10"], businessLine = ["grocery","electronics","invalid"], isActive = [false,true,true]

// Output: ["ELECTRONICS_50"]

// Explanation:

// First coupon is inactive (invalid).
// Second coupon is valid.
// Third coupon has invalid business line (invalid).
 

// Constraints:

// n == code.length == businessLine.length == isActive.length
// 1 <= n <= 100
// 0 <= code[i].length, businessLine[i].length <= 100
// code[i] and businessLine[i] consist of printable ASCII characters.
// isActive[i] is either true or false.

/*
    ============================================================
                    COUPON CODE VALIDATOR
    ============================================================

    🧠 PROBLEM INTUITION
    ------------------------------------------------------------
    Each coupon is described by three attributes:
        - code[i]         → coupon identifier
        - businessLine[i] → category of coupon
        - isActive[i]     → whether coupon is active

    A coupon is VALID only if:
        1️⃣ code is non-empty
        2️⃣ code contains only:
              - a-z, A-Z, 0-9, underscore (_)
        3️⃣ businessLine belongs to one of:
              "electronics", "grocery", "pharmacy", "restaurant"
        4️⃣ isActive is true

    ------------------------------------------------------------
    🎯 REQUIRED OUTPUT
    ------------------------------------------------------------
    - Collect all VALID coupon codes
    - Sort them first by businessLine priority:
          electronics → grocery → pharmacy → restaurant
    - Within the same businessLine, sort lexicographically

    ------------------------------------------------------------
    🧩 APPROACH
    ------------------------------------------------------------
    1️⃣ Use a HashMap to assign priority index to each businessLine
    2️⃣ Maintain 4 lists (one for each businessLine)
    3️⃣ Traverse all coupons:
         - Validate each coupon
         - Add valid codes to their respective category list
    4️⃣ Sort each category list individually
    5️⃣ Combine results in required order

    ------------------------------------------------------------
    ⏱️ TIME & SPACE COMPLEXITY
    ------------------------------------------------------------
    Time Complexity  : O(n log n)
    Space Complexity : O(n)

    ------------------------------------------------------------
    ✔ Efficient and clean solution for given constraints
    ============================================================
*/

import java.util.*;

public class CouponCodeValidator {

    public List<String> validateCoupons(String[] code,
                                        String[] businessLine,
                                        boolean[] isActive) {

        int n = code.length;

        // Map business line to priority index
        HashMap<String, Integer> categoryIndex = new HashMap<>();
        categoryIndex.put("electronics", 0);
        categoryIndex.put("grocery", 1);
        categoryIndex.put("pharmacy", 2);
        categoryIndex.put("restaurant", 3);

        // Create 4 lists for 4 business lines
        ArrayList<ArrayList<String>> buckets = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            buckets.add(new ArrayList<>());
        }

        // Process each coupon
        for (int i = 0; i < n; i++) {

            // Validate coupon conditions
            if (isActive[i]
                    && code[i].length() != 0
                    && isValid(code[i])
                    && categoryIndex.containsKey(businessLine[i])) {

                // Add code to corresponding category bucket
                int idx = categoryIndex.get(businessLine[i]);
                buckets.get(idx).add(code[i]);
            }
        }

        // Final result list
        ArrayList<String> result = new ArrayList<>();

        // Sort each bucket and add to result in priority order
        for (int i = 0; i < 4; i++) {
            Collections.sort(buckets.get(i));
            result.addAll(buckets.get(i));
        }

        return result;
    }

    // Helper method to validate coupon code format
    boolean isValid(String s) {
        // Regex allows only alphanumeric characters and underscore
        return s.matches("^[a-zA-Z0-9_]+$");
    }

    // ============================================================
    //                         MAIN METHOD
    // ============================================================
    public static void main(String[] args) {

        CouponCodeValidator validator = new CouponCodeValidator();

        String[] code = {"SAVE20", "", "PHARMA5", "SAVE@20"};
        String[] businessLine = {"restaurant", "grocery", "pharmacy", "restaurant"};
        boolean[] isActive = {true, true, true, true};

        List<String> result = validator.validateCoupons(code, businessLine, isActive);

        System.out.println("Valid Coupon Codes:");
        System.out.println(result);
    }
}
