/*
    ======================== PROBLEM STATEMENT ============================

    You are given a positive integer n, representing an n x n city.
    You are also given an array buildings, where buildings[i] = [x, y]
    represents a building located at coordinates (x, y).

    A building is considered COVERED if there exists:
        - At least one building to its LEFT  (same row, column < y)
        - At least one building to its RIGHT (same row, column > y)
        - At least one building ABOVE        (same column, row < x)
        - At least one building BELOW        (same column, row > x)

    Return the total count of such covered buildings.

    ----------------------------------------------------------------------
    Example 1:

    Input:  n = 3,
            buildings = [[1,2],[2,2],[3,2],[2,1],[2,3]]
    Output: 1

    Explanation:
        Only building (2,2) has:
          above → (1,2)
          below → (3,2)
          left  → (2,1)
          right → (2,3)

    ----------------------------------------------------------------------
    Example 2:

    Input:  n = 3,
            buildings = [[1,1],[1,2],[2,1],[2,2]]
    Output: 0

    No building has neighbors in all four directions.

    ----------------------------------------------------------------------
    Example 3:

    Input:  n = 5,
            buildings = [[1,3],[3,2],[3,3],[3,5],[5,3]]
    Output: 1

    (3,3) is the only covered building.

    ======================================================================
    ============================ APPROACH =================================

    For each row and column, we track:
        rowmin[x] = smallest column where a building exists in row x
        rowmax[x] = largest  column where a building exists in row x
        colmin[y] = smallest row where a building exists in column y
        colmax[y] = largest  row where a building exists in column y

    For a building (x, y):

        It has a LEFT  neighbor if rowmin[x] < y  
        It has a RIGHT neighbor if rowmax[x] > y  
        It has an UP    neighbor if colmin[y] < x  
        It has a DOWN  neighbor if colmax[y] > x  

    If all 4 conditions are true → The building is covered.

    ======================================================================
    ========================= TIME COMPLEXITY =============================

    Let m = number of buildings.

    Step 1 → Iterate over all buildings to fill min/max arrays → O(m)  
    Step 2 → Iterate again to count covered buildings           → O(m)

    Total Time = O(m)

    ======================================================================
    ========================= SPACE COMPLEXITY ============================

    We maintain four arrays of size n:

        rowmin[n], rowmax[n], colmin[n], colmax[n]

    Space = O(n)

    ======================================================================
*/

import java.util.Arrays;

public class CountCoveredBuildings {

    public int countCoveredBuildings(int n, int[][] buildings) {

        int[] rowmin = new int[n];
        int[] rowmax = new int[n];
        int[] colmin = new int[n];
        int[] colmax = new int[n];

        Arrays.fill(rowmin, n);
        Arrays.fill(colmax, -1);
        Arrays.fill(rowmax, -1);
        Arrays.fill(colmin, n);

        // STEP 1: Compute min and max building positions per row & column
        for (int[] building : buildings) {
            int x = building[0] - 1;  // convert to 0-index
            int y = building[1] - 1;

            rowmin[x] = Math.min(rowmin[x], y);
            rowmax[x] = Math.max(rowmax[x], y);

            colmin[y] = Math.min(colmin[y], x);
            colmax[y] = Math.max(colmax[y], x);
        }

        int count = 0;

        // STEP 2: Check each building if it's covered
        for (int[] building : buildings) {
            int x = building[0] - 1;
            int y = building[1] - 1;

            boolean hasLeft  = rowmin[x] < y;
            boolean hasRight = rowmax[x] > y;
            boolean hasUp    = colmin[y] < x;
            boolean hasDown  = colmax[y] > x;

            if (hasLeft && hasRight && hasUp && hasDown)
                count++;
        }

        return count;
    }

    // ========================== MAIN METHOD ==============================
    public static void main(String[] args) {

        CountCoveredBuildings obj = new CountCoveredBuildings();

        // Test Case 1
        int[][] b1 = {{1,2},{2,2},{3,2},{2,1},{2,3}};
        System.out.println("Test 1 Output: " + obj.countCoveredBuildings(3, b1));
        // Expected: 1

        // Test Case 2
        int[][] b2 = {{1,1},{1,2},{2,1},{2,2}};
        System.out.println("Test 2 Output: " + obj.countCoveredBuildings(3, b2));
        // Expected: 0

        // Test Case 3
        int[][] b3 = {{1,3},{3,2},{3,3},{3,5},{5,3}};
        System.out.println("Test 3 Output: " + obj.countCoveredBuildings(5, b3));
        // Expected: 1
    }
}
