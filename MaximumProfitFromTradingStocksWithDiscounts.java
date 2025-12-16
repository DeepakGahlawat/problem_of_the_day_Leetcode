/*
    =====================================================================
        MAXIMUM PROFIT FROM TRADING STOCKS WITH DISCOUNTS
    =====================================================================

    🧠 INTUITION
    ---------------------------------------------------------------------
    The company hierarchy forms a TREE:
        - Each node represents an employee / stock
        - Root (employee 1) is superior of all
        - hierarchy is acyclic → Tree DP problem

    Each stock has:
        present[u] → cost to buy normally
        future[u]  → selling value in future

    RULE:
        - If parent stock is bought, child stock can be bought at DISCOUNT
        - Discounted cost = present[u] / 2
        - Total budget is limited

    GOAL:
        Maximize total profit under the given budget.

    ---------------------------------------------------------------------
    🧩 CORE IDEA (Tree + Knapsack DP)
    ---------------------------------------------------------------------
    For each node u, maintain two DP arrays:

        dp0[b] → max profit using budget b
                 when parent of u is NOT purchased

        dp1[b] → max profit using budget b
                 when parent of u IS purchased (discount allowed)

    Why two states?
        Because availability of discount depends on whether
        parent stock was purchased or not.

    ---------------------------------------------------------------------
    🧠 HOW DFS WORKS
    ---------------------------------------------------------------------
    1️⃣ Build tree from hierarchy
    2️⃣ DFS from root
    3️⃣ For each node:
         - Merge DP results of children (Knapsack merge)
         - Decide whether to buy current node or skip
         - Update dp0 and dp1 accordingly

    ---------------------------------------------------------------------
    ⏱️ TIME & SPACE COMPLEXITY
    ---------------------------------------------------------------------
    Time Complexity:
        O(N × Budget²)   (tree knapsack merging)

    Space Complexity:
        O(N × Budget)    (DP arrays per node)

    ---------------------------------------------------------------------
    ✔ This is an ADVANCED Tree DP problem
      Frequently asked in FAANG-level interviews
    =====================================================================
*/

import java.util.*;

// Helper class to store DP results for each node
class Result {

    int[] dp0;   // parent NOT purchased
    int[] dp1;   // parent purchased (discount available)
    int size;    // total cost capacity of subtree

    Result(int[] dp0, int[] dp1, int size) {
        this.dp0 = dp0;
        this.dp1 = dp1;
        this.size = size;
    }
}

public class MaximumProfitFromTradingStocksWithDiscounts {

    public int maxProfit(
            int n,
            int[] present,
            int[] future,
            int[][] hierarchy,
            int budget
    ) {

        // Build adjacency list (tree)
        List<Integer>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        // hierarchy edges (1-based → 0-based)
        for (int[] e : hierarchy) {
            graph[e[0] - 1].add(e[1] - 1);
        }

        // DFS from root (employee 1 → index 0)
        return dfs(0, present, future, graph, budget).dp0[budget];
    }

    private Result dfs(
            int u,
            int[] present,
            int[] future,
            List<Integer>[] graph,
            int budget
    ) {

        int cost = present[u];          // normal cost
        int discountCost = cost / 2;    // discounted cost

        // dp arrays
        int[] dp0 = new int[budget + 1];
        int[] dp1 = new int[budget + 1];

        // Temporary arrays for merging children
        int[] subProfit0 = new int[budget + 1];
        int[] subProfit1 = new int[budget + 1];

        int subtreeSize = cost;

        // Process children
        for (int v : graph[u]) {

            Result child = dfs(v, present, future, graph, budget);
            subtreeSize += child.size;

            // Knapsack merge
            for (int i = budget; i >= 0; i--) {
                for (int b = 0; b <= Math.min(child.size, i); b++) {

                    subProfit0[i] = Math.max(
                            subProfit0[i],
                            subProfit0[i - b] + child.dp0[b]
                    );

                    subProfit1[i] = Math.max(
                            subProfit1[i],
                            subProfit1[i - b] + child.dp1[b]
                    );
                }
            }
        }

        // Decide for current node
        for (int b = 0; b <= budget; b++) {

            // Case 1: Do NOT buy current node
            dp0[b] = subProfit0[b];
            dp1[b] = subProfit0[b];

            // Case 2: Buy current node with DISCOUNT
            if (b >= discountCost) {
                dp1[b] = Math.max(
                        dp1[b],
                        subProfit1[b - discountCost] + future[u] - discountCost
                );
            }

            // Case 3: Buy current node WITHOUT discount
            if (b >= cost) {
                dp0[b] = Math.max(
                        dp0[b],
                        subProfit1[b - cost] + future[u] - cost
                );
            }
        }

        return new Result(dp0, dp1, subtreeSize);
    }

    // ============================================================
    //                          MAIN METHOD
    // ============================================================
    public static void main(String[] args) {

        MaximumProfitFromTradingStocksWithDiscounts solver =
                new MaximumProfitFromTradingStocksWithDiscounts();

        int n = 3;
        int[] present = {10, 5, 8};
        int[] future = {15, 7, 12};
        int[][] hierarchy = {
                {1, 2},
                {1, 3}
        };
        int budget = 10;

        System.out.println(
                "Maximum Profit = " +
                        solver.maxProfit(n, present, future, hierarchy, budget)
        );
    }
}
