/*
========================================================
Class Name: Separate Squares I
========================================================

Intuition:
-----------
We are given multiple axis-aligned squares on a 2D plane.
Our goal is to find the minimum y-coordinate of a horizontal line
such that:

    Total area of squares above the line
    ==
    Total area of squares below the line

Important Notes:
----------------
- Squares may overlap, and overlapping areas are counted multiple times.
- The line can cut through squares.
- We need an answer with precision up to 1e-5.

Key Observation:
----------------
As we move the horizontal line upward:
- Area below the line monotonically increases.
- Area above the line monotonically decreases.

This monotonic behavior allows us to use **Binary Search** on the y-coordinate.

Approach:
---------
1. Determine the search space:
   - Lowest possible y = minimum bottom y of all squares
   - Highest possible y = maximum (y + side length) of all squares

2. Binary search on y:
   - For a candidate line `mid`, compute:
     • Area below the line
     • Area above the line
   - If areaBelow >= areaAbove:
       → We can move the line downward to find a smaller valid y
   - Else:
       → Move the line upward

3. Stop when the search range is within 1e-5.

Time Complexity:
----------------
Let n = number of squares
Binary search iterations ≈ log(range / precision) ≈ constant
Each check is O(n)

Overall: O(n * log precision)

Space Complexity:
-----------------
O(1) extra space

========================================================
*/

class SeparateSquaresI {

    public double separateSquares(int[][] squares) {
        double low = 1e18;
        double high = -1e18;

        // Determine search boundaries
        for (int[] curr : squares) {
            int y = curr[1];
            int l = curr[2];
            low = Math.min(low, y);
            high = Math.max(high, y + l);
        }

        double ans = high;

        // Binary search on y-coordinate
        while (high - low > 1e-5) {
            double mid = low + (high - low) / 2.0;

            if (isPossible(mid, squares)) {
                ans = mid;      // valid line, try lower
                high = mid;
            } else {
                low = mid;     // need to move line up
            }
        }
        return ans;
    }

    // Checks whether area below >= area above for given line
    boolean isPossible(double line, int[][] squares) {
        double areaAbove = 0, areaBelow = 0;

        for (int[] curr : squares) {
            int y = curr[1];
            int l = curr[2];

            // Entire square below the line
            if (y + l <= line) {
                areaBelow += 1.0 * l * l;
            }
            // Entire square above the line
            else if (y >= line) {
                areaAbove += 1.0 * l * l;
            }
            // Square is cut by the line
            else {
                areaBelow += 1.0 * l * (line - y);
                areaAbove += 1.0 * l * ((y + l) - line);
            }
        }
        return areaBelow >= areaAbove;
    }

    // Main method for testing
    public static void main(String[] args) {
        SeparateSquaresI sol = new SeparateSquaresI();

        int[][] squares1 = {{0,0,1},{2,2,1}};
        int[][] squares2 = {{0,0,2},{1,1,1}};

        System.out.printf("%.5f\n", sol.separateSquares(squares1)); // 1.00000
        System.out.printf("%.5f\n", sol.separateSquares(squares2)); // 1.16667
    }
}
