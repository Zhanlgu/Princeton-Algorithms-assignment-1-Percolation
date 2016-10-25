import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int n;
    private WeightedQuickUnionUF uf;
    private byte[] openStateRecord;
    private int index;
    private boolean neighbourConnectBottom;
    // private boolean isPercolate;

    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException();
        this.n = n;
        uf = new WeightedQuickUnionUF(n * (n + 2));
        openStateRecord = new byte[n * (n + 2)];
        // a site has 3 state,
        //    for 0: close; for 1: open, but not connect to the bottom(all open);
        //    for 5, open and connect to the bottom.
        for (int i = 0; i < n; i++) {
            uf.union(0, i);
            openStateRecord[i] = 1;
        }
        for (int i = n; i < (n * (n + 1)); i++)
            openStateRecord[i] = 0;
        for (int i = n * (n + 1); i < (n * (n + 2)); i++) {
            openStateRecord[i] = 2;
        }
        // this.isPercolate = false;
    }

    private int xyTo1d(int x, int y) {
        return (x * n + y);
    }

    public void open(int i, int j) {
        if (i < 1 || j < 1 || i > n || j > n)
            throw new java.lang.IndexOutOfBoundsException();
        if (!isOpen(i, j)) {
            j--;
            index = xyTo1d(i, j);
            openStateRecord[index] = 1;
            neighbourConnectBottom = false;
            if ((i >= 1) && (openStateRecord[xyTo1d(i-1, j)] != 0)) {
                if (openStateRecord[uf.find(xyTo1d(i-1, j))] == 2)
                    neighbourConnectBottom = true;
                uf.union(index, xyTo1d(i-1, j));
            }
            if ((i <= n) && (openStateRecord[xyTo1d(i+1, j)] != 0)) {
                if (openStateRecord[uf.find(xyTo1d(i+1, j))] == 2)
                    neighbourConnectBottom = true;
                uf.union(index, xyTo1d(i+1, j));
            }
            if ((j >= 1) && (openStateRecord[xyTo1d(i, j-1)] != 0)) {
                if (openStateRecord[uf.find(xyTo1d(i, j-1))] == 2)
                    neighbourConnectBottom = true;
                uf.union(index, xyTo1d(i, j-1));
            }
            if ((j <= (n-2)) && (openStateRecord[xyTo1d(i, j+1)] != 0)) {
                if (openStateRecord[uf.find(xyTo1d(i, j+1))] == 2)
                    neighbourConnectBottom = true;
                uf.union(index, xyTo1d(i, j+1));
            }
            if (neighbourConnectBottom)
                openStateRecord[uf.find(index)] = 2;
        }
    }

    public boolean isOpen(int i, int j) {
        if (i < 1 || j < 1 || i > n || j > n)
            throw new java.lang.IndexOutOfBoundsException();
        j--;
        if (openStateRecord[xyTo1d(i, j)] != 0)
            return true;
        return false;
    }

    public boolean isFull(int i, int j) {
        if (i < 1 || j < 1 || i > n || j > n)
            throw new java.lang.IndexOutOfBoundsException();
        j--;
        return uf.connected(0, xyTo1d(i, j));
    }

    public boolean percolates() {
        if (openStateRecord[uf.find(0)] == 2)
            return true;
        return false;
        // if (!this.isPercolate) {
        //     if (neighbourConnectBottom)
        //         if (uf.connected(0, index)) {
        //             this.isPercolate = true;
        //             return true;
        //         }
        // } else {
        //     return true;
        // }
        // return false;
    }
}