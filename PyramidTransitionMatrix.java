/*
    ============================================================
                PYRAMID TRANSITION MATRIX
    ============================================================

    🧠 INTUITION
    ------------------------------------------------------------
    We are building a pyramid of blocks where:
        - Each upper row has exactly one block less
        - Each block is formed from two adjacent blocks below it
        - Only certain (left, right -> top) patterns are allowed

    Starting from the bottom row, we must check whether
    it is POSSIBLE to build all the way up to a single block.

    ------------------------------------------------------------
    🧩 KEY OBSERVATIONS
    ------------------------------------------------------------
    1️⃣ For every adjacent pair in the current row, we may have
        MULTIPLE possible blocks that can be placed above it
    2️⃣ This leads to a TREE of possibilities → backtracking
    3️⃣ Many subproblems repeat → use MEMOIZATION

    Bottom length ≤ 6 → exponential branching is manageable
    with pruning + memo.

    ------------------------------------------------------------
    🧮 APPROACH (DFS + BACKTRACKING + MEMO)
    ------------------------------------------------------------
    1️⃣ Preprocess `allowed` into a map:
         "AB" → list of possible top blocks

    2️⃣ Use recursive function `solve(bottom)`:
         - If bottom length == 1 → success
         - Try to build the next row using DFS
         - Memoize result for pruning

    3️⃣ Use helper `getNextBottom` to:
         - Generate all possible next rows
         - Try each recursively

    ------------------------------------------------------------
    ⏱️ TIME & SPACE COMPLEXITY
    ------------------------------------------------------------
    Let L = bottom.length (≤ 6)

    Time Complexity:
        Exponential in worst case, but heavily pruned
        ≈ O(6^6) (bounded & acceptable)

    Space Complexity:
        O(number of unique bottom states) for memo

    ------------------------------------------------------------
    ✔ Classic recursion + memoization problem
      Frequently asked LeetCode / interview problem
    ============================================================
*/

import java.util.*;

public class PyramidTransitionMatrix {

    // Map: "AB" -> list of possible top characters
    Map<String, List<Character>> map = new HashMap<>();

    // Memoization for already-checked bottom strings
    Map<String, Boolean> memo = new HashMap<>();

    public boolean pyramidTransition(String bottom, List<String> allowed) {

        // Build mapping from allowed patterns
        for (String s : allowed) {
            String key = s.substring(0, 2);
            map.computeIfAbsent(key, k -> new ArrayList<>())
               .add(s.charAt(2));
        }

        return solve(bottom);
    }

    // Recursively checks if pyramid can be built from this bottom
    boolean solve(String bottom) {

        // Base case: reached top
        if (bottom.length() == 1) return true;

        // Memoized result
        if (memo.containsKey(bottom)) return memo.get(bottom);

        boolean result = getNextBottom(bottom, 0, new StringBuilder());
        memo.put(bottom, result);
        return result;
    }

    // Builds next row using DFS
    boolean getNextBottom(String bottom, int ind, StringBuilder currNext) {

        // Finished building next row
        if (ind == bottom.length() - 1) {
            return solve(currNext.toString());
        }

        String key = bottom.substring(ind, ind + 2);

        // No allowed pattern → dead end
        if (!map.containsKey(key)) return false;

        // Try all possible top blocks
        for (char val : map.get(key)) {
            currNext.append(val);

            if (getNextBottom(bottom, ind + 1, currNext)) {
                return true;
            }

            // Backtrack
            currNext.deleteCharAt(currNext.length() - 1);
        }

        return false;
    }

    // ============================================================
    //                         MAIN METHOD
    // ============================================================
    public static void main(String[] args) {

        PyramidTransitionMatrix solver =
                new PyramidTransitionMatrix();

        String bottom1 = "BCD";
        List<String> allowed1 =
                Arrays.asList("BCC", "CDE", "CEA", "FFF");

        System.out.println(
                solver.pyramidTransition(bottom1, allowed1)
        ); // true

        String bottom2 = "AAAA";
        List<String> allowed2 =
                Arrays.asList("AAB", "AAC", "BCD", "BBE", "DEF");

        System.out.println(
                solver.pyramidTransition(bottom2, allowed2)
        ); // false
    }
}
