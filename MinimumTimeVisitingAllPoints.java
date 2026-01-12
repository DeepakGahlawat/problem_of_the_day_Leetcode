/*
========================================================
Class Name: Minimum Time Visiting All Points
========================================================

Intuition:
-----------
You are allowed to move:
- Horizontally (1 unit per second)
- Vertically (1 unit per second)
- Diagonally (1 unit horizontally + 1 unit vertically in 1 second)

Key Observation:
----------------
When moving from point A(x1, y1) to point B(x2, y2):
- Horizontal distance = |x2 - x1|
- Vertical distance   = |y2 - y1|

In one second, a diagonal move reduces BOTH horizontal and vertical
distance by 1.

So:
- First, take as many diagonal moves as possible → min(horizontal, vertical)
- Remaining distance must be covered by straight moves

Hence, minimum time required is:
    max(|x2 - x1|, |y2 - y1|)

Your code computes this implicitly by:
    hor + ver  (split into diagonal + remaining straight moves)

Approach:
---------
1. Traverse points in order.
2. For each consecutive pair:
   - Compute horizontal and vertical distances.
   - Add the maximum of the two to the total time.
3. Accumulate the total time.

Time Complexity:
----------------
O(n), where n = number of points

Space Complexity:
-----------------
O(1), constant extra space

========================================================
*/

class MinimumTimeVisitingAllPoints {

    public int minTimeToVisitAllPoints(int[][] points) {
        int count = 0;

        // Traverse consecutive points
        for (int i = 1; i < points.length; i++) {

            // Horizontal and vertical distances
            int hor = Math.abs(points[i][0] - points[i - 1][0]);
            int ver = Math.abs(points[i][1] - points[i - 1][1]);

            // Total time = max(hor, ver)
            if (hor < ver)
                count += hor + (ver - hor);
            else
                count += ver + (hor - ver);
        }
        return count;
    }

    // Main method for testing
    public static void main(String[] args) {
        MinimumTimeVisitingAllPoints sol = new MinimumTimeVisitingAllPoints();

        int[][] points1 = {{1,1},{3,4},{-1,0}};
        int[][] points2 = {{3,2},{-2,2}};

        System.out.println(sol.minTimeToVisitAllPoints(points1)); // 7
        System.out.println(sol.minTimeToVisitAllPoints(points2)); // 5
    }
}
