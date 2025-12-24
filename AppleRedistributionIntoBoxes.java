/*
    ============================================================
            APPLE REDISTRIBUTION INTO BOXES
    ============================================================

    🧠 INTUITION
    ------------------------------------------------------------
    We are given:
        - apple[]    → number of apples in each pack
        - capacity[] → capacity of each box

    Apples from the SAME pack can be split across MULTIPLE boxes.
    So the problem reduces to:
        👉 Can the selected boxes hold the TOTAL number of apples?

    Our goal:
        - Pick the MINIMUM number of boxes
        - Such that their TOTAL capacity ≥ TOTAL apples

    ------------------------------------------------------------
    🧩 KEY OBSERVATION (GREEDY)
    ------------------------------------------------------------
    To minimize the number of boxes:
        - Always pick the box with the LARGEST capacity first

    This is a classic greedy strategy:
        - Bigger boxes reduce the remaining apples faster

    ------------------------------------------------------------
    🧮 APPROACH
    ------------------------------------------------------------
    1️⃣ Compute total apples = sum(apple[])
    2️⃣ Insert all box capacities into a MAX-HEAP
    3️⃣ Repeatedly:
         - Take the largest capacity box
         - Reduce remaining apples
         - Count the number of boxes used
    4️⃣ Stop when remaining apples ≤ 0

    ------------------------------------------------------------
    ⏱️ TIME & SPACE COMPLEXITY
    ------------------------------------------------------------
    Let m = number of boxes

    Time Complexity:
        O(m log m)

    Space Complexity:
        O(m)   (PriorityQueue)

    ------------------------------------------------------------
    ✔ Simple and optimal greedy solution
      Common in OA / interview problems
    ============================================================
*/

import java.util.*;

public class AppleRedistributionIntoBoxes {

    public int minimumBoxes(int[] apple, int[] capacity) {

        // Step 1: Calculate total number of apples
        int totalApples = 0;
        for (int a : apple) {
            totalApples += a;
        }

        // Step 2: Max-heap to always pick the largest box first
        PriorityQueue<Integer> pq =
                new PriorityQueue<>((a, b) -> b - a);

        for (int cap : capacity) {
            pq.add(cap);
        }

        // Step 3: Pick boxes greedily
        int boxesUsed = 0;

        while (totalApples > 0) {
            boxesUsed++;
            totalApples -= pq.poll(); // use largest available box
        }

        return boxesUsed;
    }

    // ============================================================
    //                         MAIN METHOD
    // ============================================================
    public static void main(String[] args) {

        AppleRedistributionIntoBoxes solver =
                new AppleRedistributionIntoBoxes();

        int[] apple1 = {1, 3, 2};
        int[] capacity1 = {4, 3, 1, 5, 2};

        System.out.println(
                "Minimum boxes needed = " +
                        solver.minimumBoxes(apple1, capacity1)
        );

        int[] apple2 = {5, 5, 5};
        int[] capacity2 = {2, 4, 2, 7};

        System.out.println(
                "Minimum boxes needed = " +
                        solver.minimumBoxes(apple2, capacity2)
        );
    }
}
