// You are given an integer numberOfUsers representing the total number of users and an array events of size n x 3.

// Each events[i] can be either of the following two types:

// Message Event: ["MESSAGE", "timestampi", "mentions_stringi"]
// This event indicates that a set of users was mentioned in a message at timestampi.
// The mentions_stringi string can contain one of the following tokens:
// id<number>: where <number> is an integer in range [0,numberOfUsers - 1]. There can be multiple ids separated by a single whitespace and may contain duplicates. This can mention even the offline users.
// ALL: mentions all users.
// HERE: mentions all online users.
// Offline Event: ["OFFLINE", "timestampi", "idi"]
// This event indicates that the user idi had become offline at timestampi for 60 time units. The user will automatically be online again at time timestampi + 60.
// Return an array mentions where mentions[i] represents the number of mentions the user with id i has across all MESSAGE events.

// All users are initially online, and if a user goes offline or comes back online, their status change is processed before handling any message event that occurs at the same timestamp.

// Note that a user can be mentioned multiple times in a single message event, and each mention should be counted separately.

 

// Example 1:

// Input: numberOfUsers = 2, events = [["MESSAGE","10","id1 id0"],["OFFLINE","11","0"],["MESSAGE","71","HERE"]]

// Output: [2,2]

// Explanation:

// Initially, all users are online.

// At timestamp 10, id1 and id0 are mentioned. mentions = [1,1]

// At timestamp 11, id0 goes offline.

// At timestamp 71, id0 comes back online and "HERE" is mentioned. mentions = [2,2]

// Example 2:

// Input: numberOfUsers = 2, events = [["MESSAGE","10","id1 id0"],["OFFLINE","11","0"],["MESSAGE","12","ALL"]]

// Output: [2,2]

// Explanation:

// Initially, all users are online.

// At timestamp 10, id1 and id0 are mentioned. mentions = [1,1]

// At timestamp 11, id0 goes offline.

// At timestamp 12, "ALL" is mentioned. This includes offline users, so both id0 and id1 are mentioned. mentions = [2,2]

// Example 3:

// Input: numberOfUsers = 2, events = [["OFFLINE","10","0"],["MESSAGE","12","HERE"]]

// Output: [0,1]

// Explanation:

// Initially, all users are online.

// At timestamp 10, id0 goes offline.

// At timestamp 12, "HERE" is mentioned. Because id0 is still offline, they will not be mentioned. mentions = [0,1]

 

// Constraints:

// 1 <= numberOfUsers <= 100
// 1 <= events.length <= 100
// events[i].length == 3
// events[i][0] will be one of MESSAGE or OFFLINE.
// 1 <= int(events[i][1]) <= 105
// The number of id<number> mentions in any "MESSAGE" event is between 1 and 100.
// 0 <= <number> <= numberOfUsers - 1
// It is guaranteed that the user id referenced in the OFFLINE event is online at the time the event occurs.

/*
    ============================================================
                    COUNT MENTIONS PER USER
    ============================================================

    💡 PROBLEM INTUITION
    ------------------------------------------------------------
    We have 'numberOfUsers' users, all starting ONLINE.
    We receive events of two types:

       1) ["OFFLINE", timestamp, userId]
          → This user becomes offline for EXACTLY 60 time units.
            They return online at: timestamp + 60

       2) ["MESSAGE", timestamp, mentions_string]
          mentions_string can be:
              - "ALL"   → Mention every user (even offline).
              - "HERE"  → Mention only online users.
              - "id1 id2 id5 ..." → Mention specific users (duplicates allowed).

    Important Rule:
        If OFFLINE and MESSAGE have the SAME timestamp,
        the OFFLINE must be processed FIRST.

    Hence we must sort events by:
        (1) timestamp ASC
        (2) OFFLINE before MESSAGE when timestamp is SAME


    💡 HOW WE TRACK ONLINE USERS?
    ------------------------------------------------------------
    Instead of storing true/false, we store the time until which
    a user is offline.

        onlineUntil[i] = time when user i comes back online

    Initially:
        onlineUntil[i] = 0 → meaning always online at start.

    User is considered ONLINE at time T if:
        T >= onlineUntil[i]


    💡 MESSAGE PROCESSING:
    ------------------------------------------------------------
        If "ALL":
             → Increment all users

        If "HERE":
             → Increment all users where timestamp >= onlineUntil[i]

        If "id<number> id<number> ..."
             → Split string by spaces and increment individually


    💡 TIME COMPLEXITY:
         O(n log n) for sorting + O(events × users)
         n ≤ 100, events ≤ 100 → extremely efficient.


    ============================================================
*/

import java.util.*;

public class CountMentionsPerUser {

    public int[] countMentions(int numberOfUsers, List<List<String>> events) {

        // Create map from id string to actual index
        Map<String, Integer> idToIndex = new HashMap<>();
        for (int i = 0; i < numberOfUsers; i++) {
            idToIndex.put("id" + i, i);
        }

        // Sort events based on rules:
        //   1. timestamp ASC
        //   2. OFFLINE before MESSAGE if same timestamp
        Collections.sort(events, (a, b) -> {
            int t1 = Integer.parseInt(a.get(1));
            int t2 = Integer.parseInt(b.get(1));

            if (t1 != t2) return t1 - t2;

            // Same timestamp → OFFLINE first
            if (a.get(0).equals("OFFLINE") && b.get(0).equals("MESSAGE")) return -1;
            if (a.get(0).equals("MESSAGE") && b.get(0).equals("OFFLINE")) return 1;

            return 0;
        });

        int[] mentions = new int[numberOfUsers];
        int[] onlineUntil = new int[numberOfUsers]; // initially all 0 → meaning online

        for (List<String> event : events) {

            String type = event.get(0);
            int timestamp = Integer.parseInt(event.get(1));

            if (type.equals("OFFLINE")) {
                // User becomes offline until time + 60
                int user = Integer.parseInt(event.get(2));
                onlineUntil[user] = timestamp + 60;
            }
            else { // MESSAGE
                String message = event.get(2);

                if (message.equals("ALL")) {
                    // Mention all users including offline
                    for (int i = 0; i < numberOfUsers; i++) {
                        mentions[i]++;
                    }
                }
                else if (message.equals("HERE")) {
                    // Mention only online users
                    for (int i = 0; i < numberOfUsers; i++) {
                        if (timestamp >= onlineUntil[i]) {
                            mentions[i]++;
                        }
                    }
                }
                else {
                    // Mentions contains multiple id<number>
                    String[] arr = message.split(" ");
                    for (String id : arr) {
                        id = id.trim();
                        if (id.length() == 0) continue;

                        // Each id should be counted, duplicates allowed
                        int idx = idToIndex.get(id);
                        mentions[idx]++;
                    }
                }
            }
        }

        return mentions;
    }


    // ============================================================
    //                      MAIN METHOD
    // ============================================================
    public static void main(String[] args) {

        CountMentionsPerUser solver = new CountMentionsPerUser();

        List<List<String>> events = new ArrayList<>();
        events.add(Arrays.asList("MESSAGE", "10", "id1 id0"));
        events.add(Arrays.asList("OFFLINE", "11", "0"));
        events.add(Arrays.asList("MESSAGE", "71", "HERE"));

        int[] result = solver.countMentions(2, events);

        System.out.println("Final mentions: " + Arrays.toString(result));
    }
}
