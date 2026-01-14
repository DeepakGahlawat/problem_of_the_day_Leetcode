import java.util.*;

/*
========================================================
Class Name: Separate Squares II
========================================================

Approach:
---------
Scan Line + Segment Tree

We sweep a horizontal line from bottom to top.
At each y-interval, we maintain the total horizontal coverage
(using a segment tree over discretized x-coordinates).

We accumulate covered area strip by strip and then find
the minimum y where area below == area above.

Time Complexity: O(n log n)
Space Complexity: O(n)
========================================================
*/

class SegmentTree {

    private int[] count;
    private int[] covered;
    private int[] xs;
    private int n;

    public SegmentTree(int[] xs_) {
        xs = xs_;
        n = xs.length - 1;
        count = new int[4 * n];
        covered = new int[4 * n];
    }

    private void modify(int ql, int qr, int val, int l, int r, int pos) {
        if (xs[r + 1] <= ql || xs[l] >= qr) return;

        if (ql <= xs[l] && xs[r + 1] <= qr) {
            count[pos] += val;
        } else {
            int mid = (l + r) / 2;
            modify(ql, qr, val, l, mid, pos * 2 + 1);
            modify(ql, qr, val, mid + 1, r, pos * 2 + 2);
        }

        if (count[pos] > 0) {
            covered[pos] = xs[r + 1] - xs[l];
        } else {
            covered[pos] = (l == r) ? 0
                    : covered[pos * 2 + 1] + covered[pos * 2 + 2];
        }
    }

    public void update(int ql, int qr, int val) {
        modify(ql, qr, val, 0, n - 1, 0);
    }

    public int query() {
        return covered[0];
    }
}

class SeparateSquaresII {

    public double separateSquares(int[][] squares) {

        List<int[]> events = new ArrayList<>();
        Set<Integer> xsSet = new TreeSet<>();

        // Build events
        for (int[] sq : squares) {
            int x = sq[0], y = sq[1], l = sq[2];
            int xr = x + l;

            events.add(new int[]{y, 1, x, xr});
            events.add(new int[]{y + l, -1, x, xr});

            xsSet.add(x);
            xsSet.add(xr);
        }

        // Sort by y
        events.sort((a, b) -> Integer.compare(a[0], b[0]));

        int[] xs = xsSet.stream().mapToInt(i -> i).toArray();
        SegmentTree segTree = new SegmentTree(xs);

        List<Long> prefixArea = new ArrayList<>();
        List<Integer> widths = new ArrayList<>();

        long totalArea = 0;
        int prevY = events.get(0)[0];

        // Scan line sweep
        for (int[] e : events) {
            int y = e[0];
            int delta = e[1];
            int xl = e[2];
            int xr = e[3];

            int width = segTree.query();
            totalArea += (long) width * (y - prevY);

            segTree.update(xl, xr, delta);

            prefixArea.add(totalArea);
            widths.add(segTree.query());

            prevY = y;
        }

        long target = (totalArea + 1) / 2;
        int idx = binarySearch(prefixArea, target);

        long area = prefixArea.get(idx);
        int width = widths.get(idx);
        int baseY = events.get(idx)[0];

        return baseY + (totalArea - 2 * area) / (2.0 * width);
    }

    private int binarySearch(List<Long> list, long target) {
        int l = 0, r = list.size() - 1;
        int ans = 0;

        while (l <= r) {
            int mid = l + (r - l) / 2;
            if (list.get(mid) < target) {
                ans = mid;
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return ans;
    }

    // ===================== MAIN METHOD =====================
    public static void main(String[] args) {
        SeparateSquaresII sol = new SeparateSquaresII();

        int[][] squares1 = {{0, 0, 1}, {2, 2, 1}};
        int[][] squares2 = {{0, 0, 2}, {1, 1, 1}};

        System.out.printf("%.5f\n", sol.separateSquares(squares1)); // 1.00000
        System.out.printf("%.5f\n", sol.separateSquares(squares2)); // 1.16667
    }
}
