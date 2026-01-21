import java.util.*;

class ConstructTheMinimumBitwiseArrayII {

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
        ConstructTheMinimumBitwiseArrayII obj = new ConstructTheMinimumBitwiseArrayII();

        List<Integer> nums1 = Arrays.asList(2, 3, 5, 7);
        int[] ans1 = obj.minBitwiseArray(nums1);
        System.out.println("Input:  " + nums1);
        System.out.println("Output: " + Arrays.toString(ans1));

        List<Integer> nums2 = Arrays.asList(11, 13, 31);
        int[] ans2 = obj.minBitwiseArray(nums2);
        System.out.println("\nInput:  " + nums2);
        System.out.println("Output: " + Arrays.toString(ans2));
    }
}
