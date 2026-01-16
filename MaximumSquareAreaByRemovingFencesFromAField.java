import java.util.*;

class MaximumSquareAreaByRemovingFencesFromAField {

    static final int MOD = 1_000_000_007;

    public int maximizeSquareArea(int m, int n, int[] hFences, int[] vFences) {

        int[] h = addBoundaries(hFences, m);
        int[] v = addBoundaries(vFences, n);

        // Store all possible vertical distances
        HashSet<Integer> vDiff = new HashSet<>();
        for (int i = 0; i < v.length; i++) {
            for (int j = i + 1; j < v.length; j++) {
                vDiff.add(v[j] - v[i]);
            }
        }

        int best = -1;

        // Check horizontal distances that also exist in vertical distances
        for (int i = 0; i < h.length; i++) {
            for (int j = i + 1; j < h.length; j++) {
                int diff = h[j] - h[i];
                if (vDiff.contains(diff)) {
                    best = Math.max(best, diff);
                }
            }
        }

        if (best == -1) return -1;

        return (int) ((1L * best * best) % MOD);
    }

    private int[] addBoundaries(int[] fences, int limit) {
        int[] arr = new int[fences.length + 2];
        arr[0] = 1;
        arr[arr.length - 1] = limit;

        for (int i = 0; i < fences.length; i++) {
            arr[i + 1] = fences[i];
        }

        Arrays.sort(arr);
        return arr;
    }

    // -------------------- MAIN METHOD --------------------
    public static void main(String[] args) {
        MaximumSquareAreaByRemovingFencesFromAField sol =
                new MaximumSquareAreaByRemovingFencesFromAField();

        int[] h1 = {2, 3};
        int[] v1 = {2};
        System.out.println(sol.maximizeSquareArea(4, 3, h1, v1)); // 4

        int[] h2 = {2};
        int[] v2 = {4};
        System.out.println(sol.maximizeSquareArea(6, 7, h2, v2)); // -1
    }
}
