/*
    ===============================================================
        MAXIMUM PRODUCT OF SPLITTED BINARY TREE
    ===============================================================

    🧠 INTUITION
    ---------------------------------------------------------------
    We want to split the binary tree by removing exactly ONE edge.
    This creates two subtrees.

    Let:
        - S  = sum of all nodes in the tree
        - x  = sum of one subtree after removing an edge

    Then the other subtree has sum = (S - x)

    👉 Product after splitting at this edge = x * (S - x)

    So the problem reduces to:
        "For every possible subtree sum x,
         maximize x * (S - x)"

    ---------------------------------------------------------------
    🧩 APPROACH (TWO DFS PASSES)
    ---------------------------------------------------------------

    1️⃣ First DFS (dfs1):
       - Compute subtree sums
       - Store subtree sum directly in root.val
       - Also compute totalSum of the entire tree

    2️⃣ Second DFS (dfs2):
       - For every node, try cutting the edge above its left child
       - Try cutting the edge above its right child
       - Compute product:
             subtreeSum * (totalSum - subtreeSum)
       - Track the maximum product

    IMPORTANT:
    - We maximize the product FIRST
    - Apply modulo only at the very end (as required)

    ---------------------------------------------------------------
    ⏱️ TIME & SPACE COMPLEXITY
    ---------------------------------------------------------------
    Time Complexity:
        O(N)   (each node visited twice)

    Space Complexity:
        O(H)   recursion stack, H = height of tree
        Worst case: O(N)

    ---------------------------------------------------------------
    ✔ Key Interview Takeaways
    ---------------------------------------------------------------
    - Tree DP using subtree sums
    - Two-pass DFS technique
    - Modulo applied AFTER optimization
    - Mutating tree values is allowed when stated clearly

    ===============================================================
*/

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
        val = x;
    }
}

public class MaximumProductOfSplittedBinaryTree {

    long maxProduct = 0;
    int totalSum;
    int mod = 1_000_000_007;

    public int maxProduct(TreeNode root) {
        // First DFS: compute subtree sums and total sum
        totalSum = dfs1(root);

        // Second DFS: compute max product using subtree sums
        dfs2(root);

        // Return result modulo as required
        return (int) (maxProduct % mod);
    }

    // ------------------------------------------------------------
    // DFS 1: Compute subtree sums
    // ------------------------------------------------------------
    int dfs1(TreeNode root) {
        if (root == null) return 0;

        int left = dfs1(root.left);
        int right = dfs1(root.right);

        int sum = root.val + left + right;

        // Store subtree sum inside the node
        root.val = sum;

        return sum;
    }

    // ------------------------------------------------------------
    // DFS 2: Try splitting at every possible edge
    // ------------------------------------------------------------
    void dfs2(TreeNode root) {
        if (root == null) return;

        // Try cutting edge between root and left child
        if (root.left != null) {
            long leftSum = root.left.val;
            long product = leftSum * (totalSum - leftSum);
            maxProduct = Math.max(maxProduct, product);
        }

        // Try cutting edge between root and right child
        if (root.right != null) {
            long rightSum = root.right.val;
            long product = rightSum * (totalSum - rightSum);
            maxProduct = Math.max(maxProduct, product);
        }

        dfs2(root.left);
        dfs2(root.right);
    }

    // ------------------------------------------------------------
    // MAIN METHOD (for local testing)
    // ------------------------------------------------------------
    public static void main(String[] args) {

        MaximumProductOfSplittedBinaryTree solver =
                new MaximumProductOfSplittedBinaryTree();

        /*
            Example 1:
                    1
                   / \
                  2   3
                 / \   \
                4   5   6
        */

        TreeNode root1 = new TreeNode(1);
        root1.left = new TreeNode(2);
        root1.right = new TreeNode(3);
        root1.left.left = new TreeNode(4);
        root1.left.right = new TreeNode(5);
        root1.right.right = new TreeNode(6);

        System.out.println(solver.maxProduct(root1));
        // Expected Output: 110


        /*
            Example 2:
                    1
                     \
                      2
                     / \
                    3   4
                   / \
                  5   6
        */

        TreeNode root2 = new TreeNode(1);
        root2.right = new TreeNode(2);
        root2.right.left = new TreeNode(3);
        root2.right.right = new TreeNode(4);
        root2.right.left.left = new TreeNode(5);
        root2.right.left.right = new TreeNode(6);

        System.out.println(solver.maxProduct(root2));
        // Expected Output: 90
    }
}
