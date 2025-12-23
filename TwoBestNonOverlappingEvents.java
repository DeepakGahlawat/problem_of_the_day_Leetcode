/*
    ============================================================
            TWO BEST NON-OVERLAPPING EVENTS
    ============================================================

    🧠 INTUITION
    ------------------------------------------------------------
    Each event has:
        - startTime
        - endTime
        - value

    We can attend AT MOST TWO events such that:
        - They do NOT overlap
        - The sum of their values is MAXIMUM

    Since start/end times are large, we must avoid brute force.

    ------------------------------------------------------------
    🧩 KEY OBSERVATION
    ------------------------------------------------------------
    If events are sorted by END TIME:
        - For any current event i,
          we only need the BEST event that ends
          strictly BEFORE its start time.

    This becomes:
        - Sorting
        - Prefix maximum
        - Binary search

    ------------------------------------------------------------
    🧮 APPROACH
    ------------------------------------------------------------
    1️⃣ Sort events by endTime
    2️⃣ Build:
         - endT[]  → end times
         - best[]  → best value till index i
    3️⃣ For each event:
         - Binary search to find the last event
           whose endTime < current startTime
         - Combine values
    4️⃣ Also consider taking ONLY one event

    ------------------------------------------------------------
    ⏱️ TIME & SPACE COMPLEXITY
    ------------------------------------------------------------
    Time Complexity:
        O(n log n)

    Space Complexity:
        O(n)

    ------------------------------------------------------------
    ✔ Optimal greedy + binary search solution
      Common LeetCode / FAANG problem
    ============================================================
*/

import java.util.*;

public class TwoBestNonOverlappingEvents {

    public int maxTwoEvents(int[][] events) {

        // Sort events by end time
        Arrays.sort(events, (a, b) -> a[1] - b[1]);

        int n = events.length;

        int[] endT = new int[n];   // end times
        int[] best = new int[n];   // prefix max of values

        // Build endT[] and best[]
        for (int i = 0; i < n; i++) {
            endT[i] = events[i][1];
            best[i] = events[i][2];

            if (i > 0) {
                best[i] = Math.max(best[i], best[i - 1]);
            }
        }

        int ans = 0;

        // Try each event as the SECOND event
        for (int i = 0; i < n; i++) {

            int start = events[i][0];
            int value = events[i][2];

            // Binary search: last event ending before start
            int l = 0, r = n - 1, idx = -1;
            while (l <= r) {
                int mid = (l + r) >>> 1;

                if (endT[mid] < start) {
                    idx = mid;
                    l = mid + 1;
                } else {
                    r = mid - 1;
                }
            }

            // Case 1: Combine two events
            if (idx != -1) {
                ans = Math.max(ans, value + best[idx]);
            }

            // Case 2: Take only this event
            ans = Math.max(ans, value);
        }

        return ans;
    }

    // ============================================================
    //                         MAIN METHOD
    // ============================================================
    public static void main(String[] args) {

        TwoBestNonOverlappingEvents solver =
                new TwoBestNonOverlappingEvents();

        int[][] events = {
                {1, 3, 2},
                {4, 5, 2},
                {2, 4, 3}
        };

        System.out.println(
                "Maximum value = " +
                        solver.maxTwoEvents(events)
        );
    }
}
