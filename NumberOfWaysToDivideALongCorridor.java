public class NumberOfWaysToDivideALongCorridor {

    int mod = (int) 1e9 + 7;

    /*
     Time Complexity: O(n)
     - Each index is visited with at most 3 states (0,1,2 seats)

     Space Complexity: O(n)
     - DP table + recursion stack
    */
    public int numberOfWays(String corridor) {
        int n = corridor.length();
        Integer[][] dp = new Integer[n + 1][3];
        return find(0, 0, corridor, dp);
    }

    /*
     ind   -> current index in string
     count -> number of seats in current section
     */
    int find(int ind, int count, String s, Integer[][] dp) {

        // Reached end of corridor
        // Valid only if last section has exactly 2 seats
        if (ind == s.length()) {
            return count == 2 ? 1 : 0;
        }

        // Invalid section (more than 2 seats)
        if (count > 2) return 0;

        // Memoization check
        if (dp[ind][count] != null) return dp[ind][count];

        long ways = 0;

        // OPTION 1: Place divider BEFORE index ind
        // Allowed only if current section has exactly 2 seats
        if (count == 2) {
            ways += find(ind, 0, s, dp);
        }

        // OPTION 2: Do not place divider, consume current character
        int newCount = count + (s.charAt(ind) == 'S' ? 1 : 0);
        ways += find(ind + 1, newCount, s, dp);

        return dp[ind][count] = (int) (ways % mod);
    }

    // ---------------- MAIN METHOD ----------------
    public static void main(String[] args) {
        NumberOfWaysToDivideALongCorridor obj =
                new NumberOfWaysToDivideALongCorridor();

        String corridor1 = "SSPPSPS";
        String corridor2 = "PPSPSP";
        String corridor3 = "S";

        System.out.println(obj.numberOfWays(corridor1)); // Expected: 3
        System.out.println(obj.numberOfWays(corridor2)); // Expected: 1
        System.out.println(obj.numberOfWays(corridor3)); // Expected: 0
    }
}
