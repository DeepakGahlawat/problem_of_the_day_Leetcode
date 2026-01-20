import java.util.*;

class MinimumBitwiseArrayI {

    public int[] minBitwiseArray(List<Integer> nums) {
        int n = nums.size();
        int[] result = new int[n];

        for (int i = 0; i < n; i++) {
            int x = nums.get(i);
            int res = -1;
            int d = 1;

            while ((x & d) != 0) {
                res = x - d;
                d <<= 1;
            }
            result[i] = res;
        }
        return result;
    }

    public static void main(String[] args) {

        // ✅ Hardcoded input list (change values here)
        List<Integer> nums = Arrays.asList(1, 2, 3, 4, 5);

        MinimumBitwiseArrayI obj = new MinimumBitwiseArrayI();
        int[] ans = obj.minBitwiseArray(nums);

        System.out.println(Arrays.toString(ans));
    }
}
