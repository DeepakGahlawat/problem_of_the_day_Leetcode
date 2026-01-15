import java.util.*;

/*
========================================================
Class Name: MaximizeAreaOfSquareHoleInGrid
========================================================

Intuition:
-----------
The grid is formed by horizontal and vertical bars.
Each time we remove consecutive bars, we effectively merge adjacent 1x1 cells
into a larger continuous block.

To form a SQUARE hole:
- We need consecutive removable horizontal bars
- AND consecutive removable vertical bars

The side of the largest square hole depends on:
1 + minimum(consecutive horizontal bars removed,
            consecutive vertical bars removed)

Why +1?
---------
Removing k consecutive bars merges (k + 1) unit cells.

Approach:
----------
1. Sort hBars and vBars.
2. Find the longest sequence of consecutive bar indices in hBars → maxLenH.
3. Find the longest sequence of consecutive bar indices in vBars → maxLenV.
4. The maximum square side length is:
      side = 1 + min(maxLenH, maxLenV)
5. Return side * side (area of the square).

Time Complexity:
----------------
O(H log H + V log V)
H = hBars.length, V = vBars.length

Space Complexity:
-----------------
O(1) extra space (ignoring sorting overhead)

========================================================
*/

class MaximizeAreaOfSquareHoleInGrid {

    public int maximizeSquareHoleArea(int n, int m, int[] hBars, int[] vBars) {

        // Sort removable bars
        Arrays.sort(hBars);
        Arrays.sort(vBars);

        // Find longest consecutive sequence in horizontal bars
        int maxLenH = 1;
        int currLength = 1;

        for (int i = 1; i < hBars.length; i++) {
            if (hBars[i] == hBars[i - 1] + 1) {
                currLength++;
                maxLenH = Math.max(maxLenH, currLength);
            } else {
                currLength = 1;
            }
        }

        // Find longest consecutive sequence in vertical bars
        int maxLenV = 1;
        currLength = 1;

        for (int i = 1; i < vBars.length; i++) {
            if (vBars[i] == vBars[i - 1] + 1) {
                currLength++;
                maxLenV = Math.max(maxLenV, currLength);
            } else {
                currLength = 1;
            }
        }

        // Side of square = 1 + min(horizontal, vertical)
        int side = 1 + Math.min(maxLenH, maxLenV);

        // Area of square
        return side * side;
    }

    // ===================== MAIN METHOD =====================
    public static void main(String[] args) {
        MaximizeAreaOfSquareHoleInGrid sol =
                new MaximizeAreaOfSquareHoleInGrid();

        int[] h1 = {2, 3};
        int[] v1 = {2};
        System.out.println(sol.maximizeSquareHoleArea(2, 1, h1, v1)); // 4

        int[] h2 = {2};
        int[] v2 = {2};
        System.out.println(sol.maximizeSquareHoleArea(1, 1, h2, v2)); // 4

        int[] h3 = {2, 3};
        int[] v3 = {2, 4};
        System.out.println(sol.maximizeSquareHoleArea(2, 3, h3, v3)); // 4
    }
}
