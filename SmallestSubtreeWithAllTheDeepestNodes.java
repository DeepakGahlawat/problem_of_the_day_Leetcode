/*
    ==============================================================
        SMALLEST SUBTREE WITH ALL THE DEEPEST NODES
    ==============================================================

    🧠 INTUITION
    --------------------------------------------------------------
    We want the SMALLEST subtree that contains ALL the deepest
    nodes of the binary tree.

    Key observations:
    - Deepest nodes are those at the maximum depth
    - The answer is essentially the LOWEST COMMON ANCESTOR (LCA)
      of all deepest nodes
    - But instead of first finding deepest nodes separately,
      we can compute everything in ONE DFS

    --------------------------------------------------------------
    🧩 APPROACH (POST-ORDER DFS)
    --------------------------------------------------------------
    For every node, we want to know:
        1️⃣ The maximum depth in its subtree
        2️⃣ The subtree root that contains all deepest nodes

    We define a helper object:
        NewNode(depth, node)

    Logic at each node:
    - Recurse left and right
    - Compare depths from left and right subtrees:
        • If one side is deeper → return that side
        • If both sides have same depth → current node is the answer

    --------------------------------------------------------------
    🧮 WHY THIS WORKS
    --------------------------------------------------------------
    - If deepest nodes are only on one side → propagate that side up
    - If deepest nodes appear in both subtrees → current node is
      the smallest subtree containing them all

    --------------------------------------------------------------
    ⏱️ TIME & SPACE COMPLEXITY
    --------------------------------------------------------------
    Time Complexity:
        O(N), where N is the number of nodes

    Space Complexity:
        O(H) recursion stack, where H is the height of the tree
        Worst case: O(N)

    --------------------------------------------------------------
    ✔ Clean post-order recursion
    ✔ Very common tree + recursion interview problem
    ============================================================== 
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

// Helper class to store depth and corresponding node
class NewNode {
    int depth;
    TreeNode node;

    public NewNode(int depth, TreeNode node) {
        this.depth = depth;
        this.node = node;
    }
}

public class SmallestSubtreeWithAllTheDeepestNodes {

    public TreeNode subtreeWithAllDeepest(TreeNode root) {
        if (root == null) return null;
        return solve(0, root).node;
    }

    // Post-order DFS
    NewNode solve(int depth, TreeNode root) {

        if (root == null) return null;

        // Leaf node → deepest at this level
        if (root.left == null && root.right == null) {
            return new NewNode(depth, root);
        }

        NewNode left = solve(depth + 1, root.left);
        NewNode right = solve(depth + 1, root.right);

        // If only one subtree exists
        if (left == null) return right;
        if (right == null) return left;

        // Compare depths
        if (left.depth > right.depth) {
            return left;
        } else if (right.depth > left.depth) {
            return right;
        } else {
            // Both sides have deepest nodes → current node is answer
            return new NewNode(left.depth, root);
        }
    }

    // ==============================================================
    //                          MAIN METHOD
    // ==============================================================
    public static void main(String[] args) {

        SmallestSubtreeWithAllTheDeepestNodes solver =
                new SmallestSubtreeWithAllTheDeepestNodes();

        /*
            Example 1:
                    3
                   / \
                  5   1
                 / \ / \
                6  2 0  8
                  / \
                 7   4
            Answer: Node 2
        */

        TreeNode root1 = new TreeNode(3);
        root1.left = new TreeNode(5);
        root1.right = new TreeNode(1);
        root1.left.left = new TreeNode(6);
        root1.left.right = new TreeNode(2);
        root1.right.left = new TreeNode(0);
        root1.right.right = new TreeNode(8);
        root1.left.right.left = new TreeNode(7);
        root1.left.right.right = new TreeNode(4);

        System.out.println(
                solver.subtreeWithAllDeepest(root1).val
        ); // Output: 2

        /*
            Example 2:
                1
        */

        TreeNode root2 = new TreeNode(1);
        System.out.println(
                solver.subtreeWithAllDeepest(root2).val
        ); // Output: 1
    }
}
