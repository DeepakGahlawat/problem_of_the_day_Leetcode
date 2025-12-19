/*
    =====================================================================
                    FIND ALL PEOPLE WITH SECRET
    =====================================================================

    🧠 INTUITION
    ---------------------------------------------------------------------
    Initially:
        - Person 0 knows the secret.
        - firstPerson meets person 0 at time 0 → firstPerson also knows it.

    Meetings happen at different timestamps.
    Important rule:
        - If multiple meetings happen at the SAME time,
          the secret can spread among all connected people at that time.
        - But if no one in that time-group knows the secret,
          the spread should NOT persist beyond that timestamp.

    Hence, we need:
        - Temporary connections for meetings at the same time
        - Rollback (reset) connections if secret is not present

    This makes the problem perfect for:
        ✅ Union-Find (Disjoint Set Union)
        ✅ Time-based grouping of meetings

    ---------------------------------------------------------------------
    🧩 APPROACH
    ---------------------------------------------------------------------
    1️⃣ Sort meetings by time
    2️⃣ Group meetings with the same timestamp
    3️⃣ For each timestamp:
         - Union all participants of meetings at that time
         - Check if any participant is connected to person 0
         - If NOT connected → reset those participants
    4️⃣ After processing all times, all people connected to 0
       know the secret

    ---------------------------------------------------------------------
    ⏱️ TIME & SPACE COMPLEXITY
    ---------------------------------------------------------------------
    Let m = number of meetings, n = number of people

    Time Complexity:
        O(m log m + m α(n))
        (sorting + union-find operations)

    Space Complexity:
        O(n + m)

    ---------------------------------------------------------------------
    ✔ Classic Union-Find + rollback style problem
      Frequently asked in FAANG interviews
    =====================================================================
*/

import java.util.*;

public class FindAllPeopleWithSecret {

    public List<Integer> findAllPeople(
            int n,
            int[][] meetings,
            int firstPerson
    ) {

        // Sort meetings by time
        Arrays.sort(meetings, (a, b) -> a[2] - b[2]);

        // Group meetings by same timestamp
        Map<Integer, List<int[]>> sameTimeMeetings = new TreeMap<>();
        for (int[] meeting : meetings) {
            sameTimeMeetings
                    .computeIfAbsent(meeting[2], k -> new ArrayList<>())
                    .add(meeting);
        }

        // Union-Find structure
        UnionFind uf = new UnionFind(n);

        // Person 0 shares secret with firstPerson at time 0
        uf.unite(0, firstPerson);

        // Process meetings in increasing time order
        for (int time : sameTimeMeetings.keySet()) {

            List<int[]> currMeetings = sameTimeMeetings.get(time);

            // Step 1: Union all participants at this time
            for (int[] meet : currMeetings) {
                uf.unite(meet[0], meet[1]);
            }

            // Step 2: Reset connections if secret not present
            for (int[] meet : currMeetings) {
                int x = meet[0];
                int y = meet[1];

                // If neither is connected to 0, rollback
                if (!uf.connected(x, 0)) {
                    uf.reset(x);
                    uf.reset(y);
                }
            }
        }

        // All people connected to 0 know the secret
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (uf.connected(i, 0)) {
                result.add(i);
            }
        }

        return result;
    }

    // ============================================================
    //                         MAIN METHOD
    // ============================================================
    public static void main(String[] args) {

        FindAllPeopleWithSecret solver =
                new FindAllPeopleWithSecret();

        int n = 6;
        int[][] meetings = {
                {1, 2, 5},
                {2, 3, 8},
                {1, 5, 10}
        };
        int firstPerson = 1;

        System.out.println(
                "People knowing the secret: " +
                        solver.findAllPeople(n, meetings, firstPerson)
        );
    }
}

/*
    ============================================================
                        UNION FIND CLASS
    ============================================================
*/

class UnionFind {

    private int[] parent;
    private int[] rank;

    public UnionFind(int n) {
        parent = new int[n];
        rank = new int[n];

        // Initially, each node is its own parent
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
    }

    // Find with path compression
    public int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]);
        }
        return parent[x];
    }

    // Union by rank
    public void unite(int x, int y) {
        int px = find(x);
        int py = find(y);

        if (px != py) {
            if (rank[px] > rank[py]) {
                parent[py] = px;
            } else if (rank[px] < rank[py]) {
                parent[px] = py;
            } else {
                parent[py] = px;
                rank[px]++;
            }
        }
    }

    // Check connectivity
    public boolean connected(int x, int y) {
        return find(x) == find(y);
    }

    // Reset node to isolate it
    public void reset(int x) {
        parent[x] = x;
        rank[x] = 0;
    }
}
