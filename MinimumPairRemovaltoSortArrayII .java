import java.util.PriorityQueue;

/**
 * Problem: Minimum Pair Removal to Sort Array II
 *
 * Operation:
 * 1) Select adjacent pair with minimum sum (if multiple, select LEFTMOST)
 * 2) Replace that pair by their sum
 * 3) Repeat until array becomes non-decreasing
 *
 * This solution uses:
 * - Doubly Linked List (to merge adjacent nodes efficiently)
 * - Priority Queue (to always fetch minimum sum pair, leftmost tie-breaking)
 * - decreaseCount to track how many decreasing pairs exist currently
 *
 * Time Complexity: O(n log n)  (because each merge uses PQ operations)
 * Space Complexity: O(n)      (nodes, PQ, merged array)
 */

// Node represents each element in current dynamic array (as linked list node)
class Node {
    long value;    // current value at this node (can grow after merges)
    int left;      // original index (used to ensure leftmost tie-breaking)
    Node prev;
    Node next;

    Node(int value, int left) {
        this.value = value;
        this.left = left;
    }
}

/**
 * PQItem represents a pair of adjacent nodes (first, second) and their merge cost (sum).
 * Comparable ensures:
 * - minimum cost comes first
 * - if cost tie, leftmost pair (smaller first.left index) comes first
 */
class PQItem implements Comparable<PQItem> {
    Node first;
    Node second;
    long cost;

    PQItem(Node first, Node second, long cost) {
        this.first = first;
        this.second = second;
        this.cost = cost;
    }

    @Override
    public int compareTo(PQItem other) {
        if (this.cost == other.cost) {
            // tie-break: leftmost pair first
            return this.first.left - other.first.left;
        }
        return this.cost < other.cost ? -1 : 1;
    }
}

public class MinimumPairRemovaltoSortArrayII {

    public int minimumPairRemoval(int[] nums) {
        PriorityQueue<PQItem> pq = new PriorityQueue<>();

        // merged[i] = true means node at original index i has already been merged/removed
        boolean[] merged = new boolean[nums.length];

        int count = 0;          // number of merge operations performed
        int decreaseCount = 0;  // number of decreasing adjacent pairs currently present

        // Build linked list from nums and initialize priority queue with all adjacent pairs
        Node head = new Node(nums[0], 0);
        Node current = head;

        for (int i = 1; i < nums.length; i++) {
            Node newNode = new Node(nums[i], i);

            // link nodes
            current.next = newNode;
            newNode.prev = current;

            // push adjacent pair into PQ
            pq.offer(new PQItem(current, newNode, current.value + newNode.value));

            // track decreasing relation (nums[i-1] > nums[i])
            if (nums[i - 1] > nums[i]) {
                decreaseCount++;
            }
            current = newNode;
        }

        /**
         * Continue merging until no decreasing pairs remain,
         * i.e., the linked list values are non-decreasing.
         */
        while (decreaseCount > 0) {

            PQItem item = pq.poll();
            Node first = item.first;
            Node second = item.second;
            long cost = item.cost;

            /**
             * Lazy deletion / validation:
             * PQ may contain outdated pairs due to previous merges.
             * So we skip invalid items if:
             * - either node already merged
             * - cost doesn't match current node values (means outdated)
             */
            if (merged[first.left] ||
                merged[second.left] ||
                first.value + second.value != cost) {
                continue;
            }

            // perform merge
            count++;

            // if this pair was decreasing, it will be removed after merge
            if (first.value > second.value) {
                decreaseCount--;
            }

            Node prevNode = first.prev;
            Node nextNode = second.next;

            /**
             * Remove second from list:
             * first will represent merged node
             */
            first.next = nextNode;
            if (nextNode != null) {
                nextNode.prev = first;
            }

            /**
             * Update decreaseCount around boundary with prevNode
             * because values around first changed (first.value becomes cost).
             */
            if (prevNode != null) {
                // Before merge: prevNode.value > first.value ?
                // After merge:  prevNode.value > cost ?
                if (prevNode.value > first.value && prevNode.value <= cost) {
                    decreaseCount--;  // decreasing removed
                } else if (prevNode.value <= first.value && prevNode.value > cost) {
                    decreaseCount++;  // new decreasing created
                }

                // push new adjacent pair (prevNode, first)
                pq.offer(new PQItem(prevNode, first, prevNode.value + cost));
            }

            /**
             * Update decreaseCount around boundary with nextNode
             * because second removed and first becomes merged node
             */
            if (nextNode != null) {
                // Before merge: second.value > nextNode.value ?
                // After merge:  cost > nextNode.value ?
                if (second.value > nextNode.value && cost <= nextNode.value) {
                    decreaseCount--; // decreasing removed
                } else if (second.value <= nextNode.value && cost > nextNode.value) {
                    decreaseCount++; // new decreasing created
                }

                // push new adjacent pair (first, nextNode)
                pq.offer(new PQItem(first, nextNode, cost + nextNode.value));
            }

            // finalize merge: update first value and mark second as merged
            first.value = cost;
            merged[second.left] = true;
        }

        return count;
    }

    /**
     * Main method for testing
     */
    public static void main(String[] args) {
        MinimumPairRemovaltoSortArrayII sol = new MinimumPairRemovaltoSortArrayII();

        // Example 1
        int[] nums1 = {5, 2, 3, 1};
        System.out.println("Input: [5,2,3,1]");
        System.out.println("Output: " + sol.minimumPairRemoval(nums1)); // Expected: 2
        System.out.println();

        // Example 2
        int[] nums2 = {1, 2, 2};
        System.out.println("Input: [1,2,2]");
        System.out.println("Output: " + sol.minimumPairRemoval(nums2)); // Expected: 0
        System.out.println();

        // Extra test
        int[] nums3 = {10, 1, 1, 1};
        System.out.println("Input: [10,1,1,1]");
        System.out.println("Output: " + sol.minimumPairRemoval(nums3));
    }
}
