/*
    ============================================================
                        MEETING ROOMS III
    ============================================================

    🧠 INTUITION
    ------------------------------------------------------------
    We have n meeting rooms (0 to n-1) and a list of meetings
    with unique start times.

    Rules:
        1️⃣ Each meeting is assigned to the lowest numbered
            available room.
        2️⃣ If no room is available, the meeting is DELAYED
            until the earliest room becomes free.
        3️⃣ Delayed meetings keep the SAME duration.
        4️⃣ If multiple rooms have the same meeting count,
            return the smallest room number.

    Goal:
        Find the room that hosted the MAXIMUM number of meetings.

    ------------------------------------------------------------
    🧩 KEY DATA STRUCTURES
    ------------------------------------------------------------
    1️⃣ Min-heap `rooms`
        - Stores currently free room numbers
        - Ensures smallest room number is picked first

    2️⃣ Min-heap `pq`
        - Stores [roomNumber, endTime]
        - Sorted by earliest endTime, then room number
        - Helps manage delayed meetings

    ------------------------------------------------------------
    🧮 APPROACH
    ------------------------------------------------------------
    1️⃣ Sort meetings by start time
    2️⃣ Initialize:
         - All rooms as free
         - Count array for meetings per room
    3️⃣ For each meeting:
         - Free rooms whose meetings ended before current start
         - If a room is free → assign immediately
         - Else → delay meeting to earliest finishing room
    4️⃣ Track meeting count per room
    5️⃣ Return room with maximum meetings
       (tie → smallest room index)

    ------------------------------------------------------------
    ⏱️ TIME & SPACE COMPLEXITY
    ------------------------------------------------------------
    Let m = number of meetings

    Time Complexity:
        O(m log n)

    Space Complexity:
        O(n)

    ------------------------------------------------------------
    ✔ Advanced priority-queue simulation
      Common FAANG / LeetCode Hard problem
    ============================================================
*/

import java.util.*;

public class MeetingRoomsIII {

    public int mostBooked(int n, int[][] meetings) {

        // Sort meetings by start time
        Arrays.sort(meetings, (a, b) -> a[0] - b[0]);

        int m = meetings.length;
        int[] countMeet = new int[n];

        // Min-heap for ongoing meetings: [room, endTime]
        PriorityQueue<int[]> pq = new PriorityQueue<>(
            (a, b) -> {
                if (a[1] == b[1]) return a[0] - b[0];
                return a[1] - b[1];
            }
        );

        // Min-heap of available rooms
        PriorityQueue<Integer> rooms = new PriorityQueue<>();
        for (int i = 0; i < n; i++) rooms.add(i);

        // Process each meeting
        for (int[] meet : meetings) {

            int start = meet[0];
            int end = meet[1];

            // Free rooms that are done before current meeting starts
            while (!pq.isEmpty() && pq.peek()[1] <= start) {
                rooms.add(pq.poll()[0]);
            }

            // If no room available → delay meeting
            if (rooms.isEmpty()) {

                int[] earliest = pq.poll();
                int room = earliest[0];
                int time = earliest[1];

                int duration = end - start;
                end = time + duration;

                pq.add(new int[]{room, end});
                countMeet[room]++;
            }
            else {
                // Assign meeting to smallest available room
                int room = rooms.poll();
                pq.add(new int[]{room, end});
                countMeet[room]++;
            }
        }

        // Find room with maximum meetings
        int ans = 0;
        for (int i = 1; i < n; i++) {
            if (countMeet[i] > countMeet[ans]) {
                ans = i;
            }
        }

        return ans;
    }

    // ============================================================
    //                         MAIN METHOD
    // ============================================================
    public static void main(String[] args) {

        MeetingRoomsIII solver = new MeetingRoomsIII();

        int n1 = 2;
        int[][] meetings1 = {
                {0, 10},
                {1, 5},
                {2, 7},
                {3, 4}
        };
        System.out.println(
                "Most booked room = " +
                        solver.mostBooked(n1, meetings1)
        ); // Output: 0

        int n2 = 3;
        int[][] meetings2 = {
                {1, 20},
                {2, 10},
                {3, 5},
                {4, 9},
                {6, 8}
        };
        System.out.println(
                "Most booked room = " +
                        solver.mostBooked(n2, meetings2)
        ); // Output: 1
    }
}
