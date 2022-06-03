

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    // stuff to intialize the grid
    private int myn;
    private int gridSize;
    private boolean[] grid;
    // keep track of number of open sites
    private int numOpenSites;
    //virtual sites
    private int virtualTopIndex;
    private int virtualBottomIndex;
    // quick union for percolation
    private WeightedQuickUnionUF percUf;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 1) {
            throw new IllegalArgumentException("n must be greater than 0");
        }
        myn = n;
        gridSize = (n * n) + 2;
        grid = new boolean[gridSize]; // two spaces for the virtual tip and bottom

        numOpenSites = 0;

        // set virtual sites
        virtualTopIndex = 0;
        virtualBottomIndex = (myn * myn) + 1;
        grid[virtualTopIndex] = true;
        grid[virtualBottomIndex] = false;

        // intialize union for percolation
        percUf = new WeightedQuickUnionUF(gridSize);
        // connect top and bottom rows to virtual sites
        for (int col = 1; col <= myn; col ++) {
            // virtual top
            int rowTop = 1;
            int topIndex = getIndex(rowTop, col);
            percUf.union(virtualTopIndex, topIndex);

            // virtual bottom
            int rowBottom = myn;
            int bottomIndex = getIndex(rowBottom, col);
            percUf.union(virtualBottomIndex, bottomIndex);
        }
    }

    // given a row and column gets the index in the grid
    private int getIndex(int row, int col) {
        checkRange(row, col);
        return ((row - 1) * myn) + col;
    }

    //opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        int siteIndex = getIndex(row, col);
        // check if site is open
        if (grid[siteIndex]) {
            return;
        }
        // set site to open
        grid[siteIndex] = true;
        numOpenSites ++;

        // check if adjacent sites are open and union
        if (col > 1 && isOpen(row, col - 1)) {
            int index = getIndex(row, col - 1);
            percUf.union(siteIndex, index);
        }

        if (row > 1 && isOpen(row - 1, col)) {
            int index = getIndex(row - 1, col);
            percUf.union(siteIndex, index);
        }

        if (col < myn && isOpen(row, col + 1)) {
            int index = getIndex(row, col + 1);
            percUf.union(siteIndex, index);
        }

        if (row < myn && isOpen(row + 1, col)) {
            int index = getIndex(row + 1, col);
            percUf.union(siteIndex, index);
        }
    }

    // checks if site is open
    public boolean isOpen(int row, int col) {
        int index = getIndex(row, col);
        return grid[index];
    }

    // returns the nmber of open sites
    public int numberOfOpenSites() {
        return numOpenSites;
    }

    public boolean percolates() {
        int findTop = percUf.find(virtualTopIndex);
        int findBottom = percUf.find(virtualBottomIndex);

        return findTop == findBottom;
        
    }

    // check the row and column are in the valid range
    public void checkRange(int row, int col) {
        if (row > gridSize || row < 1) {
            throw new IndexOutOfBoundsException("Row is out of bounds");
        }
        if (col > gridSize || col < 1) {
            throw new IndexOutOfBoundsException("Column is out of bounds");
        }
    }

    // testing
    public static void main(String[] args) {
        // create a 2 *2 grid to test
        Percolation test = new Percolation(2);
        test.open(1, 1);
        test.open(1, 2);
        test.open(2, 1);
        test.open(2, 2);

        if (test.percolates()) {
            System.out.println("hey");
        } else {
            System.out.println("naw");
        }
    }
    
}
