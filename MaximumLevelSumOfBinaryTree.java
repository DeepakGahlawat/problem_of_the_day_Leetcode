/*
    ============================================================
            MAXIMUM LEVEL SUM OF A BINARY TREE
    ============================================================

    🧠 INTUITION
    ------------------------------------------------------------
    Each level of the binary tree has a certain sum of node values.
    Our task is to find the SMALLEST level number whose sum
    is MAXIMUM among all levels.

    Since levels are naturally processed from top to bottom,
    LEVEL ORDER TRAVERSAL (BFS) is the best fit.

    ------------------------------------------------------------
    🧩 KEY IDEA (BREADTH-FIRST SEARCH)
    ------------------------------------------------------------
    - Use a queue to traverse the tree level by level
    - For each level:
        * Compute sum of all nodes
        * Compare with maximum sum seen so far
    - Update answer only if current sum is STRICTLY greater
      (to ensure smallest level in case of tie)

    ------------------------------------------------------------
    🧮 APPROACH
    ------------------------------------------------------------
    1️⃣ Initialize queue with root node
    2️⃣ Track current level number
    3️⃣ For each level:
         - Process all nodes in the queue
         - Accumulate their values
    4️⃣ Update maximum sum and level if needed
    5️⃣ Return the level with maximum sum

    ------------------------------------------------------------
    ⏱️ TIME & SPACE COMPLEXITY
    ------------------------------------------------------------
    Time Complexity:
        O(N), where N is the number of nodes

    Space Complexity:
        O(N), for the queue in worst case

    ------------------------------------------------------------
    ✔ Classic BFS / level-order traversal problem
      Commonly asked in tree interviews
    ============================================================
*/

import java.util.*;

// Definition for a binary tree node
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {}

    TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}

public class MaximumLevelSumOfBinaryTree {

    public int maxLevelSum(TreeNode root) {

        int answerLevel = 1;
        int maxSum = Integer.MIN_VALUE;

        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);

        int level = 0;

        // Perform level-order traversal
        while (!queue.isEmpty()) {

            int size = queue.size();
            int sum = 0;
            level++;

            // Process all nodes of the current level
            for (int i = 0; i < size; i++) {
                TreeNode curr = queue.poll();
                sum += curr.val;

                if (curr.left != null) queue.add(curr.left);
                if (curr.right != null) queue.add(curr.right);
            }

            // Update max sum and corresponding level
            if (sum > maxSum) {
                maxSum = sum;
                answerLevel = level;
            }
        }

        return answerLevel;
    }

    // ============================================================
    //                         MAIN METHOD
    // ============================================================
    public static void main(String[] args) {

        MaximumLevelSumOfBinaryTree solver =
                new MaximumLevelSumOfBinaryTree();

        /*
            Example 1:
                    1
                   / \
                  7   0
                 / \
                7  -8
        */

        TreeNode root1 = new TreeNode(1);
        root1.left = new TreeNode(7);
        root1.right = new TreeNode(0);
        root1.left.left = new TreeNode(7);
        root1.left.right = new TreeNode(-8);

        System.out.println(
                solver.maxLevelSum(root1)
        ); // Output: 2

        /*
            Example 2:
                    989
                      \
                     10250
                     /
                  98693
                   /
               -32127
        */

        TreeNode root2 = new TreeNode(989);
        root2.right = new TreeNode(10250);
        root2.right.left = new TreeNode(98693);
        root2.right.left.left = new TreeNode(-32127);

        System.out.println(
                solver.maxLevelSum(root2)
        ); // Output: 2
    }
}
