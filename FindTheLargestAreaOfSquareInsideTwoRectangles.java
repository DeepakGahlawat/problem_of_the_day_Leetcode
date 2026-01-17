class FindTheLargestAreaOfSquareInsideTwoRectangles {

    public long largestSquareArea(int[][] bottomLeft, int[][] topRight) {

        /*
         Intuition:
         ----------
         A square can fit inside the intersection of two rectangles.
         So for every pair of rectangles:
           1) Find their intersection width (w)
           2) Find their intersection height (h)
           3) The largest square side = min(w, h)
           4) Track the maximum possible square side

         If rectangles do not intersect → w or h ≤ 0 → ignore
        */

        long maxLength = 0L;
        int n = bottomLeft.length;

        // Check every pair of rectangles
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {

                // Overlapping width
                long w = Math.min(topRight[i][0], topRight[j][0])
                       - Math.max(bottomLeft[i][0], bottomLeft[j][0]);

                // Overlapping height
                long h = Math.min(topRight[i][1], topRight[j][1])
                       - Math.max(bottomLeft[i][1], bottomLeft[j][1]);

                // Side of largest square inside intersection
                long side = Math.min(w, h);

                // Update maximum side
                if (side > 0) {
                    maxLength = Math.max(maxLength, side);
                }
            }
        }

        // Return area = side²
        return maxLength * maxLength;
    }

    // Optional main method for testing
    public static void main(String[] args) {
        FindTheLargestAreaOfSquareInsideTwoRectangles obj =
                new FindTheLargestAreaOfSquareInsideTwoRectangles();

        int[][] bottomLeft = {{1,1},{2,2},{3,1}};
        int[][] topRight = {{3,3},{4,4},{6,6}};

        System.out.println(obj.largestSquareArea(bottomLeft, topRight)); // 1
    }
}
