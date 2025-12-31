/*
    ============================================================
            LAST DAY WHERE YOU CAN STILL CROSS
    ============================================================

    🧠 INTUITION
    ------------------------------------------------------------
    Each day, one land cell becomes water.
    We want to find the LAST day when a path of LAND exists
    from the TOP row to the BOTTOM row.

    Instead of simulating flooding forward (hard to track paths),
    we reverse the process:

        👉 Start from the LAST day (all water)
        👉 Add land cells BACK day-by-day
        👉 The FIRST time top connects to bottom
            → that day is the answer

    ------------------------------------------------------------
    🧩 KEY IDEA (DSU / UNION-FIND)
    ------------------------------------------------------------
    - Treat each cell as a node
    - Convert 2D cell (r, c) → 1D index = r * col + c
    - Use two VIRTUAL nodes:
        TOP    → connected to all land in first row
        BOTTOM → connected to all land in last row

    When TOP and BOTTOM become connected,
    crossing is possible.

    ------------------------------------------------------------
    🧮 APPROACH
    ------------------------------------------------------------
    1️⃣ Initialize DSU with (row * col + 2) nodes
    2️⃣ Traverse days in REVERSE order
    3️⃣ Convert flooded cell → land
    4️⃣ Union with all 4-direction land neighbors
    5️⃣ Union with TOP / BOTTOM if in first / last row
    6️⃣ If TOP and BOTTOM are connected → return day

    ------------------------------------------------------------
    ⏱️ TIME & SPACE COMPLEXITY
    ------------------------------------------------------------
    Let N = row * col

    Time Complexity:
        O(N * α(N))   (almost linear, DSU optimized)

    Space Complexity:
        O(N)

    ------------------------------------------------------------
    ✔ Very common FAANG / LeetCode DSU problem
    ✔ Reverse-thinking is the key insight
    ============================================================
*/

import java.util.*;

public class LastDayWhereYouCanStillCross {

    int[] parent, rank;
    int TOP, BOTTOM;

    public int latestDayToCross(int row, int col, int[][] cells) {

        int n = row * col;

        // Virtual nodes
        TOP = n;
        BOTTOM = n + 1;

        parent = new int[n + 2];
        rank = new int[n + 2];

        // DSU initialization
        for (int i = 0; i < n + 2; i++) parent[i] = i;

        // Tracks land cells (false = water initially)
        boolean[][] land = new boolean[row][col];

        // 4-direction movement
        int[] dr = {1, 0, 0, -1};
        int[] dc = {0, 1, -1, 0};

        // Traverse days in reverse
        for (int day = cells.length - 1; day >= 0; day--) {

            int r = cells[day][0] - 1;
            int c = cells[day][1] - 1;

            land[r][c] = true;

            // Convert 2D → 1D
            int id = r * col + c;

            // Union with neighboring land cells
            for (int i = 0; i < 4; i++) {
                int nr = r + dr[i];
                int nc = c + dc[i];

                if (nr >= 0 && nc >= 0 && nr < row && nc < col && land[nr][nc]) {
                    union(id, nr * col + nc);
                }
            }

            // Connect to virtual TOP / BOTTOM
            if (r == 0) union(id, TOP);
            if (r == row - 1) union(id, BOTTOM);

            // Check connectivity
            if (find(TOP) == find(BOTTOM)) {
                return day;
            }
        }

        return 0;
    }

    // Find with path compression
    int find(int x) {
        if (parent[x] != x)
            parent[x] = find(parent[x]);
        return parent[x];
    }

    // Union by rank
    void union(int x, int y) {
        int px = find(x);
        int py = find(y);

        if (px == py) return;

        if (rank[px] < rank[py]) parent[px] = py;
        else if (rank[px] > rank[py]) parent[py] = px;
        else {
            parent[py] = px;
            rank[px]++;
        }
    }

    // ============================================================
    //                         MAIN METHOD
    // ============================================================
    public static void main(String[] args) {

        LastDayWhereYouCanStillCross solver =
                new LastDayWhereYouCanStillCross();

        int[][] cells1 = {
                {1,1},{2,1},{1,2},{2,2}
        };

        System.out.println(
                solver.latestDayToCross(2, 2, cells1)
        ); // Output: 2

        int[][] cells2 = {
                {1,1},{1,2},{2,1},{2,2}
        };

        System.out.println(
                solver.latestDayToCross(2, 2, cells2)
        ); // Output: 1

        int[][] cells3 = {
                {1,2},{2,1},{3,3},{2,2},{1,1},
                {1,3},{2,3},{3,2},{3,1}
        };

        System.out.println(
                solver.latestDayToCross(3, 3, cells3)
        ); // Output: 3
    }
}
