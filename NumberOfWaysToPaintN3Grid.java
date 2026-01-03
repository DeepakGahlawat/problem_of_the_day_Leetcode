/*
    ============================================================
            NUMBER OF WAYS TO PAINT N × 3 GRID
    ============================================================

    🧠 INTUITION
    ------------------------------------------------------------
    We have an n × 3 grid and 3 colors:
        Red, Yellow, Green

    Rules:
        - No two adjacent cells (horizontal or vertical)
          can have the same color.

    Brute force is impossible for n up to 5000.
    We need a Dynamic Programming solution.

    ------------------------------------------------------------
    🧩 KEY OBSERVATION (Row Patterns)
    ------------------------------------------------------------
    For each row, valid colorings fall into TWO categories:

    1️⃣ TYPE X (ABC pattern):
        - All 3 cells have DIFFERENT colors
        - Example: RYG, GRY
        - Count for first row = 6

    2️⃣ TYPE Y (ABA pattern):
        - First and third same, middle different
        - Example: RGR, YRY
        - Count for first row = 6

    Let:
        x = number of ways for TYPE X rows
        y = number of ways for TYPE Y rows

    ------------------------------------------------------------
    🔁 TRANSITIONS BETWEEN ROWS
    ------------------------------------------------------------
    From previous row:

    New X patterns can be formed from:
        - 3 ways from previous X
        - 2 ways from previous Y

        nx = 3*x + 2*y

    New Y patterns can be formed from:
        - 2 ways from previous X
        - 2 ways from previous Y

        ny = 2*x + 2*y

    ------------------------------------------------------------
    🧮 APPROACH
    ------------------------------------------------------------
    1️⃣ Initialize x = 6, y = 6 for the first row
    2️⃣ Repeat transitions for remaining (n-1) rows
    3️⃣ Final answer = (x + y) % MOD

    ------------------------------------------------------------
    ⏱️ TIME & SPACE COMPLEXITY
    ------------------------------------------------------------
    Time Complexity:
        O(n)

    Space Complexity:
        O(1)

    ------------------------------------------------------------
    ✔ Highly optimized DP solution
      Common LeetCode hard-ish DP problem
    ============================================================
*/

public class NumberOfWaysToPaintN3Grid {

    public int numOfWays(int n) {

        // x → patterns with all 3 different colors (ABC)
        // y → patterns with first & third same (ABA)
        long x = 6, y = 6;

        int mod = 1_000_000_007;

        // Build solution row by row
        for (int i = 1; i < n; i++) {

            long nx = (3 * x + 2 * y) % mod;
            long ny = (2 * x + 2 * y) % mod;

            x = nx;
            y = ny;
        }

        return (int) ((x + y) % mod);
    }

    // ============================================================
    //                         MAIN METHOD
    // ============================================================
    public static void main(String[] args) {

        NumberOfWaysToPaintN3Grid solver =
                new NumberOfWaysToPaintN3Grid();

        System.out.println(solver.numOfWays(1));    // 12
        System.out.println(solver.numOfWays(2));    // 54
        System.out.println(solver.numOfWays(5000)); // 30228214
    }
}
